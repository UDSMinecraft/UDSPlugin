package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import java.util.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class Kit {
    private final String name;
    private final int price;
    private final List<ItemStack> items;
    private final MemberRank rank;

    public Kit(final String name, final int price, final List<ItemStack> items, final MemberRank rank) {
        this.name = name;
        this.price = price;
        this.items = items;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MemberRank getRank() {
        return rank;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
