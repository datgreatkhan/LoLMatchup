package com.ykhan.lolmatchup.data;

public record MatchupInfo(PlayerInfo player,
                          PlayerInfo matchup,
                          LiveMatchInfo liveMatchInfo)
{}
