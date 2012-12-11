package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

/**
 * A WorldEdit session belonging to a player.
 * @author UndeadScythes
 */
public class WESession {
    private Vector v1 = null;
    private Vector v2 = null;
    private transient World world = null;
    private Cuboid clipboard = null;
    private Stack<Cuboid> history = new Stack<Cuboid>();

    /**
     *
     * @return
     */
    public Vector getV1() {
        return v1;
    }

    /**
     *
     * @param v1
     */
    public void setV1(final Vector v1, final World world) {
        this.v1 = v1;
        if(this.world != null && this.world != world) {
            v2 = null;
        }
        this.world = world;
    }

    /**
     *
     * @return
     */
    public Vector getV2() {
        return v2;
    }

    /**
     *
     * @param v2
     */
    public void setV2(final Vector v2, final World world) {
        this.v2 = v2;
        if(this.world != null && this.world != world) {
            v1 = null;
        }
        this.world = world;

    }

    /**
     *
     * @param v1
     * @param v2
     * @param world
     */
    public void setVPair(final Vector v1, final Vector v2, final World world) {
        this.v1 = v1;
        this.v2 = v2;
        this.world = world;
    }

    /**
     *
     * @param world
     */
    public void vert() {
        v1.setY(0);
        v2.setY(world.getMaxHeight());
    }

    /**
     *
     * @return
     */
    public int getVolume() {
        return (Math.abs(v2.getBlockX() - v1.getBlockX()) + 1) * (Math.abs(v2.getBlockY() - v1.getBlockY()) + 1) * (Math.abs(v2.getBlockZ() - v1.getBlockZ()) + 1);
    }

    /**
     *
     * @return
     */
    public Cuboid getClipboard() {
        return clipboard;
    }

    /**
     *
     * @param blocks
     */
    public void setClipboard(final Cuboid blocks) {
        clipboard = blocks;
    }

    public boolean hasClipboard() {
        return clipboard != null;
    }

    /**
     *
     * @return
     */
    public boolean hasUndo() {
        return !history.empty();
    }

    /**
     *
     * @param blocks
     */
    public void save(final Cuboid blocks) {
        history.push(blocks);
    }

    /**
     *
     * @return
     */
    public Cuboid load() {
        return history.pop();
    }
}
