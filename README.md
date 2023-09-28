# LoLMatchup
A small-scale live match and summoner lookup web app for League of Legends. Made with Spring Boot, Riot API (using R4J library) and Vuetify.

The main purpose of LoLMatchup is to search your matchup at the start of a live match, where you are then presented a quick summary of your matchup's stats and the champion they play.
This is implied to be a tool for beginner League of Legends players, who want to learn how to better play against their own matchup.

# Preview

![image](https://github.com/datgreatkhan/LoLMatchup/assets/129341569/9a0df683-ba91-4da7-a38c-06533e90f850)

![image](https://github.com/datgreatkhan/LoLMatchup/assets/129341569/ea95935b-3d15-4ef9-962d-0200b333a28a)

# Features

- Live Match Lookup - shows information for your matchup (winrate, champion stats, preferred build) and their champion (champion passive and spells w/ their description, cooldowns and cost) and the live match information (other players, bans, etc.)
- Summoner Lookup - shows information for a specific summoner (winrate, most played lane, etc.)

# Libraries and Frameworks Used

- Spring Boot w/ Spring JPA
- Vue3 with Vuetify
- PostgreSQL
- [stelar/R4J](https://github.com/stelar7/R4J) (third-party library to make calls to the Riot API)

# Notes

- Sample size is very small (therefore inflated numbers) - otherwise there will be rate limits. Riot API development key only supports a very small amount of requests per minute. To avoid rate limits, you must have your application in **production** and apply for a key in their portal. 
- Stat processing is done in a very simple way. I obtain their match history via Riot API, then process the stats for that player with the totals.
- Large scale stat services such as "OPGG", "Porofessor", etc. collect and process stats themselves in a much more large and efficient way (caching, processing all players in match histories, etc.). If you are intending to make your own large-scale application, you will have to consider those things.

Anyone may use my source code as they wish, all I ask in return is to be credited if outside of personal use.
