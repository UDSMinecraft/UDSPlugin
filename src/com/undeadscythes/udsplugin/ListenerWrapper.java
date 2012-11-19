package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * Provides checks for listeners.
 * @author UndeadScythes
 */
public class ListenerWrapper {
    public static ItemStack findItem(final String item) {
        ItemStack itemStack;
        if(item.contains(":")) {
            final String itemName = item.split(":")[0];
            Material material;
            if(itemName.matches("[0-9][0-9]*")) {
                material = Material.getMaterial(Integer.parseInt(itemName));
            } else {
                material = Material.matchMaterial(itemName);
            }
            if(material == null) {
                itemStack = null;
            } else {
                itemStack = new ItemStack(material, 1, (short) 0, Byte.parseByte(item.split(":")[1]));
            }
        } else {
            Material material;
            if(item.matches("[0-9][0-9]*")) {
                material = Material.getMaterial(Integer.parseInt(item));
            } else {
                material = Material.matchMaterial(item);
            }
            if(material == null) {
                final CustomItem myItem = CustomItem.getByName(item);
                if(myItem == null) {
                    itemStack = null;
                } else {
                    itemStack = myItem.toItemStack();
                }
            } else {
                itemStack = new ItemStack(material, 1);
            }
        }
        return itemStack;
    }

    public SaveablePlayer findShopOwner(Location location) {
        for(Region shop : UDSPlugin.getShops().values()) {
            if(location.toVector().isInAABB(shop.getV1(), shop.getV2())) {
                return shop.getOwner();
            }
        }
        return null;
    }

    public static boolean isShopSign(final String[] lines) {
        final String shopLine = lines[1];
        final String ownerLine = lines[0];
        final String priceLine = lines[3];
        return (shopLine.equalsIgnoreCase(Color.SIGN + "shop")
                || shopLine.equalsIgnoreCase("shop"))
            && ((UDSPlugin.getPlayers().get(ownerLine.replace(Color.SIGN.toString(), "")) != null
                || "".equals(ownerLine)
                || (Color.SIGN + "server").equalsIgnoreCase(ownerLine)))
            && findItem(lines[2]) != null
            && priceLine.contains(":")
            && priceLine.split(":")[0].replace("B ", "").matches("[0-9][0-9]*")
            && priceLine.split(":")[1].replace(" S", "").matches("[0-9][0-9]*");
    }

    public Entity getAbsoluteEntity(Entity entity) {
        if(entity instanceof Arrow) {
            return ((Arrow)entity).getShooter();
        }
        return entity;
    }

    public boolean hasFlag(Location location, RegionFlag flag) {
        boolean inRegion = false;
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2())) {
                inRegion = true;
                if(region.hasFlag(flag)) {
                    return true;
                }
            }
        }
        return inRegion ? false : Config.GLOBAL_FLAGS.get(flag);
    }

    public ArrayList<Region> regionsHere(Location location) {
        ArrayList<Region> regions = new ArrayList<Region>();
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2())) {
                regions.add(region);
            }
        }
        return regions;
    }

    public boolean regionsContain(ArrayList<Region> regions, Location location) {
        for(Region region : regions) {
            if(regionContains(region, location)) {
                return true;
            }
        }
        return false;
    }

    public boolean regionContains(Region region, Location location) {
        return region != null && location.toVector().isInAABB(region.getV1(), region.getV2());
    }

    public boolean isInQuarry(Location location) {
        for(Region quarry : UDSPlugin.getQuarries().values()) {
            if(location.toVector().isInAABB(quarry.getV1(), quarry.getV2())) {
                return true;
            }
        }
        return false;
    }
}
