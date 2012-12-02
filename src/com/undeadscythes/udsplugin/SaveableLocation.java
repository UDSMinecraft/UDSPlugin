package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public class SaveableLocation extends Location {
    /**
     * Initialises a brand new location.
     * @param string The location in the format "world,x,y,z,pitch,yaw".
     */
    public SaveableLocation(final String string) {
        super(Bukkit.getWorld(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]), Double.parseDouble(string.split(",")[3]));
        setPitch(Float.parseFloat(string.split(",")[4]));
        setPitch(Float.parseFloat(string.split(",")[5]));
    }

    /**
     * Converts this location into a regular location.
     * @param location The location.
     */
    public SaveableLocation(final Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return super.getWorld().getName() + "," + super.getX() + "," + super.getY() + "," + super.getZ() + "," + super.getPitch() + "," + super.getYaw();
    }
}
