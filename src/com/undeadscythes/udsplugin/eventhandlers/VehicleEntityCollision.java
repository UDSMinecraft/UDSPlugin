package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.vehicle.*;

/**
 * When a vehicle collides with an entity.
 * @author UndeadScythes
 */
public class VehicleEntityCollision extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final VehicleEntityCollisionEvent event) {
        final Vehicle vehicle = event.getVehicle();
        if(vehicle instanceof Minecart) {
            final Minecart cart = (Minecart)vehicle;
            if(cart.getPassenger() != null) {
                final Entity collider = event.getEntity();
                if(collider instanceof LivingEntity) {
                    final LivingEntity victim = (LivingEntity)collider;
                    if(!(victim instanceof Player)) {
                        victim.remove();
                        event.setCancelled(true);
                        event.setCollisionCancelled(true);
                        event.setPickupCancelled(true);
                    }
                }
            }
        }
    }
}
