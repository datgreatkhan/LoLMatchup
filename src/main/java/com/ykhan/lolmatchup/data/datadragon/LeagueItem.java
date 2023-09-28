package com.ykhan.lolmatchup.data.datadragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class LeagueItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private String name;
    private String description;
    private String colloq;
    private String plaintext;
    private List<Integer> from;
    private List<Integer> into;
    private HashMap<String, Object> image;
    private LeagueItemCost gold;
    private List<String> tags;
    private HashMap<String, Boolean> maps;
    private HashMap<String, Double> stats;
    private Integer depth;

    public LeagueItem(String name, String description, String colloq, String plaintext, List<Integer> from, List<Integer> into, HashMap<String, Object> image, LeagueItemCost gold, List<String> tags, HashMap<String, Boolean> maps, HashMap<String, Double> stats, Integer depth) {
        this.name = name;
        this.description = description;
        this.colloq = colloq;
        this.plaintext = plaintext;
        this.from = from;
        this.into = into;
        this.image = image;
        this.gold = gold;
        this.tags = tags;
        this.maps = maps;
        this.stats = stats;
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColloq() {
        return colloq;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public List<Integer> getFrom() {
        return from;
    }

    public List<Integer> getInto() {
        return into;
    }

    public HashMap<String, Object> getImage() {
        return image;
    }

    public LeagueItemCost getGold() {
        return gold;
    }

    public List<String> getTags() {
        return tags;
    }

    public HashMap<String, Boolean> getMaps() {
        return maps;
    }

    public HashMap<String, Double> getStats() {
        return stats;
    }

    public Integer getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return this.name + "\n" +
                this.description + "\n" +
                this.gold.toString() + "\n" +
                this.tags.toString() + "\n" +
                this.stats.toString();
    }
}
