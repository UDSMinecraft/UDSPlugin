package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.logging.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * @author UndeadScythes
 */
public class EntityDamage extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            final Member player = PlayerUtils.getOnlinePlayer((Player)event.getEntity());
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
        final Member winner;
        try {
            winner = loser.getChallenger();
            winner.credit(2 * winner.getWager());
            winner.endChallenge();
            loser.endChallenge();
            loser.sendNormal("You lost the challenge.");
            try {
                PlayerUtils.getOnlinePlayer(winner).sendNormal("You won the challenge.");
                PlayerUtils.getOnlinePlayer(winner).setHealth(PlayerUtils.getOnlinePlayer(winner).getMaxHealth());
            } catch (PlayerNotOnlineException ex) {}
        } catch (NoMetadataSetException ex) {
            Logger.getLogger(EntityDamage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
