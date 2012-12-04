package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When a player dies.
 * @author UndeadScythes
 */
public class PlayerDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        SaveablePlayer victim = UDSPlugin.getOnlinePlayers().get(event.getEntity().getName());
        String victimName = victim.getName();
        SaveablePlayer udsVictim = UDSPlugin.getOnlinePlayers().get(victimName);
        event.setDeathMessage(event.getDeathMessage().replace(victimName, udsVictim.getNick()));
        if(victim.hasPermission(Perm.BACK_ON_DEATH)) {
            udsVictim.setBackPoint(victim.getLocation());
        }
        if(victim.getKiller() != null) {
            SaveablePlayer killer = UDSPlugin.getOnlinePlayers().get(victim.getKiller().getName());
            if(killer != null) {
                if(udsVictim.isDuelling()) {
                    if(udsVictim.getChallenger().equals(killer)) {
                        event.getDrops().clear();
                        event.setDroppedExp(0);
                        event.setKeepLevel(true);
                        challengeWin(killer, victim);
                    } else {
                        challengeDraw(victim);
                    }
                } else {
                    pvp(killer, victim);
                }
            }
        }
    }

    public void challengeWin(SaveablePlayer killer, SaveablePlayer victim) {
        SaveablePlayer udsKiller = UDSPlugin.getOnlinePlayers().get(killer.getName());
        SaveablePlayer udsVictim = UDSPlugin.getOnlinePlayers().get(victim.getName());
        udsVictim.saveItems();
        udsKiller.credit(2 * udsVictim.getWager());
        udsKiller.endChallenge();
        killer.sendMessage(Color.MESSAGE + "You won the challenge.");
    }

    public void challengeDraw(SaveablePlayer victim) {
        SaveablePlayer udsVictim = UDSPlugin.getOnlinePlayers().get(victim.getName());
        SaveablePlayer challenger = udsVictim.getChallenger();
        challenger.credit(udsVictim.getWager());
        udsVictim.credit(udsVictim.getWager());
        challenger.endChallenge();
        challenger.sendMessage(Color.MESSAGE + "The challenge was a draw.");
    }

    public void clanKill(SaveablePlayer killer, SaveablePlayer victim) {
        Clan victimClan = UDSPlugin.getPlayers().get(victim.getName()).getClan();
        Clan killerClan = UDSPlugin.getPlayers().get(killer.getName()).getClan();
        if(!killerClan.getName().equals(victimClan.getName())) {
            killerClan.newKill();
        }
        victimClan.newDeath();
    }

    public void pvp(SaveablePlayer udsKiller, SaveablePlayer udsVictim) {
        if(udsVictim.getBounty() > 0) {
                    bountyKill(udsKiller, udsVictim);
        }
        if(udsKiller.isInClan() && udsVictim.isInClan()) {
            clanKill(udsKiller, udsVictim);
        }
    }

    public void bountyKill(SaveablePlayer udsKiller, SaveablePlayer udsVictim) {
        int total = udsVictim.getBounty();
        Bukkit.broadcastMessage(Color.BROADCAST + udsKiller.getNick() + " collected the " + total + " " + Config.currency + " bounty on " + udsVictim.getNick() + ".");
        udsKiller.credit(total);
        udsVictim.setBounty(0);
    }
}
