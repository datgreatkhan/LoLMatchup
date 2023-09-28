package com.ykhan.lolmatchup.data;

import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;

public record LaneInfo(LaneType lane,
                       double winrate) {
}
