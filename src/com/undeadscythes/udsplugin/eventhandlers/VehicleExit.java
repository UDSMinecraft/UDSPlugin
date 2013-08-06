package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.timers.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;

/**
 * Fired when an entity leaves a vehicle.
 * If the vehicle is a minecart it is removed from the world.
 * If the entity is a player a minecart is added to their inventory or dropped.
 * 
 * @author UndeadScythes
 */
public class VehicleExit extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final VehicleExitEvent event) {
        if(event.getVehicle() instanceof Minecart) {
            MinecartCheck.removeMinecart(event.getVehicle().getUniqueId());
        }
    }
}
