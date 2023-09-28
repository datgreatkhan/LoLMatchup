package com.ykhan.lolmatchup.data.datadragon;

import java.io.Serial;
import java.io.Serializable;

public class LeagueItemCost implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    private Integer base;
    private boolean purchaseable;
    private Integer total;
    private Integer sell;

    public LeagueItemCost(Integer base, boolean purchaseable, Integer total, Integer sell) {
        this.base = base;
        this.purchaseable = purchaseable;
        this.total = total;
        this.sell = sell;
    }

    public Integer getBase() {
        return base;
    }

    public boolean isPurchaseable() {
        return purchaseable;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getSell() {
        return sell;
    }

    @Override
    public String toString() {
        return "cost: " + this.base +
                "; buyable: " + this.purchaseable
                + "; total: " + this.total
                + "; sell value: " + this.sell;
    }
}
