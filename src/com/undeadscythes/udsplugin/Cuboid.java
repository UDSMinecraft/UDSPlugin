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
    final private transient Vector min;
    final private transient Vector max;
    final private transient Vector offset;
    final private transient Vector diagonal;
    final private transient int dx;
    final private transient int dy;
    final private transient int dz;
    final private transient int px;
    final private transient int py;
    final private transient int pz;
    final private transient MiniBlock[][][] blocks;

    /**
     *
     * @param world
     * @param v1
     * @param v2
     * @param pov
     */
    public Cuboid(final World world, final Vector v1, final Vector v2, final Vector pov) {
        this.world = world;
        min = Vector.getMinimum(v1, v2);
        px = min.getBlockX();
        py = min.getBlockY();
        pz = min.getBlockZ();
        max = Vector.getMaximum(v1, v2);
        diagonal = max.clone().subtract(min);
        dx = diagonal.getBlockX() + 1;
        dy = diagonal.getBlockY() + 1;
        dz = diagonal.getBlockZ() + 1;
        blocks = new MiniBlock[dx][dy][dz];
        for(int x = 0; x < dx; x++) {
            for(int y = 0; y < dy; y++) {
                for(int z = 0; z < dz; z++) {
                    blocks[x][y][z] = new MiniBlock(world.getBlockAt(px + x, py + y, pz + z));
                }
            }
        }
        offset = min.clone().subtract(blockify(pov));
    }

    /**
     *
     * @param vector
     * @return
     */
    private final Vector blockify(final Vector vector) {
        return new Vector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    /**
     *
     * @return
     */
    public int getVolume() {
        return dx * dy * dz;
    }

    /**
     *
     * @param world
     * @param place
     * @return
     */
    public Cuboid offset(final World world, final Vector place) {
        final Vector min = blockify(place).add(offset);
        final Cuboid undo = new Cuboid(world, min, min.clone().add(diagonal), blockify(place));
        place(world, min);
        return undo;
    }

    /**
     *
     */
    public void revert() {
        for(int x = 0; x < dx; x++) {
            for(int y = 0; y < dy; y++) {
                for(int z= 0; z < dz; z++) {
                    blocks[x][y][z].replace(world.getBlockAt(px + x, py + y, pz + z));
                }
            }
        }
    }

    /**
     *
     * @param world
     * @param place
     * @return
     */
    public Cuboid place(final World world, final Vector place) {
        final Cuboid undo = new Cuboid(world, place, place.clone().add(diagonal), new Vector());
        final int nx = place.getBlockX();
        final int ny = place.getBlockY();
        final int nz = place.getBlockZ();
        for(int x = 0; x < dx; x++) {
            for(int y = 0; y < dy; y++) {
                for(int z= 0; z < dz; z++) {
                    blocks[x][y][z].replace(world.getBlockAt(nx + x, ny + y, nz + z));
                }
            }
        }
        return undo;
    }
}

class MiniBlock {
    private final transient int type;
    private final transient byte data;

    public MiniBlock(final Block block) {
        type = block.getTypeId();
        data = block.getData();
    }

    public void replace(final Block block) {
        block.setTypeIdAndData(type, data, true);
    }
}
