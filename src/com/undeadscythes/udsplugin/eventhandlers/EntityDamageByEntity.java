package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When one entity damages another.
 * @author UndeadScythes
 */
public class EntityDamageByEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event) {
        Entity attacker = getAbsoluteEntity(event.getDamager());
        Entity defender = event.getEntity();
        if(attacker instanceof Player && defender instanceof Player) {
            event.setCancelled(pvp(UDSPlugin.getOnlinePlayers().get(((Player)attacker).getName()), UDSPlugin.getOnlinePlayers().get(((Player)defender).getName())));
        } else {
            event.setCancelled((defender instanceof Player && UDSPlugin.getOnlinePlayers().get(((Player)defender).getName()).hasGodMode()) || (attacker instanceof Player && !Config.HOSTILE_MOBS.contains(defender.getType()) && hasFlag(defender.getLocation(), RegionFlag.PVE)));
        }
    }

    boolean pvp(SaveablePlayer attacker, SaveablePlayer defender) {
        return attacker.getClan().equals(defender.getClan()) || !defender.isInClan() || !attacker.isInClan() || !hasFlag(attacker.getLocation(), RegionFlag.PVP) || !hasFlag(defender.getLocation(), RegionFlag.PVP);
    }
}