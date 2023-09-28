package com.ykhan.lolmatchup.data.processing;


import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;

import java.util.List;

public record LeagueMatchStats(String summonerPUUID,
                               int matchDuration,
                               boolean won,
                               TeamType team,
                               LaneType selectedLane,
                               LaneType guessedLane,
                               int championId,
                               int championLevel,
                               List<Integer> items,
                               int creepScore,
                               int visionScore,
                               int goldEarned,
                               int kills,
                               int deaths,
                               int assists,
                               int killingSprees,
                               int totalDamageDealt,
                               int totalDamageTaken,
                               int totalObjectiveDamage,
                               boolean firstBlood,
                               boolean firstTower) {}