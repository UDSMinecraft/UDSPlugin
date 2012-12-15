package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;

/**
 * When a vehicle is destroyed.
 * @author UndeadScythes
 */
public class VehicleCreate extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final VehicleCreateEvent event) {
        if(event.getVehicle() instanceof Minecart) {
            EntityTracker.addMinecart((Minecart)event.getVehicle(), null, false);
        }
    }
}
