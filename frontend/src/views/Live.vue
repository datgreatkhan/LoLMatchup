<template>
  <v-container class="fill-height w-100 bg-grey-darken-3" fluid>
    <LiveMatch :region="props.region" :name="props.name"/>
  </v-container>
</template>

<script setup>
import LiveMatch from '@/components/LiveMatch.vue'

const props = defineProps({
  region: String,
  name: String
})
</script>
