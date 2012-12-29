package com.undeadscythes.udsplugin.eventhandlers;

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
    public void onEvent(final EntityDamageByEntityEvent event) {
        final Entity attacker = getAbsoluteEntity(event.getDamager());
        final Entity defender = event.getEntity();
        if(attacker instanceof Player && defender instanceof Player) {
            event.setCancelled(pvp(UDSPlugin.getOnlinePlayers().get(((Player)attacker).getName()), UDSPlugin.getOnlinePlayers().get(((Player)defender).getName())));
        } else {
            event.setCancelled(godMode(defender) || pve(defender));
        }
    }

    private boolean pvp(final SaveablePlayer attacker, final SaveablePlayer defender) {
        if(!defender.isInClan()) {
            attacker.sendMessage(Color.MESSAGE + "This player is not in a clan.");
            return true;
        } else if(!attacker.isInClan()) {
            attacker.sendMessage(Color.MESSAGE + "You need to be in a clan to PvP.");
            return true;
        } else {
            return defender.hasGodMode() || attacker.getClan().equals(defender.getClan()) || !hasFlag(attacker.getLocation(), RegionFlag.PVP) || !hasFlag(defender.getLocation(), RegionFlag.PVP);
        }
    }

    private boolean godMode(final Entity defender) {
        return defender instanceof Player && UDSPlugin.getOnlinePlayers().get(((Player)defender).getName()).hasGodMode();
    }

    private boolean pve(final Entity defender) {
        return UDSPlugin.getPassiveMobs().contains(defender.getType()) && !hasFlag(defender.getLocation(), RegionFlag.PVE);
    }
}
