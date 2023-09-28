export function getRank(rank, tier) {
  const formattedRank = (tier === 'I') ? rank : (rank + ' ' + tier)

  return formattedRank.charAt(0) + formattedRank.substring(1).toLowerCase();
}
