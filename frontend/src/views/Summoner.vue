<template>
  <v-container class="fill-height w-100 bg-grey-darken-3" fluid>
    <Profile :region="props.region" :name="props.name"/>
  </v-container>
</template>

<script setup>
import Profile from '@/components/Profile.vue'

const props = defineProps({
  region: String,
  name: String
})
</script>
