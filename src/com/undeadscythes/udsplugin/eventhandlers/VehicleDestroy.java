package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.timers.MinecartCheck;
import com.undeadscythes.udsplugin.ListenerWrapper;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.*;

/**
 * Fired when a vehicle is destroyed.
 * 
 * @author UndeadScythes
 */
public class VehicleDestroy extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final VehicleDestroyEvent event) {
        final Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Boat) {
            if(event.getAttacker() != null) {
                vehicle.getWorld().dropItemNaturally(vehicle.getLocation(), new ItemStack(Material.BOAT));
                vehicle.remove();
            }
            event.setCancelled(true);
        } else if(vehicle instanceof Minecart) {
            MinecartCheck.minecartRemoved(vehicle.getUniqueId());
        }
    }
}
