package com.ykhan.lolmatchup.data;

import com.ykhan.lolmatchup.data.datadragon.LeagueChampion;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record LiveMatchInfo(long gameStart,
                            long gameLength,
                            Map<TeamType, List<LeagueChampion>> bannedChampions,
                            Map<TeamType, List<PlayerInfo>> players)
{}
