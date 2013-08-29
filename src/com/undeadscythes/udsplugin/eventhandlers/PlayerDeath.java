package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * @author UndeadScythes
 */
public class PlayerDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerDeathEvent event) throws NoPlayerFoundException {
        final Member victim = MemberUtils.getOnlineMember(event.getEntity());
        final String victimName = victim.getName();
        event.setDeathMessage(event.getDeathMessage().replace(victimName, victim.getNick()));
        if(victim.hasPerm(Perm.BACK_ON_DEATH)) {
            victim.setBackPoint(victim.getLocation());
        }
        if(victim.getKiller() != null) {
            final Member killer = MemberUtils.getOnlineMember(victim.getKiller());
            if(killer != null) {
                pvp(killer, victim);
            } else {
                event.setDeathMessage(event.getDeathMessage().replace(" by ", " by a "));
            }
        }
        dropItems(victim);
        MemberUtils.saveInventory(victim);
        event.getDrops().clear();
        event.setNewTotalExp(9 * victim.getTotalExp() / 10);
        event.setDroppedExp(victim.getTotalExp() / 10);
    }

    private void clanKill(final Member killer, final Member victim) throws NoMetadataSetException, NoPlayerFoundException {
        final Clan victimClan = MemberUtils.getMember(victim.getName()).getClan();
        final Clan killerClan = MemberUtils.getMember(killer.getName()).getClan();
        if(!killerClan.getName().equals(victimClan.getName())) {
            killerClan.newKill();
        }
        victimClan.newDeath();
    }

    private void pvp(final Member killer, final Member victim) throws NoPlayerFoundException {
        if(victim.getBounty() > 0) {
                    bountyKill(killer, victim);
        }
        try {
            clanKill(killer, victim);
        } catch(NoMetadataSetException ex) {}
        dropHead(victim);
        killer.addKill();
    }

    private void dropItems(final Member victim) {
        final Random rng = new Random();
        if(victim.getKills() > 24 && rng.nextDouble() < 0.99) {
            dropItem(victim);
        }
        if(victim.getKills() > 14 && rng.nextDouble() < 0.9) {
            dropItem(victim);
        }
        if(victim.getKills() > 9 && rng.nextDouble() < 0.5) {
            dropItem(victim);
        }
        if(victim.getKills() > 4 && rng.nextDouble() < 0.2) {
            dropItem(victim);
        }
        if(victim.getKills() > 2 && rng.nextDouble() < 0.1) {
            dropItem(victim);
        }
        if(victim.getKills() > 0 && rng.nextDouble() < 0.05) {
            dropItem(victim);
        }
        if(rng.nextDouble() < 0.01) {
            dropItem(victim);
        }
    }

    private void dropItem(final Member victim) {
        final Random rng = new Random();
        ItemStack drop = victim.getInventory().getItem(rng.nextInt(36));
        if(drop != null) {
            victim.getWorld().dropItemNaturally(victim.getLocation(), drop.clone());
            drop.setType(Material.AIR);
        }
    }

    private void dropHead(final Member victim) {
        final Random rng = new Random();
        if(rng.nextDouble() < Config.SKULL || victim.hasPerm(Perm.HEADDROP)) {
            final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
            final SkullMeta meta = (SkullMeta)skull.getItemMeta();
            meta.setOwner(victim.getName());
            meta.setDisplayName(victim.getName() + "'s head");
            skull.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), skull);
        }
    }

    private void bountyKill(final Member killer, final Member victim) {
        final int total = victim.getBounty();
        UDSPlugin.sendBroadcast(killer.getNick() + " collected the " + total + " " + Config.CURRENCY + " bounty on " + victim.getNick() + ".");
        killer.credit(total);
        victim.setBounty(0);
    }
}
