package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.*;

/**
 * When a player leaves a vehicle.
 * @author UndeadScythes
 */
public class VehicleExit extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(VehicleExitEvent event) {
        if(event.getVehicle() instanceof Minecart) {
            if(event.getExited() instanceof Player) {
                Player player = (Player) event.getExited();
                player.getInventory().addItem(new ItemStack(Material.MINECART.getId()));
            }
            event.setCancelled(true);
            event.getVehicle().remove();
        }
    }
}
