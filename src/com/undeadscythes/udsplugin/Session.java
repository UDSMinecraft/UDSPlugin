package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

/**
 * A WorldEdit session belonging to a player.
 * @author UndeadScythes
 */
public class Session {
    private World world = null;
    private Vector v1 = null;
    private Vector v2 = null;
    private Cuboid clipboard = null;
    private final Stack<Cuboid> history = new Stack<Cuboid>();

    /**
     * Get selection point 1.
     * @return Edit point 1.
     */
    public final Vector getV1() {
        return v1;
    }

    /**
     * Get selection point 2.
     * @return Edit point 2.
     */
    public final Vector getV2() {
        return v2;
    }

    /**
     * Get the volume of the selection.
     * @return Selection volume.
     */
    public final int getVolume() {
        return (Math.abs(v2.getBlockX() - v1.getBlockX()) + 1) * (Math.abs(v2.getBlockY() - v1.getBlockY()) + 1) * (Math.abs(v2.getBlockZ() - v1.getBlockZ()) + 1);
    }

    /**
     * Set selection point 1.
     * @param v1 Edit point 1.
     * @param world Selection world.
     */
    public final void setV1(final Vector v1, final World world) {
        this.v1 = v1;
        if(this.world != null && this.world != world) {
            v2 = null;
        }
        this.world = world;
    }

    /**
     * Set selection point 2.
     * @param v2 Edit point 2.
     * @param world Selection world.
     */
    public final void setV2(final Vector v2, final World world) {
        this.v2 = v2;
        if(this.world != null && this.world != world) {
            v1 = null;
        }
        this.world = world;

    }

    /**
     * Set both selection points at the same time.
     * @param v1 Selection point 1.
     * @param v2 Selection point 2.
     * @param world Selection world.
     */
    public final void setVPair(final Vector v1, final Vector v2, final World world) {
        this.v1 = v1;
        this.v2 = v2;
        this.world = world;
    }

    /**
     * Expand the selection as far as possible vertically.
     */
    public final void vert() {
        v1.setY(0);
        v2.setY(world.getMaxHeight());
    }

    /**
     * Check if the session has anything saved to the clipboard.
     * @return <code>true</code> if the clipboard is not empty, <code>false</code> otherwise.
     */
    public final boolean hasClipboard() {
        return clipboard != null;
    }

    /**
     * Get the clipboard contents.
     * @return Clipboard contents.
     */
    public final Cuboid getClipboard() {
        return clipboard;
    }

    /**
     * Set the clipboard contents.
     * @param blocks Clipboard contents.
     */
    public final void setClipboard(final Cuboid blocks) {
        clipboard = blocks;
    }

    /**
     * Check if this session has anything on the undo stack.
     * @return <code>true</code> if the undo stack is not empty, <code>false</code> otherwise.
     */
    public final boolean hasUndo() {
        return !history.empty();
    }

    /**
     * Get the next item on the undo stack.
     * @return Next undo item.
     */
    public final Cuboid load() {
        return history.pop();
    }

    /**
     * Add an item to the undo stack.
     * When the stack is full, as defined in the config.yml, the first element of the stack is dropped.
     * @param blocks Item to add to the undo stack.
     * @return <code>true</code> if the undo stack is full, <code>false</code> otherwise.
     */
    public final boolean save(final Cuboid blocks) {
        history.push(blocks);
        if(history.size() > UDSPlugin.getConfigInt(ConfigRef.UNDO_COUNT)) {
            history.remove(0);
            return true;
        }
        return false;
    }
    
    public final World getWorld() {
        return world;
    }
}
