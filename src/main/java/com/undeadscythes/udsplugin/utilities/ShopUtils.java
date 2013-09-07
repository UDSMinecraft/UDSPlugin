package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class ShopUtils {
    private static HashMap<Member, SortedList<ItemStack>> CARTS = new HashMap<Member, SortedList<ItemStack>>(0);

    public static void addShopper(final Member player, final Location shop) {
        player.setShopping(true);
        player.setShop(shop);
        final SortedList<ItemStack> cart = new SortedList<ItemStack>(new SortByID());
        fillCart(player, cart);
        CARTS.put(player, cart);
    }

    public static ArrayList<ItemStack> compareCarts(final Member player) {
        final ArrayList<ItemStack> diff = new ArrayList<ItemStack>(0);
        fillCart(player, diff);
        for(final ItemStack item : CARTS.get(player)) {
            diff.remove(item);
        }
        return diff;
    }

    public static void removeShopper(final Member player) {
        player.setShopping(false);
        CARTS.remove(player);
    }

    private static void fillCart(final Member player, final List<ItemStack> cart) {
        for(final ItemStack item : player.getInventory().getContents()) {
            if(item != null && !item.getType().equals(Material.AIR)) {
                cart.add(item.clone());
            }
        }
    }

    private ShopUtils() {}
}
