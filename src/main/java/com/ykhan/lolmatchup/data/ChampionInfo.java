package com.ykhan.lolmatchup.data;

import com.ykhan.lolmatchup.data.datadragon.LeagueChampion;
import com.ykhan.lolmatchup.data.datadragon.LeagueItem;
import com.ykhan.lolmatchup.data.helper.Pair;

import java.util.List;

public record ChampionInfo(LeagueChampion champion,
                           double winrate,
                           List<Pair<Integer, LeagueItem>> preferredItems)
{}
