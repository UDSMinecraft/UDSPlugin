package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public final class SaveableLocation {
    /**
     * Initialises a brand new location.
     * @param string The location in the format "world,x,y,z,pitch,yaw".
     */
    public static Location parseLocation(final String string) {
        Location location = new Location(Bukkit.getWorld(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]), Double.parseDouble(string.split(",")[3]));
        location.setPitch(Float.parseFloat(string.split(",")[4]));
        location.setPitch(Float.parseFloat(string.split(",")[5]));
        return location;
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getString(final Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getPitch() + "," + location.getYaw();
    }

    private SaveableLocation() {}
}
