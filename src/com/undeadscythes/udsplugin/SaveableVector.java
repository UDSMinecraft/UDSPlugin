package com.undeadscythes.udsplugin;

import org.bukkit.util.*;

/**
 * A location that can be constructed by parsing a string.
 * 
 * @author UndeadScythes
 */
public class SaveableVector extends Vector {
    public SaveableVector(final String string) {
        super(Double.parseDouble(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]));
    }

    @Override
    public final String toString() {
        return super.getX() + "," + super.getY() + "," + super.getZ();
    }
}
