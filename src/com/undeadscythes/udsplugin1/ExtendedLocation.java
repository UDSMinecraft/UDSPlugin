package com.undeadscythes.udsplugin1;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public class ExtendedLocation extends Location {
    /**
     * Initialises a brand new location.
     * @param string The location in the format "world,x,y,z,pitch,yaw".
     */
    public ExtendedLocation(String string) {
        super(Bukkit.getWorld(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]), Double.parseDouble(string.split(",")[3]));
        setPitch(Float.parseFloat(string.split(",")[4]));
        setPitch(Float.parseFloat(string.split(",")[5]));
    }
}
