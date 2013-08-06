package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * An extension of the base world class adding various WE-like methods.
 * 
 * @author UndeadScythes
 */
public class EditableWorld {
    private final World base;

    public EditableWorld(final World world) {
        base = world;
    }

    public final void buildTower(final int x, final int z, final int height, final Material material, final Material topper) {
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

    public final void buildLine(final int x, final int z, final int dX, final int dZ, final Material material, final Material topper) {
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
