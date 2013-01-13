package com.undeadscythes.udsplugin;

import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.block.*;

/**
 * A warp point used for player teleportation.
 * @author UndeadScythes
 */
public class Warp implements Saveable {
    /**
     * File name of warp file.
     */
    public static final String PATH = "warps.csv";

    private final String name;
    private final Location location;
    private final PlayerRank rank;
    private final int price;

    /**
     * Initialise a brand new warp point.
     * @param name Warp name.
     * @param location Location of the warp.
     * @param rank Rank required to use the warp.
     * @param price Money required to use the warp.
     */
    public Warp(final String name, final Location location, final PlayerRank rank, final int price) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.price = price;
    }

    /**
     * Initialise a warp from a string record.
     * @param record A line from a file.
     */
    public Warp(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        location = SaveableLocation.parseLocation(recordSplit[1]);
        rank = PlayerRank.getByName(recordSplit[2]);
        price = Integer.parseInt(recordSplit[3]);
    }

    @Override
    public final String getRecord() {
        final ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(SaveableLocation.getString(location));
        record.add(rank.toString());
        record.add(Integer.toString(price));
        return StringUtils.join(record, "\t");
    }

    /**
     * Get the name of the warp.
     * @return Warp name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price required to use the warp.
     * @return The price to use the warp.
     */
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        Bukkit.getLogger().info("Implicit Warp.toString(). (" + Thread.currentThread().getStackTrace() + ")"); // Implicit .toString()
        return name;
    }

    /**
     * Get the rank required to use this warp.
     * @return Rank needed to use this warp.
     */
    public PlayerRank getRank() {
        return rank;
    }

    /**
     * Find a safe place to warp to from this warp.
     * @return A safe location.
     */
    public Location getLocation() {
        return findSafePlace(location);
    }

    // Should these methods be moved to SaveableLocation? Probably

    /**
        * Find a safe place to teleport to near this location.
        * @param location Location to teleport to.
        * @return A safe place.
        */
    public static Location findSafePlace(final Location location) {
        final Location testUp = location;
        final Location testDown = location.clone().subtract(0, 1, 0);
        while(testUp.getBlockY() < UDSPlugin.BUILD_LIMIT && testDown.getBlockY() > 0) {
            if(!testUp.getBlock().getType().isSolid() && !testUp.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                return testUp;
            } else if(!testDown.getBlock().getType().isSolid() && !testDown.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                return testDown;
            } else {
                testUp.add(0, 1, 0);
                testDown.add(0, 1, 0);
            }
        }
        return location.getWorld().getSpawnLocation();
    }
}
