<script setup>
const props = defineProps({
  summoner: Object,
  championInfo: {
    type: Object,
    required: false
  }
})
</script>

<template>
  <div class="summoner-tags w-66 d-flex flex-row flex-wrap">
    <v-chip v-if="championInfo && championInfo.winrate > .55" color="orange">
      {{ 'Godlike ' + championInfo.champion.id }}
      <v-tooltip
        :text="summoner.name + ' has a ' + (Math.round(championInfo.winrate * 100)) + '% winrate on ' + championInfo.champion.id"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="summoner.stats.averageKills > 8" color="orange">
      Demon
      <v-tooltip
        :text="summoner.name + ' conquers the rift, averaging ' + Math.round(summoner.stats.averageKills) + ' kills per game.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="summoner.stats.averageAssists > 10" color="orange">
      Dimes
      <v-tooltip
        :text="summoner.name + ' is a good engager, averaging ' + Math.round(summoner.stats.averageAssists) + ' assists per game.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="(summoner.stats.averageGoldEarned / (summoner.stats.averageMatchDuration / 60)) > 400" color="orange">
      Gold Farmer
      <v-tooltip
        :text="summoner.name + ' is a great gold farmer, averaging ' + Math.round((summoner.stats.averageGoldEarned / (summoner.stats.averageMatchDuration / 60))) + ' gold per minute.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip  v-if="(summoner.stats.averageCreepScore / (summoner.stats.averageMatchDuration / 60)) > 6.5" color="green">
      OCD
      <v-tooltip
        :text="summoner.name + ' is a great CSer, averaging ' + Math.round((summoner.stats.averageCreepScore / (summoner.stats.averageMatchDuration / 60))) + ' CS per minute.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="summoner.stats.averageVisionScore > 30" color="green">
      Third Eye
      <v-tooltip
        :text="summoner.name + ' has great vision, averaging ' + Math.round(summoner.stats.averageVisionScore) + ' vision score per game.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="summoner.stats.firstBloodRate > .5" color="green">
      Bloodthirsty
      <v-tooltip
        :text="summoner.name + ' is hungry for blood, averaging a ' + Math.round(summoner.stats.firstBloodRate * 100) + '% chance for obtaining first blood.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
    <v-chip v-if="summoner.stats.firstTowerRate > .5" color="green">
      Arsonist
      <v-tooltip
        :text="summoner.name + ' is greedy for turrets, averaging a ' + Math.round(summoner.stats.firstBloodRate * 100) + '% chance for taking down the first turret.'"
        activator="parent"
        location="bottom"
      />
    </v-chip>
  </div>
</template>
