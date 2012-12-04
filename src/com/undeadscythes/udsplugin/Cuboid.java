package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.util.*;

/**
 * A clone of an area of blocks in a world.
 * @author UndeadScythes
 */
public class Cuboid {
    final private transient World world;
    final private transient Vector origin;
    final private transient int dX;
    final private transient int dY;
    final private transient int dZ;
    final private transient Block[][][] blocks;
    final private transient Vector pov;

    /**
     *
     * @param world
     * @param v1
     * @param v2
     */
    public Cuboid(final World world, final Vector v1, final Vector v2, final Location pov) {
        this.world = world;
        origin = Vector.getMinimum(v1, v2);
        dX = Math.abs(v1.getBlockX() - v2.getBlockX());
        dY = Math.abs(v1.getBlockY() - v2.getBlockY());
        dZ = Math.abs(v1.getBlockZ() - v2.getBlockZ());
        blocks = new Block[dX][dY][dZ];
        for(int x = 0; x < dX; x++) {
            for(int y = 0; y < dY; y++) {
                for(int z = 0; z < dZ; z++) {
                    blocks[x][y][z] = world.getBlockAt(origin.getBlockX() + x, origin.getBlockY() + y, origin.getBlockZ() + z);
                }
            }
        }
        this.pov = new Vector(pov.getBlockX(), pov.getBlockY(), pov.getBlockZ());
    }

    /**
     *
     * @return
     */
    public Vector getPOV() {
        return pov;
    }

    /**
     *
     * @return
     */
    public Vector getOffset() {
        return origin.clone().subtract(pov);
    }

    /**
     *
     * @return
     */
    public Vector getV1() {
        return origin;
    }

    /**
     *
     * @return
     */
    public Vector getV2() {
        return origin.clone().add(new Vector(dX, dY, dZ));
    }

    /**
     *
     * @return
     */
    public Vector getDV() {
        return new Vector(dX, dY, dZ);
    }

    /**
     *
     * @return
     */
    public int getVolume() {
        return dX * dY * dZ;
    }

    /**
     *
     * @return
     */
    public Block[][][] getBlocks() {
        return blocks.clone();
    }
}
