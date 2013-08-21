package com.undeadscythes.udsplugin.exceptions;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoRegionFoundException extends Exception {
    public NoRegionFoundException(RegionType type, Location location) {
        super("No regions found at " + location.toVector().toString() + " in world " + location.getWorld().getName() + ".");
    }
}
