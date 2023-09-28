<script setup>
import {computed, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()

const regions = ref([
  {
    id: 'BR1',
    display: 'Brazil'
  },
  {
    id: 'EUN1',
    display: 'Europe (North)'
  },
  {
    id: 'EUW1',
    display: 'Europe (West)'
  },
  {
    id: 'JP1',
    display: 'Japan'
  },
  {
    id: 'KR',
    display: 'Korea'
  },
  {
    id: 'LA1',
    display: 'Latin America (North)'
  },
  {
    id: 'LA2',
    display: 'Latin America (South)'
  },
  {
    id: 'NA1',
    display: 'North America'
  },
  {
    id: 'OC1',
    display: 'Oceania'
  },
  {
    id: 'TR1',
    display: 'Turkey'
  },
  {
    id: 'RU',
    display: 'Russia'
  },
  {
    id: 'PH2',
    display: 'Philippines'
  },
  {
    id: 'SG2',
    display: 'Singapore/Malaysia/Indonesia'
  },
  {
    id: 'TW2',
    display: 'Taiwan/Hong Kong/Macao'
  },
  {
    id: 'TH2',
    display: 'Thailand'
  },
  {
    id: 'VN2',
    display: 'Vietnam'
  },
])

const name = ref("");
const region = ref(regions.value.find(a => a.id === "NA1"));

const isSearchPopulated = computed(() => name.value.length > 0 && region.value)

function onRegionChanged() {
  console.log(region.value);
}

function onLookup() {
  router.replace('/live/' + region.value.id + '/' + name.value);
}
</script>

<template>
  <v-container class="fill-height w-100">
    <div class="w-100 fill-height d-flex justify-center align-center">
      <!-- <v-progress-circular v-if="isLoading" indeterminate /> -->

      <div class="w-66 h-25 pa-8 d-flex flex-column justify-center align-center">
        <div class="d-flex flex-column justify-center align-center">
          <p>Welcome to LoLMatchup, a simple matchup helper for League of Legends.</p>
          <p>To start, enter your name below while in a live match.</p>
        </div>
        <div class="w-66 mt-12 d-flex flex-row justify-space-between align-center">
          <v-text-field class="w-66 mr-12" v-model="name" label="Summoner Name" density="compact">
            <template v-slot:append-inner>
              <a @click="onLookup">
                <v-icon v-if="isSearchPopulated" icon="mdi-arrow-right" />
              </a>
            </template>
          </v-text-field>
          <v-select class="w-25" @update:modelValue="onRegionChanged" v-model="region" label="Region" :items="regions" item-value="id" item-title="display" return-object density="compact"></v-select>
        </div>
      </div>
    </div>
  </v-container>
</template>
