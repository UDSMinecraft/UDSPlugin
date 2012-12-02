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
    /**
     *
     * @param item
     * @return
     */
    public static ItemStack findItem(final String item) {
        ItemStack itemStack;
        if(item.contains(":")) {
            final String itemName = item.split(":")[0];
            Material material;
            if(itemName.matches(UDSPlugin.INT_REGEX)) {
                material = Material.getMaterial(Integer.parseInt(itemName));
            } else {
                material = Material.matchMaterial(itemName);
            }
            if(material == null) {
                return null;
            } else {
                itemStack = new ItemStack(material, 1, (short) 0, Byte.parseByte(item.split(":")[1]));
            }
        } else {
            Material material;
            if(item.matches(UDSPlugin.INT_REGEX)) {
                material = Material.getMaterial(Integer.parseInt(item));
            } else {
                material = Material.matchMaterial(item);
            }
            if(material == null) {
                final ShortItem myItem = ShortItem.getByName(item);
                if(myItem == null) {
                    return null;
                } else {
                    itemStack = myItem.toItemStack();
                }
            } else {
                itemStack = new ItemStack(material, 1);
            }
        }
        return itemStack;
    }

    /**
     *
     * @param location
     * @return
     */
    public SaveablePlayer findShopOwner(final Location location) {
        for(Region shop : UDSPlugin.getShops().values()) {
            if(location.toVector().isInAABB(shop.getV1(), shop.getV2())) {
                return shop.getOwner();
            }
        }
        return null;
    }

    /**
     *
     * @param lines
     * @return
     */
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

    /**
     *
     * @param entity
     * @return
     */
    public Entity getAbsoluteEntity(final Entity entity) {
        if(entity instanceof Arrow) {
            return ((Arrow)entity).getShooter();
        }
        return entity;
    }

    /**
     *
     * @param location
     * @param flag
     * @return
     */
    public boolean hasFlag(final Location location, final RegionFlag flag) {
        boolean inRegion = false;
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2())) {
                inRegion = true;
                if(region.hasFlag(flag)) {
                    return true;
                }
            }
        }
        return inRegion ? false : Config.globalFlags.get(flag);
    }

    /**
     *
     * @param location
     * @return
     */
    public List<Region> regionsHere(final Location location) {
        final List<Region> regions = new ArrayList<Region>();
        for(Region region : UDSPlugin.getRegions().values()) {
            if(location.getWorld().equals(region.getWorld()) && location.toVector().isInAABB(region.getV1(), region.getV2())) {
                regions.add(region);
            }
        }
        return regions;
    }

    /**
     *
     * @param regions
     * @param location
     * @return
     */
    public boolean regionsContain(final List<Region> regions, final Location location) {
        for(Region region : regions) {
            if(regionContains(region, location)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param region
     * @param location
     * @return
     */
    public boolean regionContains(final Region region, final Location location) {
        return region != null && location.toVector().isInAABB(region.getV1(), region.getV2());
    }

    /**
     *
     * @param location
     * @return
     */
    public boolean isInQuarry(final Location location) {
        for(Region quarry : UDSPlugin.getQuarries().values()) {
            if(location.toVector().isInAABB(quarry.getV1(), quarry.getV2())) {
                return true;
            }
        }
        return false;
    }
}
