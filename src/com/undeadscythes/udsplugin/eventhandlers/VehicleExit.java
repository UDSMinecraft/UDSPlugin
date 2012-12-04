package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.*;

/**
 * Fired when an entity leaves a vehicle.
 * If the vehicle is a minecart it is removed from the world.
 * If the entity is a player a minecart is added to their inventory or dropped.
 * @author UndeadScythes
 */
public class VehicleExit extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final VehicleExitEvent event) {
        if(event.getVehicle() instanceof Minecart) {
            if(event.getExited() instanceof Player) {
                final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(((Player)event.getExited()).getName());
                player.giveAndDrop(new ItemStack(Material.MINECART.getId()));
                player.sendMessage(Color.MESSAGE + "You picked up your minecart.");
            }
            event.getVehicle().remove();
        }
    }
}
