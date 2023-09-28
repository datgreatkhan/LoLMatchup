package com.ykhan.lolmatchup.service;

import com.ykhan.lolmatchup.config.LeagueAPIHandler;
import com.ykhan.lolmatchup.data.processing.LeagueMatchStats;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import com.ykhan.lolmatchup.model.LeagueSummonerStats;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;
import no.stelar7.api.r4j.basic.constants.types.lol.MatchlistMatchType;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * The server that collects and partially processes some desired stats of a player.
 */
@Service
public class LeagueSummonerStatsService {

    /**
     * The number of matches to fetch to process the stats of the player.
     * This number is intentionally low, or we hit rate limits.
     * The Riot Development API key only supports 100 requests per 2 minutes.
     */
    private static final int MATCH_HISTORY_LENGTH = 5;

    @Autowired
    private LeagueAPIHandler leagueAPI;

    /**
     * The current season, used for querying match history.
     * This includes both splits - so season 13.1 and 13.2
     */
    private static final Long SEASON_13 = 1673326800L;
    private final Logger logger = LoggerFactory.getLogger(LeagueSummonerStatsService.class);

    @Async
    public CompletableFuture<LeagueSummonerStats> collectStats(LeagueSummoner summoner) {
        List<LeagueMatchStats> matches;

        try {
            matches = getMatchHistoryStats(summoner).get();
        }catch(InterruptedException | ExecutionException e) {
            return CompletableFuture.failedFuture(e);
        }

        List<Integer> matchDurations = matches.stream().map(LeagueMatchStats::matchDuration).toList();

        int maxMatchDuration = matchDurations.stream().max(Integer::compareTo).get();
        double averageMatchDuration = matchDurations.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<LeagueMatchStats> wonMatches = matches.stream().filter(LeagueMatchStats::won).toList();

        double winrate = (double) wonMatches.size() / (double) matches.size();
        double redWinrate = (double) wonMatches.stream().filter(match -> match.team() == TeamType.RED).count() / (double) matches.size();
        double blueWinrate = 1.00D - redWinrate;

        List<Integer> selectedLanes = matches.stream().map(match -> match.selectedLane().ordinal()).toList();

        LaneType mostSelectedLane = LaneType.values()[selectedLanes.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).entrySet().stream().max((a, b) -> {
            if(a.getValue() > b.getValue()) {
                return 1;
            }else if (a.getValue() < b.getValue()) {
                return -1;
            }else {
                return 0;
            }
        }).get().getKey()];

        Map<Integer, Double> selectedLaneWinrates = matches.stream().collect(Collectors.groupingBy(LeagueMatchStats::selectedLane)).entrySet().stream().collect(Collectors.toMap(laneMatches -> laneMatches.getKey().ordinal(), laneMatches -> (double) laneMatches.getValue().stream().filter(LeagueMatchStats::won).count() / (double) laneMatches.getValue().size()));

        List<Integer> guessedLanes = matches.stream().map(match -> match.guessedLane().ordinal()).toList();

