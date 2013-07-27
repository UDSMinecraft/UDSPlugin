package com.undeadscythes.udsplugin;

import org.bukkit.util.*;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public class SaveableVector extends Vector {
    /**
     * Initialises a brand new location.
     * @param string The location in the format "world,x,y,z,pitch,yaw".
     */
    public SaveableVector(final String string) {
        super(Double.parseDouble(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]));
    }

    @Override
    public String toString() {
        return super.getX() + "," + super.getY() + "," + super.getZ();
    }
}
