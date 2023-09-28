<script setup>
import {useRouter} from "vue-router";

const router = useRouter()

function goToHome() {
  router.replace("/");
}
</script>

<template>
  <v-app-bar color="grey-darken-4">
    <v-app-bar-title @click="goToHome" class="v-list-item--link">
      <v-icon icon="mdi-sword-cross" />

      LoLMatchup
    </v-app-bar-title>
  </v-app-bar>
</template>
