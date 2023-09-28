package com.ykhan.lolmatchup.repository;

import com.ykhan.lolmatchup.model.LeagueSummonerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LeagueSummonerStatsRepository extends JpaRepository<LeagueSummonerStats, Long>, JpaSpecificationExecutor<LeagueSummonerStats> {

    List<LeagueSummonerStats> findBySummonerRankInfoRank(String rank);
}
