package com.undeadscythes.udsplugin.exceptions;

import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoRegionFoundException extends PlayerException {
    public NoRegionFoundException(RegionType type, Location location) {
        super("No regions found at this location.");
    }
}
