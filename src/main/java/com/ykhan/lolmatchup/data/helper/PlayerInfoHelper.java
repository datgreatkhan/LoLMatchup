package com.ykhan.lolmatchup.data.helper;

import com.ykhan.lolmatchup.config.LeagueAPIHandler;
import com.ykhan.lolmatchup.data.ChampionInfo;
import com.ykhan.lolmatchup.data.PlayerInfo;
import com.ykhan.lolmatchup.data.datadragon.LeagueItem;
import com.ykhan.lolmatchup.model.LeagueSummoner;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorParticipant;

import java.util.List;
import java.util.Map;

public class PlayerInfoHelper {

    public static PlayerInfo getPlayerInfo(LeagueAPIHandler leagueAPI, LeagueSummoner leagueSummoner, SpectatorParticipant participant) {
        int championId = participant.getChampionId();
        Map<Integer, Double> championWinrates = leagueSummoner.getStats().getChampionWinrates();
        Map<Integer, List<Integer>> championItemBuilds = leagueSummoner.getStats().getPreferredItemBuilds();
        boolean hasPlayedChampionBefore = championWinrates.containsKey(championId);

        return new PlayerInfo(leagueSummoner, participant.getTeam(), new ChampionInfo(
                leagueAPI.getLeagueChampions().get(championId + ""),
                hasPlayedChampionBefore ? championWinrates.get(championId) : -1.0D,
                hasPlayedChampionBefore ? (championItemBuilds.get(championId).stream().filter(id -> id != 0).map(itemId -> new Pair<Integer, LeagueItem>(itemId, leagueAPI.getLeagueItems().get("" + itemId)))).toList() : null
        ));
    }
}
