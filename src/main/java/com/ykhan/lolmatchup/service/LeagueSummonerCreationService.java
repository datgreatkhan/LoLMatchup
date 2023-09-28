package com.ykhan.lolmatchup.service;

import com.ykhan.lolmatchup.config.LeagueAPIHandler;
import com.ykhan.lolmatchup.exception.SummonerNotFoundException;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import com.ykhan.lolmatchup.model.LeagueSummonerRankInfo;
import com.ykhan.lolmatchup.model.LeagueSummonerStats;
import com.ykhan.lolmatchup.repository.LeagueSummonerRepository;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.pojo.lol.league.LeagueEntry;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Service that deals with creation of our own Summoner model,
 * so we can save and track their stats.
 */
@Service
public class LeagueSummonerCreationService {

    private final Logger logger = LoggerFactory.getLogger(LeagueSummonerCreationService.class);

    @Autowired
    private LeagueAPIHandler leagueAPI;

    @Autowired
    private LeagueSummonerRepository leagueSummonerRepository;

    @Autowired
    private LeagueSummonerStatsService leagueSummonerStatsService;

    @Async
    public CompletableFuture<LeagueSummoner> create(LeagueShard region, String name) {
        Summoner summoner = leagueAPI.get().getSummonerAPI().getSummonerByName(region, name);

        if(summoner == null) {
            return CompletableFuture.failedFuture(new SummonerNotFoundException("Summoner not found for Region " + region));
        }

        List<LeagueEntry> leagueEntries = summoner.getLeagueEntry();

        LeagueEntry latestRankedEntry = null;

        if(!leagueEntries.isEmpty()) {
            Optional<LeagueEntry> leagueQuery = leagueEntries.stream().filter(entry -> entry.getQueueType() == GameQueueType.RANKED_SOLO_5X5).findFirst();

            if(leagueQuery.isPresent()) {
                latestRankedEntry = leagueQuery.get();
            }
        }

        LeagueSummoner leagueSummoner = new LeagueSummoner(summoner.getPUUID(), summoner.getSummonerId(), region, name, summoner.getSummonerLevel(), summoner.getProfileIconId());

        if(latestRankedEntry != null) {
            String rank = latestRankedEntry.getTier();
            String tier = latestRankedEntry.getRank();
            Integer lp = latestRankedEntry.getLeaguePoints();
            Integer wins = latestRankedEntry.getWins();
            Integer losses = latestRankedEntry.getLosses();

            LeagueSummonerRankInfo rankInfo = new LeagueSummonerRankInfo(leagueSummoner, rank, tier, lp, wins, losses);
            leagueSummoner.setRankInfo(rankInfo);
        }else{
            leagueSummoner.setRankInfo(new LeagueSummonerRankInfo(leagueSummoner, "Unranked", "N/A", 0, 0, 0));

            //return CompletableFuture.failedFuture(new SummonerNoRankedHistoryException("Summoner has no Ranked history."));
        }

        try {
            LeagueSummonerStats summonerStats = leagueSummonerStatsService.collectStats(leagueSummoner).get();

            leagueSummoner.setStats(summonerStats);

            leagueSummonerRepository.save(leagueSummoner);

            return CompletableFuture.completedFuture(leagueSummoner);
        }catch(IllegalArgumentException | OptimisticLockingFailureException e) {
            return CompletableFuture.failedFuture(e);
        }catch(InterruptedException | ExecutionException e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
