package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When one entity damages another.
 * @author UndeadScythes
 */
public class EntityDamageByEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityDamageByEntityEvent event) {
        final Entity attacker = getAbsoluteEntity(event.getDamager());
        final Entity defender = event.getEntity();
        if(attacker instanceof Player && defender instanceof Player) {
            event.setCancelled(pvp(PlayerUtils.getOnlinePlayer(((Player)attacker).getName()), PlayerUtils.getOnlinePlayer(((Player)defender).getName())));
        } else {
            event.setCancelled(isAfk(defender) || godMode(defender) || pve(defender));
        }
    }

    private boolean pvp(final SaveablePlayer attacker, final SaveablePlayer defender) {
        return !attacker.hasPvp() || !defender.hasPvp() || defender.hasGodMode() || defender.isAfk() || !(attacker.isInClan() && defender.isInClan()) || attacker.getClan().equals(defender.getClan()) || !hasFlag(attacker.getLocation(), RegionFlag.PVP) || !hasFlag(defender.getLocation(), RegionFlag.PVP);
    }

    private boolean isAfk(final Entity defender) {
        return defender instanceof Player && PlayerUtils.getOnlinePlayer(((Player)defender).getName()).isAfk();
    }

    private boolean godMode(final Entity defender) {
        return defender instanceof Player && PlayerUtils.getOnlinePlayer(((Player)defender).getName()).hasGodMode();
    }

    private boolean pve(final Entity defender) {
        return UDSPlugin.getPassiveMobs().contains(defender.getType()) && !hasFlag(defender.getLocation(), RegionFlag.PVE);
    }
}
