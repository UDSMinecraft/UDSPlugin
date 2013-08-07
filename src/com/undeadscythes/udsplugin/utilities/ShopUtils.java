package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Utility class for handling shop interactions.
 * 
 * @author UndeadScythes
 */
public class ShopUtils {
    private static HashMap<SaveablePlayer, SortedList<ItemStack>> CARTS = new HashMap<SaveablePlayer, SortedList<ItemStack>>(0);
    
    public static void addShopper(final SaveablePlayer player, final Location shop) {
        player.setShopping(true);
        player.setShop(shop);
        final SortedList<ItemStack> cart = new SortedList<ItemStack>(new SortByID());
        fillCart(player, cart);
        CARTS.put(player, cart);
    }
    
    public static ArrayList<ItemStack> compareCarts(final SaveablePlayer player) {
        final ArrayList<ItemStack> diff = new ArrayList<ItemStack>(0);
        fillCart(player, diff);
        for(final ItemStack item : CARTS.get(player)) {
            diff.remove(item);
        }
        return diff;
    }
    
    public static void removeShopper(final SaveablePlayer player) {
        player.setShopping(false);
        CARTS.remove(player);
    }
    
    private static void fillCart(final SaveablePlayer player, final List<ItemStack> cart) {
        for(final ItemStack item : player.getInventory().getContents()) {
            if(item != null && !item.getType().equals(Material.AIR)) {
                cart.add(item.clone());
            }
        }
    }
    
    private ShopUtils() {}
}
