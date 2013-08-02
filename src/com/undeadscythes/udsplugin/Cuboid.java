package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.util.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class Cuboid {
    private Vector v1 = null;
    private Vector v2 = null;
    private World world = null;
    private int dx = 0;
    private int dy = 0;
    private int dz = 0;
    private int volume = 0;
    
    public final void setPoint1(final Location location) {
        if(world != null && !location.getWorld().equals(world)) {
            v2 = null;
        }
        v1 = location.toVector();
        world = location.getWorld();
        resetCache();
    }
    
    public final void setPoint2(final Location location) {
        if(world != null && !location.getWorld().equals(world)) {
            v1 = null;
        }
        v2 = location.toVector();
        world = location.getWorld();
        resetCache();
    }
    
    public final Vector getV1() {
        return v1;
    }
    
    public final void setV1(final Vector vector) {
        v1 = vector;
    }
    
    public final Vector getV2() {
        return v2;
    }
    
    public final void setV2(final Vector vector) {
        v2 = vector;
    }
    
    /**
     * Change this cuboid's defining points.
     * @param v1 New v1.
     * @param v2 New v2.
     */
    public final void setPoints(final Vector v1, final Vector v2) {
        setV1(v1);
        setV2(v2);
    }
    
    /**
     * Expand the selection as far as possible vertically.
     */
    public final void vert() {
        v1.setY(0);
        v2.setY(world.getMaxHeight());
    }
    
    public final World getWorld() {
        return world;
    }
    
    public final void setWorld(final World world) {
        this.world = world;
    }
    
    /**
     * Checks if this region overlaps another cuboid.
     * @param cuboid Cuboid to check.
     * @return <code>true</code> if this cuboid overlaps with the other.
     */
    public final boolean hasOverlap(final Cuboid cuboid) {
        return !(getV1().getX() > cuboid.getV2().getX()
                || getV2().getX() < cuboid.getV1().getX()
                || getV1().getZ() > cuboid.getV2().getZ()
                || getV2().getZ() < cuboid.getV1().getZ()
                || getV1().getY() > cuboid.getV2().getY()
                || getV2().getY() < cuboid.getV1().getY());
    }
    
    public final String getV1String() {
        return v1.toString();
    }
    
    public final String getV2String() {
        return v2.toString();
    }
    
    public final int getDX() {
        if(dx == 0) {
            dx = Math.abs(v1.getBlockX() - v2.getBlockX()) + 1;
        }
        return dx;
    }
    
    public final int getDY() {
        if(dy == 0) {
            dy = Math.abs(v1.getBlockY() - v2.getBlockY()) + 1;
        }
        return dy;
    }
    
    public final int getDZ() {
        if(dz == 0) {
            dz = Math.abs(v1.getBlockZ() - v2.getBlockZ()) + 1;
        }
        return dz;
    }
    
    public final int getVolume() {
        if(volume == 0) {
            volume = getDX() * getDY() * getDZ();
        }
        return volume;
    }
    
    public final void resetCache() {
        dx = 0;
        dy = 0;
        dz = 0;
        volume = 0;
    }
    
    /**
     * Expand this cuboid in some direction.
     * @param direction Direction to expand.
     * @param distance Distance to expand.
     */
    public final void expand(final Direction direction, final int distance) {
        if(direction.equals(Direction.WEST)) {
            v1.subtract(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.DOWN)) {
            v1.subtract(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.NORTH)) {
            v1.subtract(new Vector(0, 0, distance));
        } else if(direction.equals(Direction.EAST)) {
            v2.add(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.UP)) {
            v2.add(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.SOUTH)) {
            v2.add(new Vector(0, 0, distance));
        }
    }
    
    /**
     * Expand this cuboid in some direction.
     * @param direction Direction to contract.
     * @param distance Distance to contract.
     */
    public final void contract(final Direction direction, final int distance) {
        if(direction.equals(Direction.EAST)) {
            v1.add(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.UP)) {
            v1.add(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.SOUTH)) {
            v1.add(new Vector(0, 0, distance));
        } else if(direction.equals(Direction.WEST)) {
            v2.subtract(new Vector(distance, 0, 0));
        } else if(direction.equals(Direction.DOWN)) {
            v2.subtract(new Vector(0, distance, 0));
        } else if(direction.equals(Direction.NORTH)) {
            v2.subtract(new Vector(0, 0, distance));
        }
    }
}
