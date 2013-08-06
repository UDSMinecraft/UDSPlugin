package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.timers.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;

/**
 * Fired when a vehicle is created.
 * 
 * @author UndeadScythes
 */
public class VehicleCreate extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final VehicleCreateEvent event) {
        if(event.getVehicle() instanceof Minecart) {
            MinecartCheck.addMinecart((Minecart)event.getVehicle(), null);
        }
    }
}
