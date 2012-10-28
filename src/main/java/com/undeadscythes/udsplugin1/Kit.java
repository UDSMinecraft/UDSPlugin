package com.undeadscythes.udsplugin1;

import org.bukkit.inventory.ItemStack;


/**
 * A purchasable kit of items.
 * @author UndeadScythes
 */
public class Kit {
    String name;
    int price;
    ItemStack[] items;
    String rank;

    /**
     * Initialise a brand new kit.
     * @param name Name of kit.
     * @param price Price of kit.
     * @param items Items contained in kit.
     * @param rank Rank required to purchase kit.
     */
    public Kit(String name, int price, ItemStack[] items, String rank) {
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
}
