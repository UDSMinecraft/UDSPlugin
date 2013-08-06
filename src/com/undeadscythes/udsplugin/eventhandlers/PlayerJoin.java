package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

/**
 * Fired when a player joins the server.
 * 
 * @author UndeadScythes
 */
public class PlayerJoin implements Listener {
    @EventHandler
    public final void onEvent(final PlayerJoinEvent event) {
        final String playerName = event.getPlayer().getName();
        SaveablePlayer player;
        if(PlayerUtils.playerExists(playerName)) {
            player = PlayerUtils.getPlayer(playerName);
            player.wrapPlayer(event.getPlayer());
            PlayerUtils.addOnlinePlayer(player);
        } else {
            player = new SaveablePlayer(event.getPlayer());
            PlayerUtils.addPlayer(player);
            PlayerUtils.addOnlinePlayer(player);
            if(player.getName().equals(Config.SERVER_OWNER)) {
                player.setRank(PlayerRank.OWNER);
                player.sendMessage(ChatColor.GOLD + "Welcome to your new server, I hope everything goes well.");
            } else {
                UDSPlugin.sendBroadcast("A new player, free gifts for everyone!");
                final ItemStack gift = new ItemStack(Config.WELCOME_GIFT);
                for(final SaveablePlayer onlinePlayer : PlayerUtils.getOnlinePlayers()) {
                    if(!onlinePlayer.isAfk()) {
                        onlinePlayer.giveAndDrop(gift);
                    }
                }
            }
            player.quietTeleport(UDSPlugin.getData().getSpawn());
        }
        if(UDSPlugin.isLockedDown() && !player.hasLockdownPass()) {
            player.kickPlayer("The server is currently in lockdown please check back later.");
        } else {
            player.sendNormal(Config.WELCOME_MSG);
            if(player.hasPermission(Perm.NEWBIEMSG)) {
                player.sendNormal("Kill monsters or trade with players to earn " + Config.BUILD_COST + " credits then type /acceptrules in chat.");
            } else if(player.hasPermission(Perm.ADMINMSG)) {
                player.sendMessage(Config.WELCOME_ADMIN);
            }
            player.newLogin(System.currentTimeMillis());
            if(player.isHidden()) {
                for(final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, true);
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendWhisper(player.getNick() + " has joined.");
                    }

                }
            } else {
                event.setJoinMessage(Color.CONNECTION + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has joined.");
            }
            if(!player.hasPermission(Perm.VANISH)) {
                for(final SaveablePlayer hiddenPlayer : PlayerUtils.getHiddenPlayers()) {
                    hiddenPlayer.hideFrom(event.getPlayer(), true);
                }
            }
        }
    }
}
