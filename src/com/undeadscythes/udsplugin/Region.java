package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.util.Vector;

/**
 * An area of blocks with in a world.
 * @author UndeadScythes
 */
public class Region implements Saveable {
    private String name;
    private Vector min;
    private Vector max;
    private Location warp;
    private SaveablePlayer owner;
    private HashSet<SaveablePlayer> members = new HashSet<SaveablePlayer>(0);
    private String data;
    private EnumSet<RegionFlag> flags = EnumSet.noneOf(RegionFlag.class);
    private RegionType type;
    private PlayerRank rank = PlayerRank.NONE;

    /**
     * Initialise a brand new region.
     * @param name Name of the region.
     * @param v1 Minimum block position.
     * @param v2 Maximum block position.
     * @param warp Warp location of the region.
     * @param owner Owner of the region.
     * @param data Data of region, if any.
     * @param type Region type.
     */
    public Region(final String name, final Vector v1, final Vector v2, final Location warp, final SaveablePlayer owner, final String data, final RegionType type) {
        this.name = name;
        this.min = floor(Vector.getMinimum(v1, v2));
        this.max = floor(Vector.getMaximum(v1, v2));
        this.warp = warp;
        this.owner = owner;
        this.data = data;
        for(RegionFlag flag : RegionFlag.values()) {
            if(flag.isDefaulted()) {
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
    public Region(final String record) throws IOException {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        min = getBlockPos(recordSplit[1]);
        max = getBlockPos(recordSplit[2]);
        warp = SaveableLocation.parseLocation(recordSplit[3]);
        owner = PlayerUtils.getPlayer(recordSplit[4]);
        members = new HashSet<SaveablePlayer>(0);
        if(!recordSplit[5].isEmpty()) {
            for(String member : recordSplit[5].split(",")) {
                members.add(PlayerUtils.getPlayer(member));
            }
        }
        data = recordSplit[6];
        for(String flag : recordSplit[7].split(",")) {
            if(RegionFlag.getByName(flag) != null) {
                flags.add(RegionFlag.getByName(flag));
            }
        }
        type = RegionType.getByName(recordSplit[8]);
        rank = PlayerRank.getByName(recordSplit[9]);
    }

    public static Vector floor(final Vector v) {
        return new Vector(v.getBlockX(), v.getBlockY(), v.getBlockZ());
    }
    
    /**
     * Helper function to build a new block position from a string.
     * @param string String containing coded block position.
     * @return The corresponding new block position.
     */
    public static Vector getBlockPos(final String string) {
        final String[] split = string.replace("(", "").replace(")", "").split(",");
        final double x = Double.parseDouble(split[0]);
        final double y = Double.parseDouble(split[1]);
        final double z = Double.parseDouble(split[2]);
        return new Vector(x, y, z);
    }

    @Override
    public String getRecord() {
        final ArrayList<String> record = new ArrayList<String>(10);
        record.add(name);
        record.add(min.toString());
        record.add(max.toString());
        record.add(SaveableLocation.getString(warp));
        record.add(owner == null ? "" : owner.getName());
        final ArrayList<String> memberList = new ArrayList<String>(0);
        for(SaveablePlayer member : members) {
            memberList.add(member.getName());
        }
        record.add(StringUtils.join(memberList, ","));
        record.add(data);
        record.add(StringUtils.join(flags.toArray(), ","));
        record.add(type.toString());
        record.add(rank.toString());
        return StringUtils.join(record.toArray(), "\t");
    }

    /**
     * Clear the members of the region.
     */
    public void clearMembers() {
        members = new HashSet<SaveablePlayer>(0);
    }

    @Override
    public String toString() {
        Bukkit.getLogger().info("Implicit Region.toString(). (" + Thread.currentThread().getStackTrace() + ")"); // Implicit .toString()
        return name;
    }

    /**
     * Change the owner of the region.
     * @param owner New owner name.
     */
    public void changeOwner(final SaveablePlayer owner) {
        this.owner = owner;
        rank = null;
    }

    /**
     * Get the region members.
     * @return Region members.
     */
    public Set<SaveablePlayer> getMembers() {
        return members;
    }

    /**
     *
     * @param type
     */
    public void setType(final RegionType type) {
        this.type = type;
    }

    /**
     * Change the name of the region.
     * @param newName New region name.
     */
    public void changeName(final String newName) {
        name = newName;
    }

    /**
     * Get the name of the owner of this region.
     * @return Owner name.
     */
    public String getOwnerName() {
        return owner == null ? "" : owner.getNick();
    }

    /**
     * Check if the player owns this region.
     * @param player Player to check.
     * @return <code>true</code> if this is the owner of the region, <code>false</code> otherwise.
     */
    public boolean isOwnedBy(final SaveablePlayer player) {
        return player != null && player.equals(owner);
    }

    /**
     *
     * @return
     */
    public String getMemberList() {
        String membersString = "";
        for(SaveablePlayer member : members) {
            membersString = membersString.concat(", " + member.getName());
        }
        return membersString.replaceFirst(", ", "");
    }

    /**
     *
     * @param player
     */
    public void sendInfo(final SaveablePlayer player) {
        player.sendNormal("Region " + name + " info:");
        player.sendText("Owner:" + (owner == null ? "" : " " + owner.getName()) + (rank.equals(PlayerRank.NONE) ? "" : " " + rank.toString() + "+"));
        player.sendText("Members: " + getMemberList());
        player.sendText("Type: " + type.toString());
        if(flags.isEmpty()) {
            player.sendText("No flags.");
        } else {
            String flagString = "";
            for(RegionFlag test : flags) {
                flagString = flagString.concat(test.toString() + ", ");
            }
            player.sendText("Flags: " + flagString.substring(0, flagString.length() - 2));
        }
        player.sendText("Volume: " + getVolume());
    }

    /**
     *
     * @return
     */
    public int getVolume() {
        return (max.getBlockX() - min.getBlockX() + 1) * (max.getBlockY() - min.getBlockY() + 1) * (max.getBlockZ() - min.getBlockZ() + 1);
    }

    /**
     * Expand this region in some direction.
     * @param direction Direction to expand.
     * @param distance Distance to expand.
     */
    public void expand(final Direction direction, final int distance) {
        if(direction.equals(Direction.NORTH)) {
            min.add(new Vector(0, 0, -distance));
        } else if(direction.equals(Direction.SOUTH)) {
            max.add(new Vector(0, 0, distance));
        } else if(direction.equals(Direction.EAST)) {
            max.add(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.WEST)) {
            min.add(new Vector(-distance, 0, 0));
        } else if(direction.equals(Direction.UP)) {
            max.add(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.DOWN)) {
            min.add(new Vector(0, -distance, 0));
        }
    }

    /**
     * Expand this region in some direction.
     * @param direction Direction to expand.
     * @param distance Distance to expand.
     */
    public void contract(final Direction direction, final int distance) {
        if(direction.equals(Direction.NORTH)) {
            max.add(new Vector(0, 0, -distance));
        } else if(direction.equals(Direction.SOUTH)) {
            min.add(new Vector(0, 0, distance));
        } else if(direction.equals(Direction.EAST)) {
            min.add(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.WEST)) {
            max.add(new Vector(-distance, 0, 0));
        } else if(direction.equals(Direction.UP)) {
            min.add(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.DOWN)) {
            max.add(new Vector(0, -distance, 0));
        }
    }

    /**
     * Get the region's rank.
     * @return Player rank of the region.
     */
    public PlayerRank getRank() {
        return rank;
    }

    /**
     * Set this regions player rank.
     * @param rank Rank to set.
     */
    public void setRank(final PlayerRank rank) {
        this.rank = rank;
        owner = null;
    }

    /**
     * Get this regions flags.
     * @return The region flags.
     */
    public Set<RegionFlag> getFlags() {
        return flags;
    }

    /**
     * Change this regions defining points.
     * @param v1 New v1.
     * @param v2 New v2.
     */
    public void changeV(final Vector v1, final Vector v2) {
        this.min = v1;
        this.max = v2;
    }

    /**
     * Place single corner markers around the region.
     */
    public void placeCornerMarkers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildTower(min.getBlockX(), min.getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(max.getBlockX(), min.getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(min.getBlockX(), max.getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(max.getBlockX(), max.getBlockZ(), 1, Material.FENCE, Material.TORCH);
    }

    /**
     * Place 3 wide side markers around the region.
     */
    public void placeMoreMarkers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildLine(min.getBlockX(), (min.getBlockZ() + max.getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine(max.getBlockX(), (min.getBlockZ() + max.getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine((min.getBlockX() + max.getBlockX()) / 2 - 3, min.getBlockZ(), 6, 0, Material.FENCE, Material.TORCH);
        world.buildLine((min.getBlockX() + max.getBlockX()) / 2 - 3, max.getBlockZ(), 6, 0, Material.FENCE, Material.TORCH);
    }

    /**
     * Place 10 block high towers in each corner of the region.
     */
    public void placeTowers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildTower(min.getBlockX(), min.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(min.getBlockX(), max.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(max.getBlockX(), min.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(max.getBlockX(), max.getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
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
    public boolean hasOverlap(final Region region) {
        return !(min.getX() > region.getV2().getX() || max.getX() < region.getV1().getX() || min.getZ() > region.getV2().getZ() || max.getZ() < region.getV1().getZ() || min.getY() > region.getV2().getY() || max.getY() < region.getV1().getY());
    }

    /**
     * Set the warp point of a region.
     * @param location Location to set as warp.
     */
    public void setWarp(final Location location) {
        warp = location;
    }

    /**
     * Get the name of the owner of this region.
     * @return Regions owner's name.
     */
    public SaveablePlayer getOwner() {
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
    public void rename(final String name) {
        this.name = name;
    }

    /**
     * Check if coordinates are contained within the region.
     * @param world World of coordinates.
     * @param x X-Coordinate.
     * @param y Y-Coordinate.
     * @param z Z-Coordinate.
     * @return <code>true</code> if the location is contained within the region, <code>false</code> otherwise.
     */
    private boolean contains(final World world, final double x, final double y, final double z) {
        return warp.getWorld().equals(world) && x > min.getX() && x < max.getX() + 1 && z > min.getZ() && z < max.getZ() + 1 && y > min.getY() && y < max.getY() + 1;
    }

    /**
     * Get the type of this region.
     * @return Type of the region.
     */
    public RegionType getType() {
        return type;
    }

    /**
     * Get the warp location of this region.
     * @return Location of region warp point.
     */
    public Location getWarp() {
        return Warp.findSafePlace(warp);
    }

    /**
     * Check if a player is a member of a region.
     * @param player Player name.
     * @return <code>true</code> if player is a member, <code>false</code> otherwise.
     */
    public boolean hasMember(final SaveablePlayer player) {
        return members.contains(player);
    }

    /**
     * Add a player as a member of the region.
     * @param player Player name.
     * @return <code>true</code> if the player was not already a member of the region, <code>false</code> otherwise.
     */
    public boolean addMember(final SaveablePlayer player) {
        return members.add(player);
    }

    /**
     * Remove a player as a member of a region.
     * @param player Player name.
     * @return <code>true</code> if the player was a member of the region, <code>false</code> otherwise.
     */
    public boolean delMember(final SaveablePlayer player) {
        return members.remove(player);
    }

    /**
     * Get the regions minimum vector.
     * @return Vector 1.
     */
    public Vector getV1() {
        return min;
    }

    /**
     * Get the regions maximum vector.
     * @return Vector 2.
     */
    public Vector getV2() {
        return max;
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
    
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * Check if a region has a certain flag set.
     * @param flag Flag to check.
     * @return <code>true</code> if this flag is set, <code>false</code> otherwise.
     */
    public boolean hasFlag(final RegionFlag flag) {
        return flags.contains(flag);
    }

    /**
     * Set a flag in this region.
     * @param flag Flag to set.
     * @return <code>true</code> if this flag was not already set, <code>false</code> otherwise.
     */
    public boolean setFlag(final RegionFlag flag) {
        return flags.add(flag);
    }

    /**
     * Toggle a flag in this region.
     * @param flag Flag to toggle.
     * @return <code>true</code> if this flag is now set, <code>false</code> otherwise.
     */
    public boolean toggleFlag(final RegionFlag flag) {
        if(!flags.add(flag)) {
            return !flags.remove(flag);
        }
        return true;
    }

    /**
     *
     * @param location
     * @return
     */
    public boolean contains(final Location location) {
        return location.toVector().isInAABB(min, max);
    }
    
    public void replace(final Material from, final Material to) {
        final World world = warp.getWorld();
        for(int x = min.getBlockX(); x < max.getBlockX(); x++) {
            for(int y = min.getBlockY(); y < max.getBlockY(); y++) {
                for(int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    final Block block = world.getBlockAt(x, y, z);
                    if(!block.getType().equals(from)) {
                        continue;
                    }
                    block.setType(to);
                }
            }
        }
    }
}
