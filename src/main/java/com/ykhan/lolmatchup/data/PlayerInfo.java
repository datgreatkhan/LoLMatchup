package com.ykhan.lolmatchup.data;

import com.ykhan.lolmatchup.model.LeagueSummoner;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;

public record PlayerInfo(LeagueSummoner summoner, // stats & rank info here
                         /*LaneInfo laneInfo,*/
                         TeamType team,
                         ChampionInfo championInfo) {}
