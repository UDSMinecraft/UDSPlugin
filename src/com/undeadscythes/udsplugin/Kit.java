package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import java.util.*;
import org.bukkit.inventory.*;


/**
 * A purchasable kit of items.
 * @author UndeadScythes
 */
public class Kit {
    String name;
    int price;
    ArrayList<ItemStack> items;
    PlayerRank rank;

    /**
     * Initialise a brand new kit.
     * @param name Name of kit.
     * @param price Price of kit.
     * @param items Items contained in kit.
     * @param rank Rank required to purchase kit.
     */
    public Kit(String name, int price, ArrayList<ItemStack> items, PlayerRank rank) {
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

    public PlayerRank getRank() {
        return rank;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }
}
