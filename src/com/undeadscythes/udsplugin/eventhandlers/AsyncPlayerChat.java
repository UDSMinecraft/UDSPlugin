package com.undeadscythes.udsplugin.eventhandlers;

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
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) throws IOException {
        event.setCancelled(true);
        ExtendedPlayer player = UDSPlugin.getOnlinePlayers().get(event.getPlayer().getName());
        if(player.getChannel() == Channel.PUBLIC) {
            if(Censor.censor(event.getMessage())) {
                String message = player.getRank().color() + player.getDisplayName() + ": " + Color.TEXT + event.getMessage();
                for(ExtendedPlayer target : UDSPlugin.getOnlinePlayers().values()) {
                    if(!target.isIgnoringPlayer(player)) {
                        target.sendMessage(message);
                    }
                }
            } else {
                player.sendMessage(Message.BAD_LANGUAGE);
                if(player.canAfford(1)) {
                    player.debit(1);
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " put 1 " + Config.CURRENCY + " in the swear jar.");
                } else {
                    player.sendMessage(Message.CANT_PAY_SWEARJAR);
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " gets jail time for using bad language.");
                    player.jail(1, 1);
                }
            }
        } else if(player.getChannel() == Channel.ADMIN) {
            String message = ExtendedPlayer.Rank.ADMIN.color() + "[ADMIN] " + player.getDisplayName() + ": " + event.getMessage();
            for(ExtendedPlayer target : UDSPlugin.getOnlinePlayers().values()) {
                if(target.getRank().compareTo(ExtendedPlayer.Rank.MOD) >= 0) {
                    target.sendMessage(message);
                }
            }
        } else if(player.getChannel() == Channel.CLAN) {
            Clan clan = UDSPlugin.getClans().get(player.getClan());
            if(clan != null) {
                String message = Color.CLAN + "[" + clan.getName() + "] " + player.getDisplayName() + ": " + event.getMessage();
                for(ExtendedPlayer target : clan.getOnlineMembers()) {
                    target.sendMessage(message);
                }
            } else {
                player.sendMessage(Message.NOT_IN_CLAN);
            }
        } else if(player.getChannel() == Channel.PRIVATE) {
            ChatRoom chatRoom = player.getChatRoom();
            String message = Color.PRIVATE + "[" + chatRoom.getName() + "] " + player.getDisplayName() + ": " + event.getMessage();
            for(ExtendedPlayer target : chatRoom.getOnlineMembers()) {
                target.sendMessage(message);
            }
        }
    }
}
