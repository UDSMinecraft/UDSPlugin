package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * An extension of the base world class adding various WE-like methods.
 * @author UndeadScythes
 */
public class EditableWorld {
    private final transient World base;

    /**
     * Wrap an existing world with these lovely extra methods.
     * @param world World to wrap.
     */
    public EditableWorld(final World world) {
        base = world;
    }

    /**
     * Build a tower of blocks in the world with an optinoal topper.
     * @param x X coordinate of tower.
     * @param z Z coordinate of tower.
     * @param height Height of tower.
     * @param material Material to make the main tower out of.
     * @param topper Block to place on top, <code>null</code> if none required.
     */
    public void buildTower(final int x, final int z, final int height, final Material material, final Material topper) {
        final int y = base.getHighestBlockYAt(x, z);
        final int maxY = y + height;
        int i;
        for(i = y; i < maxY; i++) {
            base.getBlockAt(x, i, z).setType(material);
        }
        if(topper != null) {
            base.getBlockAt(x, i, z).setType(topper);
        }
    }

    /**
     * Place a line of blocks with optional toppers. Either dX or dZ should be 0 to indicate direction.
     * @param x X coordinate of line.
     * @param z Z coordinate of line.
     * @param dX Length of line in X axis.
     * @param dZ Length of line in Z axis.
     * @param material Material to make line out of.
     * @param topper Optional topper to place on top of the line, <code>null</code> if not required.
     */
    public void buildLine(final int x, final int z, final int dX, final int dZ, final Material material, final Material topper) {
        if(dX == 0) {
            final int maxZ = z + dZ;
            for(int i = z; i <= maxZ; i++) {
                final int y = base.getHighestBlockYAt(x, i);
                base.getBlockAt(x, y, i).setType(material);
                if(topper != null) {
                    base.getBlockAt(x, y + 1, i).setType(topper);
                }
            }
        } else {
            final int maxX = x + dX;
            for(int i = x; i <= maxX; i++) {
                final int y = base.getHighestBlockYAt(i, z);
                base.getBlockAt(i, y, z).setType(material);
                if(topper != null) {
                    base.getBlockAt(i, y + 1, z).setType(topper);
                }
            }
        }
    }
}
