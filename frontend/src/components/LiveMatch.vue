<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";

import {lookup} from "@/api/api";
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

const liveMatch = ref(undefined);
const hasLiveMatch = ref(false);

const summoner = computed(() => liveMatch.value.player.summoner)
const team = computed(() => liveMatch.value.player.team)
const championInfo = computed(() => liveMatch.value.player.championInfo)

const matchInfo = computed(() => liveMatch.value.liveMatchInfo)

function goToHome() {
  router.replace("/");
}

onMounted(() => {
  lookup(props.region, props.name).then(response => {
    console.log(response.data);

    if(response.status === 200) {
      hasLiveMatch.value = true;
    }

    liveMatch.value = response.data;
    isLoading.value = false;
  })
})
</script>

<template>
  <v-container class="fill-height w-100">
    <div class="w-100 fill-height d-flex justify-center">
      <v-progress-circular v-if="isLoading" indeterminate />

      <div v-if="hasLiveMatch && !isLoading" class="w-66 h-25 pl-8 pr-8 pt-8 d-flex flex-column justify-space-between">
        <div class="d-flex flex-row justify-center align-center">
          <v-badge :content="summoner.level" location="top start" color="amber" class="mr-8">
            <v-img width="96" height="96" :src="getSummonerIcon(summoner.avatar)" />
          </v-badge>
          <div class="d-flex flex-column justify-start">
            <h1>{{ summoner.name }} - {{ getRank(summoner.rankInfo.rank, summoner.rankInfo.tier) }} - ({{ summoner.region }})</h1>
            <p>Winrate: {{ Math.round(summoner.stats.winrate * 100) }}% - ({{ Math.round(summoner.stats.redWinrate * 100) }}% Red, {{ Math.round(summoner.stats.blueWinrate * 100) }}% Blue)</p>
            <p>Kills/Deaths/Assists: {{ Math.round(summoner.stats.averageKills) }}/{{ Math.round(summoner.stats.averageDeaths) }}/{{ Math.round(summoner.stats.averageAssists) }}</p>
          </div>
        </div>

        <v-divider class="mb-8 mt-8" />

        <div class="w-100 d-flex flex-row justify-space-between align-center">
          <v-row class="justify-space-evenly">
            <v-col cols="3" class="p-4 d-flex flex-column bg-grey-darken-2 rounded">
              <span class="align-self-center">{{ summoner.name }}'s {{ championInfo.champion.id }}</span>

              <v-divider />

              <div class="fill-height d-flex flex-column justify-space-evenly align-center">
                <span>{{ Math.round(championInfo.winrate * 100)}}% Winrate on {{ championInfo.champion.id }}</span>
                <!-- <p class="align-self-center">0.0/0.0/0.0 Average KDA (place-holder)</p> -->
                <StatTagDisplay
                  :summoner="summoner"
                  :championInfo="championInfo"
                />
                <div class="d-flex flex-column">
                  <span>Preferred Item Build</span>
                  <div class="d-flex flex-row flex-wrap align-center justify-center" style="width: 160px">
                    <v-img
                      v-for="item in championInfo.preferredItems" :text="item.second.description"
                      :src="getItemIcon(item.first)"
                      max-width="32"
                      max-height="32"
                    >
                      <v-tooltip
                        :text="item.second.plaintext"
                        activator="parent"
                        location="bottom"
                      />
                    </v-img>
                  </div>
                </div>
              </div>
            </v-col>

            <v-col cols="7" class="d-flex flex-column p-4 bg-grey-darken-2 rounded">
              <p class="align-self-center">{{ championInfo.champion.id }} Summary</p>
              <p>Passive</p>
              <v-img
                :src="getPassiveIcon(championInfo.champion.passive.image.full)"
                max-width="48"
                max-height="48"
              >
                <v-tooltip
                  max-width="512"
                  :text="championInfo.champion.passive.description"
                  activator="parent"
                  location="bottom"
                />
              </v-img>
              <p>Spells</p>
              <div v-for="spell in championInfo.champion.spells" class="d-flex flex-row align-center">
                <v-img
                  :src="getAbilityIcon(spell.id)"
                  max-width="48"
                  max-height="48"
                >
                  <v-tooltip
                    max-width="256"
                    :text="spell.description"
                    activator="parent"
                    location="bottom"
                  />
                </v-img>
                <p class="ml-4">Cooldown: {{ spell.cooldownBurn }}</p>
                <p class="ml-4">Cost: {{ spell.costBurn == '0' ? 'No cost' : spell.costBurn }}</p>
              </div>
            </v-col>
          </v-row>
        </div>

        <MatchTable
          :summoner="summoner"
          :matchInfo="matchInfo"
        />
      </div>

      <div v-if="!hasLiveMatch && !isLoading">
        <h2>This user is not in a live match.</h2>
        <a @click="goToHome">Click here to go back to Home.</a>
      </div>
    </div>
  </v-container>
</template>
