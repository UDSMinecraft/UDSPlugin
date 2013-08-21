package com.undeadscythes.udsplugin.utilities;

import com.undeadscythes.udsplugin.UDSPlugin;
import org.bukkit.*;
import org.bukkit.block.*;

/**
 * Utility class to handle manipulation of {@link org.bukkit.Location} objects.
 *
 * @author UndeadScythes
 */
public class LocationUtils {
    public static Location findSafePlace(final Location location) {
        final Location testUp = location;
        final Location testDown = location.clone().subtract(0, 1, 0);
        while(testUp.getBlockY() < UDSPlugin.BUILD_LIMIT && testDown.getBlockY() > 0) {
            if(!testUp.getBlock().getType().isSolid() && !testUp.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                return testUp;
            } else if(!testDown.getBlock().getType().isSolid() && !testDown.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                return testDown;
            } else {
                testUp.add(0, 1, 0);
                testDown.subtract(0, 1, 0);
            }
        }
        return null;
    }

    public static Location findFloor(final Location location) {
        final Location test = location.clone().subtract(0, 1, 0);
        while(test.getBlockY() > 0) {
            if(test.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                return test;
            } else {
                test.subtract(0, 1, 0);
            }
        }
        return UDSPlugin.getWorldSpawn(location.getWorld());
    }

    public static Location parseLocation(final String string) {
        Location location = new Location(Bukkit.getWorld(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]), Double.parseDouble(string.split(",")[3]));
        location.setPitch(Float.parseFloat(string.split(",")[4]));
        location.setPitch(Float.parseFloat(string.split(",")[5]));
        return location;
    }

    public static String getString(final Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getPitch() + "," + location.getYaw();
    }

    private LocationUtils() {}
}
