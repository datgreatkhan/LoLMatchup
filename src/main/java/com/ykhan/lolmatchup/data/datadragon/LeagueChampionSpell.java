package com.ykhan.lolmatchup.data.datadragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class LeagueChampionSpell implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private String id;
    private String name;
    private String description;
    private String tooltip;
    private HashMap<String, List<String>> leveltip;
    private Integer maxrank;
    private List<Double> cooldown;
    private String cooldownBurn;
    private List<Integer> cost;
    private String costBurn;
    private Object datavalues;
    private List<Object> effect;
    private List<Object> effectBurn;
    private List<Object> vars;
    private String costType;
    private Integer maxammo;
    private List<Integer> range;
    private String rangeBurn;
    private HashMap<String, Object> image;
    private String resource;

    public LeagueChampionSpell(String id, String name, String description, String tooltip, HashMap<String, List<String>> leveltip, Integer maxrank, List<Double> cooldown, String cooldownBurn, List<Integer> cost, String costBurn, Object datavalues, List<Object> effect, List<Object> effectBurn, List<Object> vars, String costType, Integer maxammo, List<Integer> range, String rangeBurn, HashMap<String, Object> image, String resource) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tooltip = tooltip;
        this.leveltip = leveltip;
        this.maxrank = maxrank;
        this.cooldown = cooldown;
        this.cooldownBurn = cooldownBurn;
        this.cost = cost;
        this.costBurn = costBurn;
        this.datavalues = datavalues;
        this.effect = effect;
        this.effectBurn = effectBurn;
        this.vars = vars;
        this.costType = costType;
        this.maxammo = maxammo;
        this.range = range;
        this.rangeBurn = rangeBurn;
        this.image = image;
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public HashMap<String, List<String>> getLeveltip() {
        return leveltip;
    }

    public Integer getMaxrank() {
        return maxrank;
    }

    public List<Double> getCooldown() {
        return cooldown;
    }

    public String getCooldownBurn() {
        return cooldownBurn;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public String getCostBurn() {
        return costBurn;
    }

    public Object getDatavalues() {
        return datavalues;
    }

    public List<Object> getEffect() {
        return effect;
    }

    public List<Object> getEffectBurn() {
        return effectBurn;
    }

    public List<Object> getVars() {
        return vars;
    }

    public String getCostType() {
        return costType;
    }

    public Integer getMaxammo() {
        return maxammo;
    }

    public List<Integer> getRange() {
        return range;
    }

    public String getRangeBurn() {
        return rangeBurn;
    }

    public HashMap<String, Object> getImage() {
        return image;
    }

    public String getResource() {
        return resource;
    }
}
