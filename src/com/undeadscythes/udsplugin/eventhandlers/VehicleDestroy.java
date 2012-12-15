package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.*;

/**
 * When a vehicle is destroyed.
 * @author UndeadScythes
 */
public class VehicleDestroy extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final VehicleDestroyEvent event) {
        final Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Boat) {
            if(event.getAttacker() != null) {
                vehicle.getWorld().dropItemNaturally(vehicle.getLocation(), new ItemStack(Material.BOAT));
                vehicle.remove();
            }
            event.setCancelled(true);
        } else if(vehicle instanceof Minecart) {
            EntityTracker.minecartRemoved(vehicle.getUniqueId());
        }
    }
}
