package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * When a player dies.
 * @author UndeadScythes
 */
public class PlayerDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerDeathEvent event) {
        final SaveablePlayer victim = PlayerUtils.getOnlinePlayer(event.getEntity().getName());
        final String victimName = victim.getName();
        final SaveablePlayer udsVictim = PlayerUtils.getOnlinePlayer(victimName);
        event.setDeathMessage(event.getDeathMessage().replace(victimName, udsVictim.getNick()));
        if(victim.hasPermission(Perm.BACK_ON_DEATH)) {
            udsVictim.setBackPoint(victim.getLocation());
        }
        if(victim.getKiller() != null) {
            final SaveablePlayer killer = PlayerUtils.getOnlinePlayer(victim.getKiller().getName());
            if(killer != null) {
                pvp(killer, victim);
            }
        }
    }

    private void clanKill(final SaveablePlayer killer, final SaveablePlayer victim) {
        final Clan victimClan = PlayerUtils.getPlayer(victim.getName()).getClan();
        final Clan killerClan = PlayerUtils.getPlayer(killer.getName()).getClan();
        if(!killerClan.getName().equals(victimClan.getName())) {
            killerClan.newKill();
        }
        victimClan.newDeath();
    }

    private void pvp(final SaveablePlayer udsKiller, final SaveablePlayer udsVictim) {
        if(udsVictim.getBounty() > 0) {
                    bountyKill(udsKiller, udsVictim);
        }
        if(udsKiller.isInClan() && udsVictim.isInClan()) {
            clanKill(udsKiller, udsVictim);
        }
        final Random rng = new Random();
        if(rng.nextDouble() < UDSPlugin.getConfigDouble(ConfigRef.SKULL) || udsVictim.hasRank(PlayerRank.MOD)) {
            final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
            final SkullMeta meta = (SkullMeta)skull.getItemMeta();
            meta.setOwner(udsVictim.getName());
            meta.setDisplayName(udsVictim.getName() + "'s head");
            skull.setItemMeta(meta);
            udsKiller.getWorld().dropItemNaturally(udsVictim.getLocation(), skull);
        }
    }

    private void bountyKill(final SaveablePlayer udsKiller, final SaveablePlayer udsVictim) {
        final int total = udsVictim.getBounty();
        UDSPlugin.sendBroadcast(udsKiller.getNick() + " collected the " + total + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCY) + " bounty on " + udsVictim.getNick() + ".");
        udsKiller.credit(total);
        udsVictim.setBounty(0);
    }
}
