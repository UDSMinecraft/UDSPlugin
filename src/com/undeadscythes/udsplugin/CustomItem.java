package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 *
 * @author UndeadScythes
 */
public enum CustomItem {
    WHITE_WOOL(Material.WOOL, 0),
    ORANGE_WOOL(Material.WOOL, 1),
    MAGENTA_WOOL(Material.WOOL, 2),
    DENIM_WOOL(Material.WOOL, 3),
    YELLOW_WOOL(Material.WOOL, 4),
    LIME_WOOL(Material.WOOL, 5),
    PINK_WOOL(Material.WOOL, 6),
    SLATE_WOOL(Material.WOOL, 7),
    GRAY_WOOL(Material.WOOL, 8),
    CYAN_WOOL(Material.WOOL, 9),
    PURPLE_WOOL(Material.WOOL, 10),
    NAVY_WOOL(Material.WOOL, 11),
    BROWN_WOOL(Material.WOOL, 12),
    OLIVE_WOOL(Material.WOOL, 13),
    RED_WOOL(Material.WOOL, 14),
    BLACK_WOOL(Material.WOOL, 15),
    CHAIN_HAT(Material.CHAINMAIL_HELMET, 0),
    CHAIN_CHEST(Material.CHAINMAIL_CHESTPLATE, 0),
    CHAIN_LEGS(Material.CHAINMAIL_LEGGINGS, 0),
    CHAIN_BOOTS(Material.CHAINMAIL_BOOTS, 0),
    INK_SACK(Material.INK_SACK, 0),
    RED_DYE(Material.INK_SACK, 1),
    CACTUS_DYE(Material.INK_SACK, 2),
    COCOA_BEANS(Material.INK_SACK, 3),
    LAPIS_LAZULI(Material.INK_SACK, 4),
    PURPLE_DYE(Material.INK_SACK, 5),
    CYAN_DYE(Material.INK_SACK, 6),
    GRAY_DYE(Material.INK_SACK, 7),
    SLATE_DYE(Material.INK_SACK, 8),
    PINK_DYE(Material.INK_SACK, 9),
    LIME_DYE(Material.INK_SACK, 10),
    YELLOW_DYE(Material.INK_SACK, 11),
    DENIM_DYE(Material.INK_SACK, 12),
    MAGENTA_DYE(Material.INK_SACK, 13),
    ORANGE_DYE(Material.INK_SACK, 14),
    BONEMEAL(Material.INK_SACK, 15),
    MILK(Material.MILK_BUCKET, 0),
    LANTERN(Material.REDSTONE_LAMP_OFF, 0),
    MOSSY_COBBLE(Material.MOSSY_COBBLESTONE, 0),
    MYCELIUM(Material.MYCEL, 0),
    DOUBLE_SLAB(Material.DOUBLE_STEP, 0),
    CREEPER_BLOCK(Material.SANDSTONE, 1),
    CIRCLE_BLOCK(Material.SMOOTH_BRICK, 3),
    SANDSTONE_BLOCK(Material.SANDSTONE, 2);

    private Material material;
    private byte data;

    private CustomItem(Material material, int data) {
        this.material = material;
        this.data = (byte)data;
    }

    public static CustomItem getByName(String name) {
        for(CustomItem item : values()) {
            if(item.name().equals(name.toUpperCase())) {
                return item;
            }
        }
        return null;
    }

    public static CustomItem getByItem(Material material) {
        for(CustomItem item : values()) {
            if(item.material.equals(material)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public ItemStack toItemStack() {
        return new ItemStack(material, 1, (short)0, data);
    }

    public ItemStack toItemStack(int amount) {
        return new ItemStack(material, amount, (short)0, data);
    }
}
