package com.undeadscythes.udsplugin1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * An area of blocks with in a world.
 * @author UndeadScythes
 */
public class Region implements Saveable {
    /**
     * File name of region file.
     */
    public static String PATH = "region.csv";

    private String name;
    private Vector v1;
    private Vector v2;
    private Location warp;
    private String owner;
    private ArrayList<String> members;

    /**
     * Initialise a brand new region.
     * @param name Name of the region.
     * @param v1 Minimum block position.
     * @param v2 Maximum block position.
     * @param warp Warp location of the region.
     * @param owner Owner of the region.
     */
    public Region(String name, Vector v1, Vector v2, Location warp, String owner) {
        this.name = name;
        this.v1 = v1;
        this.v2 = v2;
        this.warp = warp;
        this.owner = owner;
        members = new ArrayList<String>();
    }

    /**
     * Initialise a region from a string record.
     * @param record A line from a save file.
     * @throws IOException Thrown when vectors can't be loaded.
     */
    public Region(String record) throws IOException {
        String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        v1 = getBlockPos(recordSplit[1]);
        v2 = getBlockPos(recordSplit[2]);
        warp = new ExtendedLocation(recordSplit[3]);
        owner = recordSplit[4];
        members = new ArrayList<String>(Arrays.asList(recordSplit[5].split(",")));
    }

    /**
     * Helper function to build a new block position from a string.
     * @param string String containing coded block position.
     * @return The corresponding new block position.
     */
    private Vector getBlockPos(String string) {
        String[] split = string.replace("(", "").replace(")", "").split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        int z = Integer.parseInt(split[2]);
        return new Vector(x, y, z);
    }

    /**
     * @inheritDoc
     */
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(v1.toString());
        record.add(v2.toString());
        record.add(warp.toString());
        record.add(owner);
        record.add(StringUtils.join(members.toArray(), ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    /**
     * Get the name of the region.
     * @return Name of the region.
     */
    public String getName() {
        return name;
    }

    /**
     * Change the name of the region.
     * @param name New name of the region.
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Check if a location is contained within the region.
     * @param location Location to check.
     * @return <code>true</code> if the location is contained within the region, <code>false</code> otherwise.
     */
    public boolean contains(Location location) {
        return contains(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    /**
     * Check if coordinates are contained within the region.
     * @param world World of coordinates.
     * @param x X-Coordinate.
     * @param y Y-Coordinate.
     * @param z Z-Coordinate.
     * @return <code>true</code> if the location is contained within the region, <code>false</code> otherwise.
     */
    public boolean contains(World world, double x, double y, double z) {
        if(warp.getWorld().equals(world) && x > v1.getX() && x < v2.getX() + 1 && z > v1.getZ() && z < v2.getZ() + 1 && y > v1.getY() && y < v2.getY() + 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the warp location of this region.
     * @return Location of region warp point.
     */
    public Location getWarp() {
        return warp;
    }

    /**
     * Check if a player is a member of a region.
     * @param name Player name.
     * @return <code>true</code> if player is a member, <code>false</code> otherwise.
     */
    public boolean hasMember(String name) {
        return members.contains(name);
    }

    /**
     * Add a player as a member of the region.
     * @param name Player name.
     */
    public void addMember(String name) {
        members.add(name);
    }

    /**
     * Remove a player as a member of a region.
     * @param name Player name.
     */
    public void delMember(String name) {
        members.remove(name);
    }
}
