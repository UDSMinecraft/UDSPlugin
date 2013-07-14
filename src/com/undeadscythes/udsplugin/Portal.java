package com.undeadscythes.udsplugin;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * A nether portal that goes elsewhere.
 * @author Dave
 */
public class Portal implements Saveable {
    /**
     * File name of portal file.
     */
    public static final String PATH = "portals.csv";

    private final String name;
    private Warp warp;
    private final World world;
    private final Vector min, max;

    public Portal(final String name, final Warp warp, final World world, final Vector v1, final Vector v2) {
        this.name = name;
        this.warp = warp;
        this.world = world;
        this.min = Region.floor(Vector.getMinimum(v1, v2));
        this.max = Region.floor(Vector.getMaximum(v1, v2));
    }
    
    public Portal(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        warp = UDSPlugin.getWarps().get(recordSplit[1]);
        world = Bukkit.getWorld(recordSplit[2]);
        min = Region.getBlockPos(recordSplit[3]);
        max = Region.getBlockPos(recordSplit[4]);
    }

    @Override
    public final String getRecord() {
        final ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(warp.getName());
        record.add(world.getName());
        record.add(min.toString());
        record.add(max.toString());
        return StringUtils.join(record, "\t");
    }
    
    public final Warp getWarp() {
        return warp;
    }
    
    public final void setWarp(final Warp warp) {
        this.warp = warp;
    }
    
    public final String getName() {
        return name;
    }
    
    public final Vector getV1() {
        return min;
    }
    
    public final Vector getV2() {
        return max;
    }
    
    public final World getWorld() {
        return world;
    }
}
