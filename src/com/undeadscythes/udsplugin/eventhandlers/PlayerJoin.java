package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

/**
 * When a player logs onto the server.
 * @author UndeadScythes
 */
public class PlayerJoin implements Listener {
    @EventHandler
    public void onEvent(final PlayerJoinEvent event) {
        final String playerName = event.getPlayer().getName();
        SaveablePlayer player;
        if(UDSPlugin.getPlayers().containsKey(playerName)) {
            player = UDSPlugin.getPlayers().get(playerName);
            player.wrapPlayer(event.getPlayer());
            UDSPlugin.getOnlinePlayers().put(playerName, player);
        } else {
            player = new SaveablePlayer(event.getPlayer());
            UDSPlugin.getPlayers().put(playerName, player);
            UDSPlugin.getOnlinePlayers().put(playerName, player);
            if(player.getName().equals(UDSPlugin.getConfigString(ConfigRef.SERVER_OWNER))) {
                player.setRank(PlayerRank.OWNER);
                player.sendMessage(ChatColor.GOLD + "Welcome to your new server, I hope everything goes well.");
            } else {
                Bukkit.broadcastMessage(Color.BROADCAST + "A new player, free gifts for everyone!");
                final ItemStack gift = new ItemStack(UDSPlugin.getConfigMaterial(ConfigRef.WELCOME_GIFT));
                for(SaveablePlayer onlinePlayer : UDSPlugin.getOnlinePlayers().values()) {
                    onlinePlayer.giveAndDrop(gift);
                }
            }
            player.quietTeleport(UDSPlugin.getWarps().get("spawn"));
        }
        if(UDSPlugin.isLockedDown() && !player.hasLockdownPass()) {
            player.kickPlayer("The server is currently in lockdown please check back later.");
        } else {
            player.sendMessage(Color.MESSAGE + UDSPlugin.getConfigString(ConfigRef.WELCOME_MSG));
            if(player.getRank().equals(PlayerRank.DEFAULT)) {
                player.sendMessage(Color.MESSAGE + "Kill monsters or trade with players to earn " + UDSPlugin.getConfigInt(ConfigRef.BUILD_COST) + " credits then type /acceptrules in chat.");
            } else if(player.getRank().compareTo(PlayerRank.MOD) >= 0) {
                player.sendMessage(UDSPlugin.getConfigString(ConfigRef.WELCOME_ADMIN));
            }
            if(player.isHidden()) {
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!UDSPlugin.getOnlinePlayers().get(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, true);
                    } else {
                        onlinePlayer.sendMessage(Color.WHISPER + player.getNick() + " has joined.");
                    }

                }
            } else {
                event.setJoinMessage(Color.BROADCAST + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has joined.");
            }
            if(!player.hasPermission(Perm.VANISH)) {
                for(SaveablePlayer hiddenPlayer : UDSPlugin.getHiddenPlayers().values()) {
                    hiddenPlayer.hideFrom(event.getPlayer(), true);
                }
            }
        }
    }
}
