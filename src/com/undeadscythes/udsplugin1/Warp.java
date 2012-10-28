package com.undeadscythes.udsplugin1;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * A warp point used for player teleportation.
 * @author UndeadScythes
 */
public class Warp implements Saveable {
    /**
     * File name of warp file.
     */
    public static String PATH = "warp.csv";

    private String name;
    private Location location;
    private Rank rank;
    private int price;

    /**
     * Initialise a brand new warp point.
     * @param name Warp name.
     * @param location Location of the warp.
     * @param rank Rank required to use the warp.
     * @param price Money required to use the warp.
     */
    public Warp(String name, ExtendedLocation location, Rank rank, int price) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.price = price;
    }

    /**
     * Initialise a warp from a string record.
     * @param record A line from a file.
     */
    public Warp(String record) {
        String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        location = new ExtendedLocation(recordSplit[1]);
        rank = Rank.valueOf(recordSplit[2]);
        price = Integer.parseInt(recordSplit[3]);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(location.toString());
        record.add(rank.toString());
        record.add(price + "");
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

    /**
     * Get the location of the warp.
     * @return Warp location.
     */
    public Location getLocation() {
        return location;
    }

    public static Location findSafePlace(World world, double x, double z) {
        Location safePlace = world.getHighestBlockAt((int)x, (int)z).getLocation();
        safePlace.add(.5, 0, .5);
        return safePlace;
    }
}
