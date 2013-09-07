package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.logging.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.*;

/**
 * @author UndeadScythes
 */
public class EntityDamage extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            final Member player = MemberUtils.getOnlineMember((Player)event.getEntity());
            if(player.hasGodMode() || player.isAfk() || (event.getCause().equals(DamageCause.DROWNING) && player.hasScuba())) {
                event.setCancelled(true);
            }
            if(((Damageable)event.getEntity()).getHealth() < event.getDamage()) {
                for(Region arena : RegionUtils.getRegions(RegionType.ARENA)) {
                    if(arena.contains(player.getLocation())) {
                        event.setCancelled(true);
                        player.teleport(arena.getWarp());
                        player.setHealth(player.getMaxHealth());
                    }
                }
                if(player.isDuelling()) {
                    challengeLoss(player);
                }
            }
        } else if(UDSPlugin.isPassiveMob(event.getEntity().getType()) && !hasFlag(event.getEntity().getLocation(), RegionFlag.PVE)) {
            event.setCancelled(true);
        }
    }

    private void challengeLoss(final Member loser) {
        final OfflineMember winner;
        try {
            winner = loser.getChallenger();
            winner.credit(2 * winner.getWager());
            winner.endChallenge();
            loser.endChallenge();
            loser.sendNormal("You lost the challenge.");
            try {
                MemberUtils.getOnlineMember(winner).sendNormal("You won the challenge.");
                MemberUtils.getOnlineMember(winner).setHealth(MemberUtils.getOnlineMember(winner).getMaxHealth());
            } catch(PlayerNotOnlineException ex) {}
        } catch(NoMetadataSetException ex) {
            Logger.getLogger(EntityDamage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
