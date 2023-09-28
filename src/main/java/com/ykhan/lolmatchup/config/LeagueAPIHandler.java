package com.ykhan.lolmatchup.config;

import com.ykhan.lolmatchup.data.datadragon.LeagueChampion;
import com.ykhan.lolmatchup.data.datadragon.LeagueItem;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.impl.R4J;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

@Component
public class LeagueAPIHandler {

    private static final String RIOT_API_KEY = "Your Riot API key here";

    private final Logger logger = LoggerFactory.getLogger(LeagueAPIHandler.class);

    private R4J.LOLAPI leagueApi;

    private HashMap<String, LeagueChampion> leagueChampions;

    private HashMap<String, LeagueItem> leagueItems;

    @EventListener
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        // summoner spells
            // http://ddragon.leagueoflegends.com/cdn/13.18.1/data/en_US/summoner.json
            // http://ddragon.leagueoflegends.com/cdn/13.18.1/img/spell/SummonerFlash.png

        // items
            // https://ddragon.leagueoflegends.com/cdn/13.18.1/img/item/1001.png

        try {
            FileInputStream champsFileInputStream = new FileInputStream("leagueChampions");
            ObjectInputStream champsObjectInputStream = new ObjectInputStream(champsFileInputStream);

            leagueChampions = (HashMap<String, LeagueChampion>) champsObjectInputStream.readObject();

            champsObjectInputStream.close();

            FileInputStream itemsFileInputStream = new FileInputStream("leagueItems");
            ObjectInputStream itemsObjectInputStream = new ObjectInputStream(itemsFileInputStream);

            leagueItems = (HashMap<String, LeagueItem>) itemsObjectInputStream.readObject();

            itemsObjectInputStream.close();

            logger.info("Loaded " + leagueChampions.size() + " champions.");
            logger.info("Loaded " + leagueItems.size() + " items.");

            String randomChampion = (String) leagueChampions.keySet().toArray()[new Random().nextInt(leagueChampions.size())];
            String randomItem = (String) leagueItems.keySet().toArray()[new Random().nextInt(leagueItems.size())];

            logger.info("Random Champion:");
            logger.info(leagueChampions.get(randomChampion).toString());
            logger.info("Random Item:");
            logger.info(leagueItems.get(randomItem).toString());
        }catch(IOException|ClassNotFoundException e) {
            logger.error("Failed to load or deserialize items & champions.", e);
        }

        R4J apiHandler = new R4J(new APICredentials(RIOT_API_KEY));
        this.leagueApi = apiHandler.getLoLAPI();
    }

    public R4J.LOLAPI get() {
        return this.leagueApi;
    }

    public HashMap<String, LeagueChampion> getLeagueChampions() {
        return this.leagueChampions;
    }

    public HashMap<String, LeagueItem> getLeagueItems() { return this.leagueItems; }

    /*
     The below code is what I used to manually take data from "DataDragon",
     parse it, and then serialize it so we can load it on startup each time.

     Definitely not recommend to do, just did it to save time.
    */

    /*
    String championsListEndpoint = "https://ddragon.leagueoflegends.com/cdn/13.18.1/data/en_US/champion.json";
    String championEndpoint = "https://ddragon.leagueoflegends.com/cdn/13.18.1/data/en_US/champion/<CHAMPION_NAME>.json";

    Gson gson = new Gson();
        try {
        URL championsList = new URL(championsListEndpoint);

        JsonReader championListReader = gson.newJsonReader(new InputStreamReader(championsList.openStream()));

        championListReader.beginObject();
        for(int i = 0; i < 7; i++) {
            championListReader.skipValue();
        }

        JsonElement champArray = JsonParser.parseReader(championListReader);
        JsonObject champArrayObj = champArray.getAsJsonObject();

        Set<String> championNames = champArrayObj.keySet();
        HashMap<String, LeagueChampion> leagueChampions = new HashMap<String, LeagueChampion>();

        for(String champName : championNames) {
            URL championData = new URL(championEndpoint.replace("<CHAMPION_NAME>", champName));

            JsonReader championDataReader = gson.newJsonReader(new InputStreamReader(championData.openStream()));

            championDataReader.beginObject();

            for(int i = 0; i < 7; i++) {
                championDataReader.skipValue();
            }

            championDataReader.beginObject();
            championDataReader.skipValue();

            LeagueChampion leagueChampion = gson.fromJson(championDataReader, LeagueChampion.class);

            System.out.println(leagueChampion.getId() + ", " + leagueChampion.getKey() + ": " + leagueChampion.getBlurb());

            leagueChampions.put(leagueChampion.getKey(), leagueChampion);

            championDataReader.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("leagueChampions");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(leagueChampions);
        objectOutputStream.close();

        championListReader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

     */
}
