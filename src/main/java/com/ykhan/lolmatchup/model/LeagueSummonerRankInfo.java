package com.ykhan.lolmatchup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class LeagueSummonerRankInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "summoner_id", nullable = false)
    @JsonIgnore
    private LeagueSummoner summoner;

    @Column(name = "rank", nullable = false)
    private String rank;

    @Column(name = "tier", nullable = false)
    private String tier;

    @Column(name = "lp", nullable = false)
    private Integer leaguePoints;

    @Column(name = "wins", nullable = false)
    private Integer wins;

    @Column(name = "losses", nullable = false)
    private Integer losses;

    public LeagueSummonerRankInfo() {}

    public LeagueSummonerRankInfo(LeagueSummoner summoner, String rank, String tier, Integer leaguePoints, Integer wins, Integer losses) {
        this.summoner = summoner;
        this.rank = rank;
        this.tier = tier;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LeagueSummoner getSummoner() {
        return summoner;
    }

    public void setSummoner(LeagueSummoner summoner) {
        this.summoner = summoner;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}
