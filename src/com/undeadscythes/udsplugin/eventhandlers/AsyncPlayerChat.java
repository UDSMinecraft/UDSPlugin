package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import com.undeadscythes.udsplugin.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * Description
 * @author UndeadScythes
 */
public class AsyncPlayerChat implements Listener {
    /**
    * Chat channel for in-game chat.
    * @author UndeadScythes
    */
    public enum Channel {
        /**
        * Public chat, default.
        */
        PUBLIC,
        /**
        * Clan chat.
        */
        CLAN,
        /**
        * Private chat or chat rooms.
        */
        PRIVATE,
        /**
        * Administrative chat.
        */
        ADMIN;
    }

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) throws IOException {
        event.setCancelled(true);
        SaveablePlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        String logMessage = player.getDisplayName() + ": " + event.getMessage();
        Bukkit.getLogger().info(logMessage);
        if(!player.newChat()) {
            player.sendMessage(Color.ERROR + "You have been jailed for spamming chat.");
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " gets jail time for spamming chat.");
            player.jail(5, 1000);
        } else if(player.getChannel() == Channel.PUBLIC) {
            String message = event.getMessage();
            if(Censor.noCensor(message)) {
                message = player.getRank().color() + player.getDisplayName() + ": " + Color.TEXT + message;
            } else {
                player.sendMessage(Color.ERROR + "Please do not use bad language.");
                message = player.getRank().color() + player.getDisplayName() + ": " + Color.TEXT + Censor.fix(message);
                if(player.canAfford(1)) {
                    player.debit(1);
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " put 1 " + Config.CURRENCY + " in the swear jar.");
                } else {
                    player.sendMessage(Color.ERROR + "You have no money to put in the swear jar.");
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " gets jail time for using bad language.");
                    player.jail(1, 1);
                }
            }
            for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                if(!target.isIgnoringPlayer(player)) {
                    target.sendMessage(message);
                }
            }
        } else if(player.getChannel() == Channel.ADMIN) {
            String message = PlayerRank.ADMIN.color() + "[ADMIN] " + player.getDisplayName() + ": " + event.getMessage();
            for(SaveablePlayer target : UDSPlugin.getOnlinePlayers().values()) {
                if(target.getRank().compareTo(PlayerRank.MOD) >= 0) {
                    target.sendMessage(message);
                }
            }
        } else if(player.getChannel() == Channel.CLAN) {
            Clan clan = player.getClan();
            String message = Color.CLAN + "[" + clan.getName() + "] " + player.getDisplayName() + ": " + event.getMessage();
            for(SaveablePlayer target : clan.getOnlineMembers()) {
                target.sendMessage(message);
            }
        } else if(player.getChannel() == Channel.PRIVATE) {
            ChatRoom chatRoom = player.getChatRoom();
            String message = Color.PRIVATE + "[" + chatRoom.getName() + "] " + player.getDisplayName() + ": " + event.getMessage();
            for(SaveablePlayer target : chatRoom.getOnlineMembers()) {
                target.sendMessage(message);
            }
        }
    }
}
