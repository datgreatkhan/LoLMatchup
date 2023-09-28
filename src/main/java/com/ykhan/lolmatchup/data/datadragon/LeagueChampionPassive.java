package com.ykhan.lolmatchup.data.datadragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class LeagueChampionPassive implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private String name;
    private String description;
    private HashMap<String, Object> image;

    public LeagueChampionPassive(String name, String description, HashMap<String, Object> image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, Object> getImage() {
        return image;
    }
}
