package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.inventory.*;


/**
 * A purchasable kit of items.
 * @author UndeadScythes
 */
public class Kit {
    private final transient String name;
    private final transient int price;
    private final transient List<ItemStack> items;
    private final transient PlayerRank rank;

    /**
     * Initialise a brand new kit.
     * @param name Name of kit.
     * @param price Price of kit.
     * @param items Items contained in kit.
     * @param rank Rank required to purchase kit.
     */
    public Kit(final String name, final int price, final List<ItemStack> items, final PlayerRank rank) {
        this.name = name;
        this.price = price;
        this.items = items;
        this.rank = rank;
    }

    /**
     * Get the name of this kit.
     * @return The name of the kit.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of this kit.
     * @return The price of this kit.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Get the rank required to purchase this kit.
     * @return Player rank of kit.
     */
    public PlayerRank getRank() {
        return rank;
    }

    /**
     * Get the items contained in this kit.
     * @return Kit items.
     */
    public List<ItemStack> getItems() {
        return items;
    }
}
