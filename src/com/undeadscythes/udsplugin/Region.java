package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

/**
 * An area of blocks with in a world.
 * @author UndeadScythes
 */
public class Region implements Saveable {
    public enum Flag {
        PROTECTED(true),
        BLOCK_MOBS(true),
        BLOCK_ANIMALS(false),
        LOCK_DOORS(true),
        BLOCK_VINES(false),
        BLOCK_SHROOMS(false),
        BLOCK_FIRE(true),
        BLOCK_SNOW(true),
        BLOCK_PVP(true);

        private boolean defaultValue;

        Flag(boolean value) {
            this.defaultValue = value;
        }

        public boolean getDefault() {
            return defaultValue;
        }
    }

    public enum Type {
        ARBITRARY,
        SHOP,
        BASE,
        QUARRY,
        HOME,
        ARENA,
        CITY;
    }

    /**
     * File name of region file.
     */
    public static String PATH = "region.data";

    private String name;
    private Vector v1;
    private Vector v2;
    private Location warp;
    private String owner;
    private HashSet<String> members = new HashSet<String>();
    private String data;
    private HashSet<Flag> flags;
    private Type type;

    /**
     * Initialise a brand new region.
     * @param name Name of the region.
     * @param v1 Minimum block position.
     * @param v2 Maximum block position.
     * @param warp Warp location of the region.
     * @param owner Owner of the region.
     */
    public Region(String name, Vector v1, Vector v2, Location warp, String owner, String data, Type type) {
        this.name = name;
        this.v1 = v1;
        this.v2 = v2;
        this.warp = warp;
        this.owner = owner;
        this.data = data;
        flags = new HashSet<Flag>();
        for(Flag flag : Flag.values()) {
            if(flag.getDefault()) {
                flags.add(flag);
            }
        }
        this.type = type;
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
        warp = (Location)(new LoadableLocation(recordSplit[3]));
        owner = recordSplit[4];
        members = new HashSet<String>(Arrays.asList(recordSplit[5].split(",")));
        data = recordSplit[5];
        flags = new HashSet<Flag>();
        for(String flag : recordSplit[6].split(",")) {
            flags.add(Flag.valueOf(flag));
        }
        type = Type.valueOf(recordSplit[7]);
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
    @Override
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(v1.toString());
        record.add(v2.toString());
        record.add(warp.toString());
        record.add(owner);
        record.add(StringUtils.join(members.toArray(), ","));
        record.add(data);
        record.add(StringUtils.join(flags.toArray(), ","));
        record.add(type.toString());
        return StringUtils.join(record.toArray(), "\t");
    }

    /**
     * Place single corner markers around the region.
     */
    public void placeCornerMarkers() {
        WEWorld world = new WEWorld(getWorld());

    }

    /**
     * Place 3 wide side markers around the region.
     */
    public void placeMoreMarkers() {
        WEWorld world = new WEWorld(getWorld());
        world.buildLine(v1.getBlockX(), (v1.getBlockZ() + v2.getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine(v2.getBlockX(), (v1.getBlockZ() + v2.getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine((v1.getBlockX() + v2.getBlockX()) / 2 - 3, v1.getBlockZ(), 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine((v1.getBlockX() + v2.getBlockX()) / 2 - 3, v2.getBlockZ(), 0, 6, Material.FENCE, Material.TORCH);
    }

    /**
     * Place 10 block high towers in each corner of the region.
     */
    public void placeTowers() {
        WEWorld world = new WEWorld(getWorld());
        world.buildTower(v1.getBlockX(), v1.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(v1.getBlockX(), v2.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(v2.getBlockX(), v1.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(v2.getBlockX(), v2.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
    }

    /**
     * Get the number of members this region has.
     * @return The number of members of this region.
     */
    public int getMemberNo() {
        return members.size();
    }

    /**
     * Checks if this region overlaps another region.
     * @param region Region to check.
     * @return <code>true</code> if this region overlaps with the other, <code>false</code> otherwise.
     */
    public boolean hasOverlap(Region region) {
        return !(v1.getX() > region.getV2().getX() || v2.getX() < region.getV1().getX() || v1.getZ() > region.getV2().getZ() || v2.getZ() < region.getV1().getZ() || v1.getY() > region.getV2().getY() || v2.getY() < region.getV1().getY());
    }

    /**
     * Set the warp point of a region.
     * @param location Location to set as warp.
     */
    public void setWarp(Location location) {
        warp = location;
    }

    /**
     * Get the name of the owner of this region.
     * @return Regions owner's name.
     */
    public String getOwner() {
        return owner;
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
     * Get the type of this region.
     * @return Type of the region.
     */
    public Type getType() {
        return type;
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
     * @return <code>true</code> if the player was not already a member of the region, <code>false</code> otherwise.
     */
    public boolean addMember(String name) {
        return members.add(name);
    }

    /**
     * Remove a player as a member of a region.
     * @param name Player name.
     * @return <code>true</code> if the player was a member of the region, <code>false</code> otherwise.
     */
    public boolean delMember(String name) {
        return members.remove(name);
    }

    /**
     * Get the regions minimum vector.
     * @return Vector 1.
     */
    public Vector getV1() {
        return v1;
    }

    /**
     * Get the regions maximum vector.
     * @return Vector 2.
     */
    public Vector getV2() {
        return v2;
    }

    /**
     * Get the world this region is in.
     * @return The regions world.
     */
    public World getWorld() {
        return warp.getWorld();
    }

    /**
     * Get the data stored with this region.
     * @return The region data.
     */
    public String getData() {
        return data;
    }

    /**
     * Check if a region has a certain flag set.
     * @param flag Flag to check.
     * @return <code>true</code> if this flag is set, <code>false</code> otherwise.
     */
    public boolean hasFlag(Flag flag) {
        return flags.contains(flag);
    }

    /**
     * Set a flag in this region.
     * @param flag Flag to set.
     * @return <code>true</code> if this flag was not already set, <code>false</code> otherwise.
     */
    public boolean setFlag(Flag flag) {
        return flags.add(flag);
    }

    /**
     * Toggle a flag in this region.
     * @param flag Flag to toggle.
     * @return <code>true</code> if this flag is now set, <code>false</code> otherwise.
     */
    public boolean toggleFlag(Flag flag) {
        if(!flags.add(flag)) {
            return !flags.remove(flag);
        }
        return true;
    }
}
