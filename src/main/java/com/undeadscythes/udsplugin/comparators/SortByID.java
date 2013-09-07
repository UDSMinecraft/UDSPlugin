package com.undeadscythes.udsplugin.comparators;

import java.util.*;
import org.bukkit.inventory.*;

/**
 * Compares items by ID number and data value.
 * 
 * @author Dave
 */
public class SortByID implements Comparator<ItemStack> {
    @Override
    public int compare(ItemStack item1, ItemStack item2) {
        if(item1.getTypeId() == item2.getTypeId()) {
            return item1.getData().getData() - item2.getData().getData();
        }
        return item1.getTypeId() - item2.getTypeId();
    }
}
