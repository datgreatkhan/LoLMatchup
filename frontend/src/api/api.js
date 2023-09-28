import {apiClient} from "@/plugins";

export function lookup(region, summonerName) {
  return apiClient.get('live/' + region + '/' + summonerName);
}

export function profile(region, summonerName) {
  return apiClient.get('summoner/' + region + '/' + summonerName);
}
