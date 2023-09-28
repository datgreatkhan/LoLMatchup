<script setup>
import {getChampionIcon} from "@/util/assets";
import {getRank} from "@/util/naming";

const props = defineProps({
  summoner: Object,
  matchInfo: Object
});
</script>

<template>
  <div class="w-100 mt-16 d-flex flex-row justify-space-between align-center">
    <v-row class="justify-space-evenly">
      <v-col cols="10" class="d-flex flex-column bg-grey-darken-2 rounded">
        <p class="align-self-center">{{ props.summoner.name }}'s Match Info</p>

        <v-divider />

        <p class="align-self-center">{{ new Date(props.matchInfo.gameStart).toString() }}</p>

        <p class="align-self-center mt-4">Bans</p>

        <v-row class="w-100 mt-2">
          <v-col class="d-flex justify-space-evenly">
            <div class="d-flex flex-row justify-space-evenly align-center" style="width: 160px">
              <v-img v-for="champion in props.matchInfo.bannedChampions.RED" :src="getChampionIcon(champion.id)" height="32" />
            </div>
          </v-col>
          <v-col class="d-flex justify-center">
            <div class="d-flex flex-row justify-space-evenly align-center" style="width: 160px">
              <v-img v-for="champion in props.matchInfo.bannedChampions.BLUE" :src="getChampionIcon(champion.id)" height="32" />
            </div>
          </v-col>
        </v-row>

        <p class="align-self-center mt-2 mb-2">Players</p>

        <v-row class="w-100 mt-2">
          <v-col cols="6">
            <div v-for="playerInfo in props.matchInfo.players.RED" class="mt-2">
              <div class="d-flex flex-row justify-start align-center">
                <v-img :src="getChampionIcon(playerInfo.championInfo.champion.id)"  width="36" height="36" max-height="36" max-width="36" />
                <p class="ml-8">{{ playerInfo.summoner.name }} ({{ getRank(playerInfo.summoner.rankInfo.rank, playerInfo.summoner.rankInfo.tier) }} - {{ playerInfo.championInfo.winrate === -1.0 ? 'N/A' : Math.round(playerInfo.championInfo.winrate * 100) + '%' }})</p>
              </div>
            </div>
          </v-col>
          <v-col cols="6">
            <div v-for="playerInfo in props.matchInfo.players.BLUE" class="mt-2">
              <div class="d-flex flex-row justify-start align-center">
                <v-img :src="getChampionIcon(playerInfo.championInfo.champion.id)"  width="36" height="36" max-height="36" max-width="36" />
                <p class="ml-8">{{ playerInfo.summoner.name }} ({{ getRank(playerInfo.summoner.rankInfo.rank, playerInfo.summoner.rankInfo.tier) }} - {{ playerInfo.championInfo.winrate === -1.0 ? 'N/A' : Math.round(playerInfo.championInfo.winrate * 100) + '%' }})</p>
              </div>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>
