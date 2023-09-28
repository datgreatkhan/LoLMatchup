package com.ykhan.lolmatchup.controller;

import com.ykhan.lolmatchup.config.LeagueAPIHandler;
import com.ykhan.lolmatchup.data.LiveMatchInfo;
import com.ykhan.lolmatchup.data.PlayerInfo;
import com.ykhan.lolmatchup.data.helper.Pair;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import com.ykhan.lolmatchup.repository.LeagueSummonerRepository;
import com.ykhan.lolmatchup.service.SummonerLookupService;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorGameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class MainController {

    @Autowired
    private SummonerLookupService summonerLookupService;

    /*

        We want to return:

            - Looked up - PlayerInfo
                - LeagueSummoner (target)
                - LaneInfo
                    - Lane - LaneType
                    - Winrate
                - ChampionInfo
                    - Champion - LeagueChampion
                    - Winrate
                    - Preferred Item Build
                 - Stats
            - Guessed Matchup - PlayerInfo
                - LeagueSummoner (guessed matchup)
                - LaneInfo
                    - Lane - LaneType
                    - Winrate
                - ChampionInfo
                    - Champion - LeagueChampion
                    - Winrate
                    - Preferred Item Build
                - Stats
            - LiveMatchInfo:
                - Game Start, Game Length
                - Banned Champions - LeagueChampions[10]
                - Other Players - PlayerInfo[9]
                    - LeagueSummoner
                    - LaneInfo
                        - Lane - LaneType
                        - Winrate
                    - ChampionInfo
                        - Champion - LeagueChampion
                        - Winrate
                        - Preferred Item Build
                    - Stats

     */

    private record LiveMatch(PlayerInfo player, LiveMatchInfo liveMatchInfo) {}

    @GetMapping("live/{region}/{name}")
    public ResponseEntity<LiveMatch> liveMatch(@PathVariable String region, @PathVariable String name) {
        Optional<LeagueShard> queryRegion = Arrays.stream(LeagueShard.values()).filter(r -> r.getValue().equals(region)).findFirst();

        if(queryRegion.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<LeagueSummoner> querySummoner = summonerLookupService.lookupLeagueSummoner(queryRegion.get(), name);

        if(querySummoner.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }else{
            LeagueSummoner summoner = querySummoner.get();

            Optional<Pair<PlayerInfo, LiveMatchInfo>> currentMatchOptional = summonerLookupService.lookupLiveMatch(summoner);

            if(currentMatchOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }else {
                Pair<PlayerInfo, LiveMatchInfo> matchData = currentMatchOptional.get();

                PlayerInfo player = matchData.getFirst();
                LiveMatchInfo liveMatchInfo = matchData.getSecond();

                return new ResponseEntity<>(new LiveMatch(player, liveMatchInfo), HttpStatus.OK);
            }
        }
    }

    @GetMapping("summoner/{region}/{name}")
    public ResponseEntity<LeagueSummoner> summoner(@PathVariable String region, @PathVariable String name) {
        Optional<LeagueShard> queryRegion = Arrays.stream(LeagueShard.values()).filter(r -> r.getValue().equals(region)).findFirst();

        if(queryRegion.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<LeagueSummoner> querySummoner = summonerLookupService.lookupLeagueSummoner(queryRegion.get(), name);

        if(querySummoner.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }else {
            LeagueSummoner summoner = querySummoner.get();

            return new ResponseEntity<>(summoner, HttpStatus.OK);
        }
    }
}
