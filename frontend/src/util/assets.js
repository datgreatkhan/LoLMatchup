export function getSummonerIcon(id) {
  return 'https://raw.communitydragon.org/13.17/game/assets/ux/summonericons/profileicon' + id + '.png';
}

export function getRankEmblem(rank) {
  return 'https://raw.communitydragon.org/13.17/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-' + rank + '.png';
}

export function getChampionIcon(champion) {
  return 'https://ddragon.leagueoflegends.com/cdn/13.18.1/img/champion/' + champion + '.png';
}

export function getChampionBanner(champion) {
  return 'https://ddragon.leagueoflegends.com/cdn/img/champion/loading/' + champion + '_0.jpg';
}

export function getPassiveIcon(imageName) {
  return 'http://ddragon.leagueoflegends.com/cdn/13.18.1/img/passive/' + imageName;
}

export function getAbilityIcon(name) {
  return 'http://ddragon.leagueoflegends.com/cdn/13.18.1/img/spell/' + name + '.png';
}

export function getItemIcon(item) {
  return 'http://ddragon.leagueoflegends.com/cdn/13.18.1/img/item/' + item + '.png';
}
