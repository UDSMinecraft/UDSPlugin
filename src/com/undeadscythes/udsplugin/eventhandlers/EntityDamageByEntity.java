package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * @author UndeadScythes
 */
public class EntityDamageByEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityDamageByEntityEvent event) {
        final Entity attacker = getAbsoluteEntity(event.getDamager());
        final Entity defender = event.getEntity();
        if(attacker instanceof Player && defender instanceof Player) {
            event.setCancelled(pvp(MemberUtils.getOnlineMember((Player)attacker), MemberUtils.getOnlineMember((Player)defender)));
        } else {
            event.setCancelled(isAfk(defender) || godMode(defender) || pve(defender));
        }
    }

    private boolean pvp(final Member attacker, final Member defender) {
        try {
            return attacker.getClan().equals(defender.getClan());
        } catch(NoMetadataSetException ex) {
            return !attacker.hasPvp() || !defender.hasPvp() || defender.hasGodMode() || defender.isAfk() || !hasFlag(attacker.getLocation(), RegionFlag.PVP) || !hasFlag(defender.getLocation(), RegionFlag.PVP);
        }
    }

    private boolean isAfk(final Entity defender) {
        return defender instanceof Player && MemberUtils.getOnlineMember((Player)defender).isAfk();
    }

    private boolean godMode(final Entity defender) {
        return defender instanceof Player && MemberUtils.getOnlineMember((Player)defender).hasGodMode();
    }

    private boolean pve(final Entity defender) {
        return UDSPlugin.isPassiveMob(defender.getType()) && !hasFlag(defender.getLocation(), RegionFlag.PVE);
    }
}
