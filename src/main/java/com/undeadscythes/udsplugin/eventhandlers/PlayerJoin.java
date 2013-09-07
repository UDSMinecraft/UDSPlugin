package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class PlayerJoin implements Listener {
    @EventHandler
    public void onEvent(final PlayerJoinEvent event) {
        final String playerName = event.getPlayer().getName();
        Member player;
        try {
            player = new Member(event.getPlayer());
        } catch(NoPlayerFoundException ex) {
            player = new Member(new OfflineMember(event.getPlayer()));
        }
        player.setupPlayer();
        MemberUtils.addOnlineMember(player);
        if(!MemberUtils.memberExists(playerName)) {
            MemberUtils.addMember(player.getOfflineMember());
            if(player.getName().equals(Config.SERVER_OWNER)) {
                player.setRank(MemberRank.OWNER);
                player.sendMessage(ChatColor.GOLD + "Welcome to your new server, I hope everything goes well.");
            } else {
                UDSPlugin.sendBroadcast("A new player, free gifts for everyone!");
                final ItemStack gift = new ItemStack(Config.WELCOME_GIFT);
                for(final Member onlinePlayer : MemberUtils.getOnlineMembers()) {
                    if(!onlinePlayer.isAfk()) {
                        onlinePlayer.giveAndDrop(gift);
                    }
                }
            }
            player.quietTeleport(UDSPlugin.getData().spawn);
        }
        if(UDSPlugin.isLockedDown() && !player.hasLockdownPass()) {
            player.kickPlayer("The server is currently in lockdown please check back later.");
        } else {
            player.sendNormal(Config.WELCOME_MSG);
            if(player.hasPerm(Perm.NEWBIEMSG)) {
                player.sendNormal("Kill monsters or trade with players to earn " + Config.BUILD_COST + " credits then type /acceptrules in chat.");
            } else if(player.hasPerm(Perm.ADMINMSG)) {
                player.sendMessage(Config.WELCOME_ADMIN);
            }
            GameMode mode = UDSPlugin.getWorldMode(player.getWorld());
            if(mode != null && !player.hasPerm(Perm.ANYMODE)) {
                player.setGameMode(mode);
            }
            player.newLogin(System.currentTimeMillis());
            if(player.isHidden()) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "dynmap hide " + player.getName());
                for(final Member onlinePlayer : MemberUtils.getOnlineMembers()) {
                    if(!onlinePlayer.hasPerm(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer.getOfflineMember(), true);
                    } else {
                        onlinePlayer.sendWhisper(player.getNick() + " has joined.");
                    }
                }
            } else {
                try {
                    event.setJoinMessage(Color.CONNECTION + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has joined.");
                } catch(NoMetadataSetException ex) {}
            }
            if(!player.hasPerm(Perm.VANISH)) {
                for(final OfflineMember hiddenPlayer : MemberUtils.getHiddenMembers()) {
                    try {
                        MemberUtils.getOnlineMember(hiddenPlayer).hideFrom(event.getPlayer(), true);
                    } catch(PlayerNotOnlineException ex) {}
                }
            }
        }
        MemberUtils.updateMembers();
    }
}
