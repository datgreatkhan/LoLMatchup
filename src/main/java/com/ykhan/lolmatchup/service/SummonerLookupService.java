package com.ykhan.lolmatchup.service;

import com.ykhan.lolmatchup.config.LeagueAPIHandler;
import com.ykhan.lolmatchup.data.ChampionInfo;
import com.ykhan.lolmatchup.data.LiveMatchInfo;
import com.ykhan.lolmatchup.data.PlayerInfo;
import com.ykhan.lolmatchup.data.datadragon.LeagueChampion;
import com.ykhan.lolmatchup.data.helper.Pair;
import com.ykhan.lolmatchup.data.helper.PlayerInfoHelper;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import com.ykhan.lolmatchup.repository.LeagueSummonerRepository;
import jakarta.transaction.Transactional;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;
import no.stelar7.api.r4j.impl.lol.raw.SpectatorAPI;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorGameInfo;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Service that is used to look up a player, or their live match and processes the data
 * so it is prepared for delivery to the frontend.
 */
@Service
public class SummonerLookupService {

    private static final Logger logger = LoggerFactory.getLogger(SummonerLookupService.class);

    @Autowired
    private LeagueAPIHandler leagueAPI;

    @Autowired
    private LeagueSummonerCreationService leagueSummonerCreationService;

    @Autowired
    private LeagueSummonerRepository leagueSummonerRepository;

    @Transactional
    public Optional<LeagueSummoner> lookupLeagueSummoner(LeagueShard region, String name) {
        Optional<LeagueSummoner> leagueSummoner = leagueSummonerRepository.findByRegionAndName(region, name);

        if(leagueSummoner.isEmpty()) {
            try{
                LeagueSummoner createdLeagueSummoner = leagueSummonerCreationService.create(region, name).get();

                return Optional.of(createdLeagueSummoner);
            }catch(InterruptedException | ExecutionException e) {
                return Optional.empty();
            }
        }else{
            return leagueSummoner;
        }
    }

    @Transactional
    public Optional<Pair<PlayerInfo, LiveMatchInfo>> lookupLiveMatch(LeagueSummoner leagueSummoner) {
        LeagueShard region = leagueSummoner.getRegion();
        SpectatorAPI spectator = leagueAPI.get().getSpectatorAPI();

        SpectatorGameInfo currentMatch = spectator.getCurrentGame(region, leagueSummoner.getSummonerId());

        if(currentMatch == null) {
            return Optional.empty();
        }else{
            List<SpectatorParticipant> participants = currentMatch.getParticipants();

            ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();

            for(SpectatorParticipant participant : participants) {
                Optional<LeagueSummoner> summonerQuery = leagueSummonerRepository.findBySummonerId(participant.getSummonerId());

                LeagueSummoner summoner;

                if(summonerQuery.isEmpty()) {
                    try {
                        summoner = leagueSummonerCreationService.create(region, participant.getSummonerName()).get();
                    }catch(InterruptedException | ExecutionException e) {
                        logger.error("Error on creation of Summoner.", e);
                        return Optional.empty();
                    }
                }else{
                     summoner = summonerQuery.get();
                }

                players.add(PlayerInfoHelper.getPlayerInfo(leagueAPI, summoner, participant));
            }

            PlayerInfo player = players.stream().filter(p -> p.summoner().getSummonerId().equals(leagueSummoner.getSummonerId())).findFirst().get();

            List<PlayerInfo> redTeam = players.stream().filter(p -> p.team() == TeamType.RED).toList();
            List<PlayerInfo> blueTeam = players.stream().filter(p -> p.team() == TeamType.BLUE).toList();

            Map<TeamType, List<PlayerInfo>> teams = new HashMap<>();
            teams.put(TeamType.RED, redTeam);
            teams.put(TeamType.BLUE, blueTeam);

            List<LeagueChampion> redBans = currentMatch.getBannedChampions().stream().filter(p -> p.getTeamId() == TeamType.RED.getValue()).map(champ -> leagueAPI.getLeagueChampions().get(champ.getChampionId() + "")).filter(v -> v != null).toList();
            List<LeagueChampion> blueBans = currentMatch.getBannedChampions().stream().filter(p -> p.getTeamId() == TeamType.BLUE.getValue()).map(champ -> leagueAPI.getLeagueChampions().get(champ.getChampionId() + "")).filter(v -> v != null).toList();

            Map<TeamType, List<LeagueChampion>> bannedChampions = new HashMap<>();
            bannedChampions.put(TeamType.RED, redBans);
            bannedChampions.put(TeamType.BLUE, blueBans);

            return Optional.of(new Pair<PlayerInfo, LiveMatchInfo>(player, new LiveMatchInfo(currentMatch.getGameStart(), currentMatch.getGameLength(), bannedChampions, teams)));
        }
    }
}
