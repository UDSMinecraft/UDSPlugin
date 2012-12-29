package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * When an entity is damaged.
 * @author UndeadScythes
 */
public class EntityDamage extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityDamageEvent event) {
        if((event.getEntity() instanceof Player)) {
            final SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(((Player)event.getEntity()).getName());
            if(player.hasGodMode() || player.isAfk() || (event.getCause().equals(DamageCause.DROWNING) && player.hasScuba())) {
                event.setCancelled(true);
            }
        } else if(UDSPlugin.getPassiveMobs().contains(event.getEntity().getType()) && !hasFlag(event.getEntity().getLocation(), RegionFlag.PVE)) {
            event.setCancelled(true);
        }
    }
}