        LaneType mostGuessedLane = LaneType.values()[guessedLanes.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).entrySet().stream().max((a, b) -> {
            if(a.getValue() > b.getValue()) {
                return 1;
            }else if (a.getValue() < b.getValue()) {
                return -1;
            }else {
                return 0;
            }
        }).get().getKey()];

        Map<Integer, Double> guessedLaneWinrates = matches.stream().collect(Collectors.groupingBy(LeagueMatchStats::guessedLane)).entrySet().stream().collect(Collectors.toMap(laneMatches -> laneMatches.getKey().ordinal(), laneMatches -> (double) laneMatches.getValue().stream().filter(LeagueMatchStats::won).count() / (double) laneMatches.getValue().size()));

        int mostSelectedChampion = matches.stream().map(LeagueMatchStats::championId).collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).entrySet().stream().max((a, b) -> {
            if(a.getValue() > b.getValue()) {
                return 1;
            }else if (a.getValue() < b.getValue()) {
                return -1;
            }else {
                return 0;
            }
        }).get().getKey();

        Map<Integer, Double> championWinrates = matches.stream().collect(Collectors.groupingBy(LeagueMatchStats::championId)).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, champMatches -> (double) champMatches.getValue().stream().filter(m -> m.won()).count() / (double) champMatches.getValue().size()));
        
        double averageChampionLevel = matches.stream().map(LeagueMatchStats::championLevel).reduce(0, (a, b) -> a + b).doubleValue() / (double) matches.size();

        Map<Integer, List<Integer>> preferredItemBuilds = matches.stream().collect(Collectors.groupingBy(LeagueMatchStats::championId)).entrySet().stream().collect(Collectors.toMap(champMatches -> champMatches.getKey(), champMatches -> champMatches.getValue().stream().collect(Collectors.groupingBy(LeagueMatchStats::items, Collectors.counting())).entrySet().stream().max((a, b) -> {
            if(a.getValue() > b.getValue()) {
                return 1;
            }else if (a.getValue() < b.getValue()) {
                return -1;
            }else {
                return 0;
            }
        }).get().getKey()));

        List<Integer> creepScores = matches.stream().map(LeagueMatchStats::creepScore).toList();

        int maxCreepScore = creepScores.stream().max(Integer::compareTo).get();
        double averageCreepScore = creepScores.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> visionScores = matches.stream().map(LeagueMatchStats::visionScore).toList();

        int maxVisionScore = visionScores.stream().max(Integer::compareTo).get();
        double averageVisionScore = visionScores.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> gold = matches.stream().map(LeagueMatchStats::goldEarned).toList();

        int maxGoldEarned = gold.stream().max(Integer::compareTo).get();
        double averageGoldEarned = gold.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> kills = matches.stream().map(LeagueMatchStats::kills).toList();

        int maxKills = kills.stream().max(Integer::compareTo).get();
        double averageKills = kills.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> deaths = matches.stream().map(LeagueMatchStats::deaths).toList();

        int maxDeaths = deaths.stream().max(Integer::compareTo).get();
        double averageDeaths = deaths.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> assists  = matches.stream().map(LeagueMatchStats::assists).toList();

        int maxAssists = assists.stream().max(Integer::compareTo).get();
        double averageAssists = assists.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> killingSprees  = matches.stream().map(LeagueMatchStats::killingSprees).toList();

        int maxKillingSprees = killingSprees.stream().max(Integer::compareTo).get();
        double averageKillingSprees = killingSprees.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> totalDamageDealt = matches.stream().map(LeagueMatchStats::totalDamageDealt).toList();

        int maxTotalDamageDealt = totalDamageDealt.stream().max(Integer::compareTo).get();
        double averageTotalDamageDealt = totalDamageDealt.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> totalDamageReceived = matches.stream().map(LeagueMatchStats::totalDamageTaken).toList();

        int maxTotalDamageReceived = totalDamageReceived.stream().max(Integer::compareTo).get();
        double averageTotalDamageReceived = totalDamageReceived.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        List<Integer> totalObjectiveDamage = matches.stream().map(LeagueMatchStats::totalObjectiveDamage).toList();

        int maxTotalObjectiveDamage = totalObjectiveDamage.stream().max(Integer::compareTo).get();
        double averageTotalObjectiveDamage = totalObjectiveDamage.stream().reduce(0, Integer::sum).doubleValue() / (double) matches.size();

        double firstBloodRate = (double) matches.stream().map(LeagueMatchStats::firstBlood).filter(b -> b).count() / (double) matches.size();
        double firstTowerRate = (double) matches.stream().map(LeagueMatchStats::firstTower).filter(b -> b).count() / (double) matches.size();

        logger.info("Summoner Stat Summary for " + summoner.getName() + ":");
        logger.info("average match duration: " + averageMatchDuration);
        logger.info("highest match duration: " + maxMatchDuration);
        logger.info("total winrate: " + winrate);
        logger.info("winrate (red): " + redWinrate + ", winrate (blue): " + blueWinrate);
        logger.info("most selected lane: " + mostSelectedLane);
        logger.info("most selected champ: " + leagueAPI.getLeagueChampions().get("" + mostSelectedChampion).getId());
        logger.info("average KDA: " + averageKills + " / " + averageDeaths + " / " + averageAssists);
        logger.info("most kills: " + maxKills + ", most deaths: " + maxDeaths + ", most assists: " + maxAssists);
        logger.info("average CS: " + averageCreepScore + ", max CS: " + maxCreepScore);
        logger.info("average vision score: " + averageVisionScore + ", max vision score: " + maxVisionScore);
        logger.info(selectedLaneWinrates.entrySet().stream().collect(Collectors.toMap(pair -> LaneType.values()[pair.getKey()], Map.Entry::getValue)).toString());
        logger.info(championWinrates.toString());
        logger.info(preferredItemBuilds.toString());

        LeagueSummonerStats summonerStats = new LeagueSummonerStats(
                summoner,
                maxMatchDuration,
                averageMatchDuration,
                winrate,
                redWinrate,
                blueWinrate,
                mostSelectedLane,
                selectedLaneWinrates,
                mostGuessedLane,
                guessedLaneWinrates,
                mostSelectedChampion,
                championWinrates,
                averageChampionLevel,
                preferredItemBuilds,
                maxCreepScore,
                averageCreepScore,
                maxVisionScore,
                averageVisionScore,
                maxGoldEarned,
                averageGoldEarned,
                maxKills,
                averageKills,
                maxDeaths,
                averageDeaths,
                maxAssists,
                averageAssists,
                maxKillingSprees,
                averageKillingSprees,
                maxTotalDamageDealt,
                averageTotalDamageDealt,
                maxTotalDamageReceived,
                averageTotalDamageReceived,
                maxTotalObjectiveDamage,
                averageTotalObjectiveDamage,
                firstBloodRate,
                firstTowerRate
        );

        return CompletableFuture.completedFuture(summonerStats);
    }

    @Async
    public CompletableFuture<ArrayList<LeagueMatchStats>> getMatchHistoryStats(LeagueSummoner summoner) {
        List<String> matchHistory = leagueAPI.get().getMatchAPI().getMatchList(summoner.getRegion().toRegionShard(), summoner.getUniqueId(), GameQueueType.TEAM_BUILDER_RANKED_SOLO, MatchlistMatchType.RANKED, 0, MATCH_HISTORY_LENGTH, SEASON_13, Instant.now().getEpochSecond());

        logger.info("# of matches: " + matchHistory.size());

        if(matchHistory.isEmpty()) {
            return CompletableFuture.completedFuture(new ArrayList<LeagueMatchStats>());
        }

        ArrayList<LeagueMatchStats> leagueMatchStats = new ArrayList<LeagueMatchStats>();

        // These matches are ordered descending (endTime -> startTime)
        // so no sorting or reversal required
        for(String matchId : matchHistory) {
            LOLMatch leagueMatch = leagueAPI.get().getMatchAPI().getMatch(summoner.getRegion().toRegionShard(), matchId);

            // for some reason its null sometimes?
            if(leagueMatch == null) {
                continue;
            }

            int matchDuration = leagueMatch.getGameDuration();

            Optional<MatchParticipant> summonerParticipantOptional = leagueMatch.getParticipants().stream().filter(part -> part.getSummonerId().equals(summoner.getSummonerId())).findFirst();

            LeagueMatchStats matchStats;

            if(summonerParticipantOptional.isPresent()) {
                MatchParticipant participant = summonerParticipantOptional.get();

                boolean won = participant.didWin();
                TeamType team = participant.getTeam();
                LaneType selectedLane = participant.getChampionSelectLane();
                LaneType guessedLane = participant.getGameDeterminedPosition();
                int championId = participant.getChampionId();
                int championLevel = participant.getChampionLevel();
                List<Integer> items = Arrays.stream((new Integer[] {
                        participant.getItem0(),
                        participant.getItem1(),
                        participant.getItem2(),
                        participant.getItem3(),
                        participant.getItem4(),
                        participant.getItem5(),
                        participant.getItem6()
                })).toList();

                int creepScore = participant.getTotalMinionsKilled();
                int visionScore = participant.getVisionScore();
                int goldEarned = participant.getGoldEarned();
                int kills = participant.getKills();
                int deaths = participant.getDeaths();
                int assists = participant.getAssists();
                int killingSprees = participant.getKillingSprees();
                int totalDamageDealt = participant.getTotalDamageDealt();
                int totalDamageTaken = participant.getTotalDamageTaken();
                int totalObjectiveDamage = participant.getDamageDealtToObjectives();
                boolean gotFirstBlood = participant.isFirstBloodKill();
                boolean gotFirstTower = participant.isFirstTowerAssist();

                matchStats = new LeagueMatchStats(
                        participant.getPuuid(),
                        matchDuration,
                        won,
                        team,
                        selectedLane,
                        guessedLane,
                        championId,
                        championLevel,
                        items,
                        creepScore,
                        visionScore,
                        goldEarned,
                        kills,
                        deaths,
                        assists,
                        killingSprees,
                        totalDamageDealt,
                        totalDamageTaken,
                        totalObjectiveDamage,
                        gotFirstBlood,
                        gotFirstTower
                );

                leagueMatchStats.add(matchStats);
            }
        }

        return CompletableFuture.completedFuture(leagueMatchStats);
    }
}
