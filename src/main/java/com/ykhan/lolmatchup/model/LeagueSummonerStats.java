package com.ykhan.lolmatchup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ykhan.lolmatchup.model.converters.MapConverter;
import jakarta.persistence.*;
import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;

import java.util.List;
import java.util.Map;

/**
 * Represents the stats of a Summoner based on
 * their match history. This is "post-processed" data.
 */
@Entity
@SuppressWarnings("JpaAttributeTypeInspection")
public class LeagueSummonerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "summoner_id")
    @JsonIgnore
    private LeagueSummoner summoner;
    @Column(nullable = false)
    private int maxMatchDuration;
    @Column(nullable = false)
    private double averageMatchDuration;
    @Column(nullable = false)
    private double winrate;
    @Column(nullable = false)
    private double redWinrate;
    @Column(nullable = false)
    private double blueWinrate;
    @Column(nullable = false)
    private LaneType mostSelectedLane;
    @Column(nullable = false)
    @Convert(converter = MapConverter.class)
    @Lob
    private Map<Integer, Double> selectedLaneWinrates;
    @Column(nullable = false)
    private LaneType mostGuessedLane;
    @Column(nullable = false)
    @Convert(converter = MapConverter.class)
    @Lob
    private Map<Integer, Double> guessedLaneWinrates;
    @Column(nullable = false)
    private int mostSelectedChampion;
    @Column(nullable = false)
    @Convert(converter = MapConverter.class)
    @Lob
    private Map<Integer, Double> championWinrates;
    @Column(nullable = false)
    private double averageChampionLevel;
    @Column(nullable = false)
    @Convert(converter = MapConverter.class)
    @Lob
    private Map<Integer, List<Integer>> preferredItemBuilds;
    @Column(nullable = false)
    private int maxCreepScore;
    @Column(nullable = false)
    private double averageCreepScore;
    @Column(nullable = false)
    private int maxVisionScore;
    @Column(nullable = false)
    private double averageVisionScore;
    @Column(nullable = false)
    private int maxGoldEarned;
    @Column(nullable = false)
    private double averageGoldEarned;
    @Column(nullable = false)
    private int maxKills;
    @Column(nullable = false)
    private double averageKills;
    @Column(nullable = false)
    private int maxDeaths;
    @Column(nullable = false)
    private double averageDeaths;
    @Column(nullable = false)
    private int maxAssists;
    @Column(nullable = false)
    private double averageAssists;
    @Column(nullable = false)
    private int maxKillingSprees;
    @Column(nullable = false)
    private double averageKillingSprees;
    @Column(nullable = false)
    private int maxTotalDamageDealt;
    @Column(nullable = false)
    private double averageTotalDamageDealt;
    @Column(nullable = false)
    private int maxTotalDamageReceived;
    @Column(nullable = false)
    private double averageTotalDamageReceived;
    @Column(nullable = false)
    private int maxTotalObjectiveDamage;
    @Column(nullable = false)
    private double averageTotalObjectiveDamage;
    @Column(nullable = false)
    private double firstBloodRate;
    @Column(nullable = false)
    private double firstTowerRate;

    public LeagueSummonerStats() {}

    public LeagueSummonerStats(LeagueSummoner summoner, int maxMatchDuration, double averageMatchDuration, double winrate, double redWinrate, double blueWinrate, LaneType mostSelectedLane, Map<Integer, Double> selectedLaneWinrates, LaneType mostGuessedLane, Map<Integer, Double> guessedLaneWinrates, int mostSelectedChampion, Map<Integer, Double> championWinrates, double averageChampionLevel, Map<Integer, List<Integer>> preferredItemBuilds, int maxCreepScore, double averageCreepScore, int maxVisionScore, double averageVisionScore, int maxGoldEarned, double averageGoldEarned, int maxKills, double averageKills, int maxDeaths, double averageDeaths, int maxAssists, double averageAssists, int maxKillingSprees, double averageKillingSprees, int maxTotalDamageDealt, double averageTotalDamageDealt, int maxTotalDamageReceived, double averageTotalDamageReceived, int maxTotalObjectiveDamage, double averageTotalObjectiveDamage, double firstBloodRate, double firstTowerRate) {
        this.summoner = summoner;
        this.maxMatchDuration = maxMatchDuration;
        this.averageMatchDuration = averageMatchDuration;
        this.winrate = winrate;
        this.redWinrate = redWinrate;
        this.blueWinrate = blueWinrate;
        this.mostSelectedLane = mostSelectedLane;
        this.selectedLaneWinrates = selectedLaneWinrates;
        this.mostGuessedLane = mostGuessedLane;
        this.guessedLaneWinrates = guessedLaneWinrates;
        this.mostSelectedChampion = mostSelectedChampion;
        this.championWinrates = championWinrates;
        this.averageChampionLevel = averageChampionLevel;
        this.preferredItemBuilds = preferredItemBuilds;
        this.maxCreepScore = maxCreepScore;
        this.averageCreepScore = averageCreepScore;
        this.maxVisionScore = maxVisionScore;
        this.averageVisionScore = averageVisionScore;
        this.maxGoldEarned = maxGoldEarned;
        this.averageGoldEarned = averageGoldEarned;
        this.maxKills = maxKills;
        this.averageKills = averageKills;
        this.maxDeaths = maxDeaths;
        this.averageDeaths = averageDeaths;
        this.maxAssists = maxAssists;
        this.averageAssists = averageAssists;
        this.maxKillingSprees = maxKillingSprees;
        this.averageKillingSprees = averageKillingSprees;
        this.maxTotalDamageDealt = maxTotalDamageDealt;
        this.averageTotalDamageDealt = averageTotalDamageDealt;
        this.maxTotalDamageReceived = maxTotalDamageReceived;
        this.averageTotalDamageReceived = averageTotalDamageReceived;
        this.maxTotalObjectiveDamage = maxTotalObjectiveDamage;
        this.averageTotalObjectiveDamage = averageTotalObjectiveDamage;
        this.firstBloodRate = firstBloodRate;
        this.firstTowerRate = firstTowerRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeagueSummoner getSummoner() {
        return summoner;
    }

    public void setSummoner(LeagueSummoner summoner) {
        this.summoner = summoner;
    }

    public int getMaxMatchDuration() {
        return maxMatchDuration;
    }

    public void setMaxMatchDuration(int maxMatchDuration) {
        this.maxMatchDuration = maxMatchDuration;
    }

    public double getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(double averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }

    public double getRedWinrate() {
        return redWinrate;
    }

    public void setRedWinrate(double redWinrate) {
        this.redWinrate = redWinrate;
    }

    public double getBlueWinrate() {
        return blueWinrate;
    }

    public void setBlueWinrate(double blueWinrate) {
        this.blueWinrate = blueWinrate;
    }

    public LaneType getMostSelectedLane() {
        return mostSelectedLane;
    }

    public void setMostSelectedLane(LaneType mostSelectedLane) {
        this.mostSelectedLane = mostSelectedLane;
    }

    public Map<Integer, Double> getSelectedLaneWinrates() {
        return selectedLaneWinrates;
    }

    public void setSelectedLaneWinrates(Map<Integer, Double> selectedLaneWinrates) {
        this.selectedLaneWinrates = selectedLaneWinrates;
    }

    public LaneType getMostGuessedLane() {
        return mostGuessedLane;
    }

    public void setMostGuessedLane(LaneType mostGuessedLane) {
        this.mostGuessedLane = mostGuessedLane;
    }

    public Map<Integer, Double> getGuessedLaneWinrates() {
        return guessedLaneWinrates;
    }

    public void setGuessedLaneWinrates(Map<Integer, Double> guessedLaneWinrates) {
        this.guessedLaneWinrates = guessedLaneWinrates;
    }

    public int getMostSelectedChampion() {
        return mostSelectedChampion;
    }

    public void setMostSelectedChampion(int mostSelectedChampion) {
        this.mostSelectedChampion = mostSelectedChampion;
    }

    public Map<Integer, Double> getChampionWinrates() {
        return championWinrates;
    }

    public void setChampionWinrates(Map<Integer, Double> championWinrates) {
        this.championWinrates = championWinrates;
    }

    public double getAverageChampionLevel() {
        return averageChampionLevel;
    }

    public void setAverageChampionLevel(double averageChampionLevel) {
        this.averageChampionLevel = averageChampionLevel;
    }

    public Map<Integer, List<Integer>> getPreferredItemBuilds() {
        return preferredItemBuilds;
    }

    public void setPreferredItemBuilds(Map<Integer, List<Integer>> preferredItemBuilds) {
        this.preferredItemBuilds = preferredItemBuilds;
    }

    public int getMaxCreepScore() {
        return maxCreepScore;
    }

    public void setMaxCreepScore(int maxCreepScore) {
        this.maxCreepScore = maxCreepScore;
    }

    public double getAverageCreepScore() {
        return averageCreepScore;
    }

    public void setAverageCreepScore(double averageCreepScore) {
        this.averageCreepScore = averageCreepScore;
    }

    public int getMaxVisionScore() {
        return maxVisionScore;
    }

    public void setMaxVisionScore(int maxVisionScore) {
        this.maxVisionScore = maxVisionScore;
    }

    public double getAverageVisionScore() {
        return averageVisionScore;
    }

    public void setAverageVisionScore(double averageVisionScore) {
        this.averageVisionScore = averageVisionScore;
    }

    public int getMaxGoldEarned() {
        return maxGoldEarned;
    }

    public void setMaxGoldEarned(int maxGoldEarned) {
        this.maxGoldEarned = maxGoldEarned;
    }

    public double getAverageGoldEarned() {
        return averageGoldEarned;
    }

    public void setAverageGoldEarned(double averageGoldEarned) {
        this.averageGoldEarned = averageGoldEarned;
    }

    public int getMaxKills() {
        return maxKills;
    }

    public void setMaxKills(int maxKills) {
        this.maxKills = maxKills;
    }

    public double getAverageKills() {
        return averageKills;
    }

    public void setAverageKills(double averageKills) {
        this.averageKills = averageKills;
    }

    public int getMaxDeaths() {
        return maxDeaths;
    }

    public void setMaxDeaths(int maxDeaths) {
        this.maxDeaths = maxDeaths;
    }

    public double getAverageDeaths() {
        return averageDeaths;
    }

    public void setAverageDeaths(double averageDeaths) {
        this.averageDeaths = averageDeaths;
    }

    public int getMaxAssists() {
        return maxAssists;
    }

    public void setMaxAssists(int maxAssists) {
        this.maxAssists = maxAssists;
    }

    public double getAverageAssists() {
        return averageAssists;
    }

    public void setAverageAssists(double averageAssists) {
        this.averageAssists = averageAssists;
    }

    public int getMaxKillingSprees() {
        return maxKillingSprees;
    }

    public void setMaxKillingSprees(int maxKillingSprees) {
        this.maxKillingSprees = maxKillingSprees;
    }

    public double getAverageKillingSprees() {
        return averageKillingSprees;
    }

    public void setAverageKillingSprees(double averageKillingSprees) {
        this.averageKillingSprees = averageKillingSprees;
    }

    public int getMaxTotalDamageDealt() {
        return maxTotalDamageDealt;
    }

    public void setMaxTotalDamageDealt(int maxTotalDamageDealt) {
        this.maxTotalDamageDealt = maxTotalDamageDealt;
    }

    public double getAverageTotalDamageDealt() {
        return averageTotalDamageDealt;
    }

    public void setAverageTotalDamageDealt(double averageTotalDamageDealt) {
        this.averageTotalDamageDealt = averageTotalDamageDealt;
    }

    public int getMaxTotalDamageReceived() {
        return maxTotalDamageReceived;
    }

    public void setMaxTotalDamageReceived(int maxTotalDamageReceived) {
        this.maxTotalDamageReceived = maxTotalDamageReceived;
    }

    public double getAverageTotalDamageReceived() {
        return averageTotalDamageReceived;
    }

    public void setAverageTotalDamageReceived(double averageTotalDamageReceived) {
        this.averageTotalDamageReceived = averageTotalDamageReceived;
    }

    public int getMaxTotalObjectiveDamage() {
        return maxTotalObjectiveDamage;
    }

    public void setMaxTotalObjectiveDamage(int maxTotalObjectiveDamage) {
        this.maxTotalObjectiveDamage = maxTotalObjectiveDamage;
    }

    public double getAverageTotalObjectiveDamage() {
        return averageTotalObjectiveDamage;
    }

    public void setAverageTotalObjectiveDamage(double averageTotalObjectiveDamage) {
        this.averageTotalObjectiveDamage = averageTotalObjectiveDamage;
    }

    public double getFirstBloodRate() {
        return firstBloodRate;
    }

    public void setFirstBloodRate(double firstBloodRate) {
        this.firstBloodRate = firstBloodRate;
    }

    public double getFirstTowerRate() {
        return firstTowerRate;
    }

    public void setFirstTowerRate(double firstTowerRate) {
        this.firstTowerRate = firstTowerRate;
    }
}
