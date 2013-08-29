package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.commands.*;
import java.io.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class AsyncPlayerChat implements Listener {
    @EventHandler
    public void onEvent(final AsyncPlayerChatEvent event) throws IOException {
        event.setCancelled(true);
        final Member player = MemberUtils.getOnlineMember(event.getPlayer());
        final String logMessage = player.getNick() + ": " + event.getMessage();
        Bukkit.getLogger().info(logMessage);
        if(!player.newChat()) {
            player.sendError("You have been jailed for spamming chat.");
            UDSPlugin.sendBroadcast(player.getNick() + " gets jail time for spamming chat.");
            JailCmd.jail(player, 5, 1000);
        } else if(player.getChannel() == ChatChannel.PUBLIC) {
            String message = event.getMessage();
            if(Censor.noCensor(message)) {
                message = (player.getRankColor() + player.getNick() + ": " + Color.TEXT).concat(message);
            } else {
                player.sendError("Please do not use bad language.");
                message = player.getRankColor() + player.getNick() + ": " + Color.TEXT + Censor.fix(message);
                if(player.canAfford(1)) {
                    player.debit(1);
                    UDSPlugin.sendBroadcast(player.getNick() + " put 1 " + Config.CURRENCY + " in the swear jar.");
                } else {
                    player.sendError("You have no money to put in the swear jar.");
                    UDSPlugin.sendBroadcast(player.getNick() + " gets jail time for using bad language.");
                    JailCmd.jail(player, 1, 1);
                }
            }
            for(Member target : MemberUtils.getOnlineMembers()) {
                if(!target.isIgnoringPlayer(player.getOfflineMember())) {
                    target.sendMessage(message);
                }
            }
        } else if(player.getChannel() == ChatChannel.ADMIN) {
            final String message = MemberRank.ADMIN.getColor() + "[ADMIN] " + player.getNick() + ": " + event.getMessage();
            for(Member target : MemberUtils.getOnlineMembers()) {
                if(target.hasPerm(Perm.ADMINCHAT)) {
                    target.sendMessage(message);
                }
            }
        } else if(player.getChannel() == ChatChannel.CLAN) {
            try {
                final Clan clan = player.getClan();
                final String message = "[" + clan.getName() + "] " + player.getNick() + ": " + event.getMessage();
                for(Member target : clan.getOnlineMembers()) {
                    target.sendClan(message);
                }
            } catch(NoMetadataSetException ex) {}
        } else if(player.getChannel() == ChatChannel.PRIVATE) {
            try {
                final ChatRoom chatRoom = player.getChatRoom();
                final String message = "[" + chatRoom.getName() + "] " + player.getNick() + ": " + event.getMessage();
                for(Member target : chatRoom.getMembers()) {
                    target.sendPrivate(message);
                }
            } catch(NoMetadataSetException ex) {}
        }
    }
}
