package com.ykhan.lolmatchup.data.datadragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class LeagueChampion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String key;
    private String name;
    private String title;
    private HashMap<String, Object> image;
    private List<Object> skins;
    private String lore;
    private String blurb;
    private List<String> allytips;
    private List<String> enemytips;
    // fighter, tank
    private List<String> tags;
    private String partype;
    // attack, defense, magic, difficulty
    private HashMap<String, Integer> info;
    // hp, hpperlevel, armor, hpregen, etc.
    private HashMap<String, Double> stats;
    private List<LeagueChampionSpell> spells;
    private LeagueChampionPassive passive;

    public LeagueChampion(String id, String key, String name, String title, HashMap<String, Object> image, List<Object> skins, String lore, String blurb, List<String> allytips, List<String> enemytips, List<String> tags, String partype, HashMap<String, Integer> info, HashMap<String, Double> stats, List<LeagueChampionSpell> spells, LeagueChampionPassive passive) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
        this.image = image;
        this.skins = skins;
        this.lore = lore;
        this.blurb = blurb;
        this.allytips = allytips;
        this.enemytips = enemytips;
        this.tags = tags;
        this.partype = partype;
        this.info = info;
        this.stats = stats;
        this.spells = spells;
        this.passive = passive;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, Object> getImage() {
        return image;
    }

    public List<Object> getSkins() {
        return skins;
    }

    public String getLore() {
        return lore;
    }

    public String getBlurb() {
        return blurb;
    }

    public List<String> getAllytips() {
        return allytips;
    }

    public List<String> getEnemytips() {
        return enemytips;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getPartype() {
        return partype;
    }

    public HashMap<String, Integer> getInfo() {
        return info;
    }

    public HashMap<String, Double> getStats() {
        return stats;
    }

    public List<LeagueChampionSpell> getSpells() {
        return spells;
    }

    public LeagueChampionPassive getPassive() {
        return passive;
    }

    @Override
    public String toString() {
        return this.id + " [" + this.key + "]\n" +
                this.title + "\n" +
                this.tags.toString() + "\n" +
                this.lore;
    }
}
