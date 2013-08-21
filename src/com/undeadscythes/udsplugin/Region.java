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
 *
 * @author UndeadScythes
 */
public class Region extends Cuboid implements Saveable {
    private String name;
    private Location warp;
    private Member owner;
    private HashSet<Member> members = new HashSet<Member>(0);
    private String data;
    private EnumSet<RegionFlag> flags = EnumSet.noneOf(RegionFlag.class);
    private RegionType type;
    private PlayerRank rank;

    public Region(final String name, final Vector v1, final Vector v2, final Location warp, final Member owner, final String data, final RegionType type) {
        this.name = name;
        setV1(VectorUtils.getFlooredVector(Vector.getMinimum(v1, v2)));
        setV2(VectorUtils.getFlooredVector(Vector.getMaximum(v1, v2)));
        this.warp = warp;
        setWorld(warp.getWorld());
        this.owner = owner;
        this.data = data;
        for(RegionFlag flag : RegionFlag.values()) {
            if(flag.isDefaulted()) {
                flags.add(flag);
            }
        }
        this.type = type;
    }

    public Region(final String record) throws IOException {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        setV1(VectorUtils.getVector(recordSplit[1]));
        setV2(VectorUtils.getVector(recordSplit[2]));
        warp = LocationUtils.parseLocation(recordSplit[3]);
        setWorld(warp.getWorld());
        owner = PlayerUtils.getPlayer(recordSplit[4]);
        members = new HashSet<Member>(0);
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

    @Override
    public String getRecord() {
        final ArrayList<String> record = new ArrayList<String>(10);
        record.add(name);
        record.add(getV1String());
        record.add(getV2String());
        record.add(LocationUtils.getString(warp));
        record.add(owner == null ? "null" : owner.getName());
        final ArrayList<String> memberList = new ArrayList<String>(0);
        for(Member member : members) {
            memberList.add(member.getName());
        }
        record.add(StringUtils.join(memberList, ","));
        record.add(data);
        record.add(StringUtils.join(flags.toArray(), ","));
        record.add(type.toString());
        record.add(rank == null ? "null" : rank.toString());
        return StringUtils.join(record.toArray(), "\t");
    }

    public void clearMembers() {
        members = new HashSet<Member>(0);
    }

    public void changeOwner(final Member owner) {
        this.owner = owner;
        rank = null;
    }

    public Set<Member> getMembers() {
        return members; //TODO: Check calls to this method and add methods for each one.
    }

    public void setType(final RegionType type) {
        this.type = type;
    }

    public String getOwnerName() {
        return owner == null ? "" : owner.getNick();
    }

    public boolean isOwnedBy(final Member player) {
        return player != null && player.equals(owner);
    }

    public String getMemberList() {
        String membersString = "";
        for(Member member : members) {
            membersString = membersString.concat(", " + member.getName());
        }
        return membersString.replaceFirst(", ", "");
    }

    public void sendInfo(final Member player) {
        player.sendNormal("Region " + name + " info:");
        player.sendText("Owner:" + (owner == null ? "" : " " + owner.getName()) + (rank == null ? "" : " " + rank.toString() + "+"));
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

    public PlayerRank getRank() {
        return rank;
    }

    public void setRank(final PlayerRank rank) {
        this.rank = rank;
        owner = null;
    }

    public Set<RegionFlag> getFlags() {
        return flags; //TODO: find calls to this method and create new methods for each case.
    }

    public void placeCornerMarkers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildTower(getV1().getBlockX(), getV1().getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(getV2().getBlockX(), getV1().getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(getV1().getBlockX(), getV2().getBlockZ(), 1, Material.FENCE, Material.TORCH);
        world.buildTower(getV2().getBlockX(), getV2().getBlockZ(), 1, Material.FENCE, Material.TORCH);
    }

    public void placeMoreMarkers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildLine(getV1().getBlockX(), (getV1().getBlockZ() + getV2().getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine(getV2().getBlockX(), (getV1().getBlockZ() + getV2().getBlockZ()) / 2 - 3, 0, 6, Material.FENCE, Material.TORCH);
        world.buildLine((getV1().getBlockX() + getV2().getBlockX()) / 2 - 3, getV1().getBlockZ(), 6, 0, Material.FENCE, Material.TORCH);
        world.buildLine((getV1().getBlockX() + getV2().getBlockX()) / 2 - 3, getV2().getBlockZ(), 6, 0, Material.FENCE, Material.TORCH);
    }

    public void placeTowers() {
        final EditableWorld world = new EditableWorld(getWorld());
        world.buildTower(getV1().getBlockX(), getV1().getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(getV1().getBlockX(), getV2().getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(getV2().getBlockX(), getV1().getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
        world.buildTower(getV2().getBlockX(), getV2().getBlockZ(), 10, Material.FENCE, Material.GLOWSTONE);
    }

    public int getMemberNo() {
        return members.size();
    }

    public void setWarp(final Location location) {
        warp = location;
    }

    public Member getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void rename(final String name) {
        this.name = name;
    }

    public RegionType getType() {
        return type;
    }

    public Location getWarp() {
        return LocationUtils.findSafePlace(warp);
    }

    public boolean hasMember(final Member player) {
        return members.contains(player);
    }

    public boolean addMember(final Member player) {
        return members.add(player);
    }

    public boolean delMember(final Member player) {
        return members.remove(player);
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public boolean hasFlag(final RegionFlag flag) {
        return flags.contains(flag);
    }

    public boolean setFlag(final RegionFlag flag) {
        return flags.add(flag);
    }

    public boolean toggleFlag(final RegionFlag flag) {
        if(!flags.add(flag)) {
            return !flags.remove(flag);
        }
        return true;
    }

    public boolean contains(final Location location) {
        return location.toVector().isInAABB(getV1(), getV2());
    }

    public void replace(final Material from, final Material to) {
        final World world = warp.getWorld();
        for(int x = getV1().getBlockX(); x < getV2().getBlockX(); x++) {
            for(int y = getV1().getBlockY(); y < getV2().getBlockY(); y++) {
                for(int z = getV1().getBlockZ(); z < getV2().getBlockZ(); z++) {
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
