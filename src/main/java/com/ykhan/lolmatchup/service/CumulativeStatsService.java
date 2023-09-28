package com.ykhan.lolmatchup.service;

import com.ykhan.lolmatchup.data.AverageStats;
import com.ykhan.lolmatchup.model.LeagueSummonerStats;
import com.ykhan.lolmatchup.repository.LeagueSummonerRepository;
import com.ykhan.lolmatchup.repository.LeagueSummonerStatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// TODO not implemented yet
@Service
public class CumulativeStatsService {

    @Autowired
    private LeagueSummonerRepository leagueSummonerRepository;

    private LeagueSummonerStatsRepository leagueSummonerStatsRepository;

    @Transactional
    public Optional<AverageStats> getCumulativeAverage(String rank) {
        List<LeagueSummonerStats> statsForRank = leagueSummonerStatsRepository.findBySummonerRankInfoRank(rank);

        int n = 0;

        // TODO

        return Optional.empty();
    }

    private double getAverage(Stream<Double> values, int sampleSize) {
        return values.reduce(0D, Double::sum).doubleValue() / (double) sampleSize;
    }
}
