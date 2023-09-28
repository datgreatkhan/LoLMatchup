package com.ykhan.lolmatchup.data;

import java.util.Map;

public record AverageStats(
        double averageMatchDuration,
        double averageWinrate,
        double averageRedWinrate,
        double averageBlueWinrate,
        Map<Integer, Double> averageLaneWinrates,
        Map<Integer, Double> averageChampionWinrates,
        double averageCreepScore,
        double averageVisionScore,
        double averageGoldEarned,
        double averageKills,
        double averageDeaths,
        double averageAssists,
        double averageKillingSprees,
        double averageTotalDamageDealt,
        double averageTotalDamageReceived,
        double averageTotalObjectiveDamage,
        double averageFirstBloodRate,
        double averageFirstTowerRate)
{}
