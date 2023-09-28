<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";

import {lookup, profile} from "@/api/api";
import {
  getAbilityIcon,
  getChampionBanner,
  getChampionIcon,
  getItemIcon,
  getPassiveIcon,
  getRankEmblem,
  getSummonerIcon
} from "@/util/assets";

import MatchTable from "@/components/match/MatchTable.vue";
import {getRank} from "../util/naming";
import StatTagDisplay from "@/components/shared/StatTagDisplay.vue";

const props = defineProps({
  region: String,
  name: String
})

const router = useRouter()

const isLoading = ref(true);

const summoner = ref(undefined);
const foundSummoner = ref(false);

function goToHome() {
  router.replace("/");
}

onMounted(() => {
  profile(props.region, props.name).then(response => {
    console.log(response.data);

    if(response.status === 200) {
      foundSummoner.value = true;
    }

    summoner.value = response.data;
    isLoading.value = false;
  })
})
</script>

<template>
  <v-container class="fill-height w-100">
    <div class="w-100 fill-height d-flex justify-center">
      <v-progress-circular v-if="isLoading" indeterminate />

      <div v-if="foundSummoner && !isLoading" class="w-66 h-25 pl-8 pr-8 pt-8 d-flex flex-column justify-space-between">
        <div class="d-flex flex-row justify-center align-center">
          <v-badge :content="summoner.level" location="top start" color="amber" class="mr-8">
            <v-img width="96" height="96" :src="getSummonerIcon(summoner.avatar)" />
          </v-badge>
          <div class="d-flex flex-column justify-start">
            <h1>{{ summoner.name }} - {{ getRank(summoner.rankInfo.rank, summoner.rankInfo.tier) }} - ({{ summoner.region }})</h1>
          </div>
        </div>

        <v-divider class="mb-8 mt-8" />

        <div class="w-100 d-flex flex-row justify-space-between align-center">
          <v-row class="justify-space-evenly">
            <v-col cols="3" class="p-4 d-flex flex-column bg-grey-darken-2 rounded">
              <span class="align-self-center">{{ summoner.name }}'s Summary</span>

              <v-divider />

              <div class="fill-height d-flex flex-column justify-space-evenly align-center">
                <span>Winrate: {{ Math.round(summoner.stats.winrate * 100) }}% - ({{ Math.round(summoner.stats.redWinrate * 100) }}% Red, {{ Math.round(summoner.stats.blueWinrate * 100) }}% Blue)</span>
                <span>Kills/Deaths/Assists: {{ Math.round(summoner.stats.averageKills) }}/{{ Math.round(summoner.stats.averageDeaths) }}/{{ Math.round(summoner.stats.averageAssists) }}</span>
                <!-- <p class="align-self-center">0.0/0.0/0.0 Average KDA (place-holder)</p> -->
                <StatTagDisplay
                  :summoner="summoner"
                />
                <div class="d-flex flex-column">
                  <v-row>
                    <v-col>
                      <span>Main Lane: {{ summoner.stats.mostSelectedLane }}</span>
                    </v-col>
                    <v-col>
                      <span>Main Champion: TODO</span>
                    </v-col>
                  </v-row>
                </div>
              </div>
            </v-col>

            <v-col cols="7" class="d-flex flex-column p-4 bg-grey-darken-2 rounded">
              <span>Match History? Champion winrates?</span>
            </v-col>
          </v-row>
        </div>
      </div>
      <div v-if="!foundSummoner && !isLoading">
        <h2>This summoner does not exist. Double check the region and name.</h2>
        <a @click="goToHome">Click here to go back to the home page.</a>
      </div>
    </div>
  </v-container>
</template>
