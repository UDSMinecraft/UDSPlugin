package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.inventory.*;


/**
 * A purchasable kit of items.
 * 
 * @author UndeadScythes
 */
public class Kit {
    private final String name;
    private final int price;
    private final List<ItemStack> items;
    private final PlayerRank rank;

    public Kit(final String name, final int price, final List<ItemStack> items, final PlayerRank rank) {
        this.name = name;
        this.price = price;
        this.items = items;
        this.rank = rank;
    }

    public final String getName() {
        return name;
    }

    public final int getPrice() {
        return price;
    }

    public final PlayerRank getRank() {
        return rank;
    }

    public final List<ItemStack> getItems() {
        return items;
    }
}
