package com.ykhan.lolmatchup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class LeagueSummoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @JsonIgnore
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @Column(name = "unique_id", nullable = false)
    @JsonIgnore
    private String uniqueId;

    @Column(name = "summoner_id", nullable = false)
    @JsonIgnore
    private String summonerId;

    @Column(name = "region", nullable = false)
    private LeagueShard region;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "avatar", nullable = false)
    private Integer avatar;

    @OneToOne(mappedBy = "summoner", cascade = CascadeType.ALL)
    private LeagueSummonerRankInfo rankInfo;

    @OneToOne(mappedBy = "summoner", cascade = CascadeType.ALL)
    private LeagueSummonerStats stats;

    public LeagueSummoner() {}

    public LeagueSummoner(String uniqueId, String summonerId, LeagueShard region, String name, Integer level, Integer avatar) {
        this.uniqueId = uniqueId;
        this.summonerId = summonerId;
        this.region = region;
        this.name = name;
        this.level = level;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public LeagueShard getRegion() {
        return region;
    }

    public void setRegion(LeagueShard region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public LeagueSummonerRankInfo getRankInfo() {
        return rankInfo;
    }

    public void setRankInfo(LeagueSummonerRankInfo rankInfo) {
        this.rankInfo = rankInfo;
    }

    public LeagueSummonerStats getStats() {
        return stats;
    }

    public void setStats(LeagueSummonerStats stats) {
        this.stats = stats;
    }
}
