package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.util.*;


/**
 * A clone of an area of blocks in a world.
 *
 * @author UndeadScythes
 */
public class BlockBox extends Cuboid {
    final private Vector offset;
    final private Vector diagonal;
    final private int dx;
    final private int dy;
    final private int dz;
    final private int px;
    final private int py;
    final private int pz;
    final private MiniBlock[][][] blocks;

    public BlockBox(final World world, final Vector v1, final Vector v2, final Vector pov) {
        setWorld(world);
        setV1(Vector.getMinimum(v1, v2));
        px = getV1().getBlockX();
        py = getV1().getBlockY();
        pz = getV1().getBlockZ();
        setV2(Vector.getMaximum(v1, v2));
        diagonal = getV2().clone().subtract(getV1());
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
        offset = getV1().clone().subtract(blockify(pov));
    }

    private Vector blockify(final Vector vector) {
        return new Vector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public final BlockBox offset(final World world, final Vector place) {
        final Vector min = blockify(place).add(offset);
        final BlockBox undo = new BlockBox(world, min, min.clone().add(diagonal), blockify(place));
        place(world, min);
        return undo;
    }

    public final void revert() {
        for(int x = 0; x < dx; x++) {
            for(int y = 0; y < dy; y++) {
                for(int z= 0; z < dz; z++) {
                    blocks[x][y][z].replace(getWorld().getBlockAt(px + x, py + y, pz + z));
                }
            }
        }
    }

    public final BlockBox place(final World world, final Vector place) {
        final BlockBox undo = new BlockBox(world, place, place.clone().add(diagonal), new Vector());
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
