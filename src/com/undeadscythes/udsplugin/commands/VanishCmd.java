package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Color;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class VanishCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        if(args.length == 0) {
            if(player.toggleHidden()) {
                UDSPlugin.getHiddenPlayers().put(player.getName(), player);
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!UDSPlugin.getOnlinePlayers().get(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, true);
                        onlinePlayer.sendMessage(Color.BROADCAST + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has left.");
                    } else {
                        onlinePlayer.sendMessage(Color.WHISPER + player.getNick() + " has vanished.");
                    }
                }
            } else {
                UDSPlugin.getHiddenPlayers().remove(player.getName());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!UDSPlugin.getOnlinePlayers().get(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, false);
                        onlinePlayer.sendMessage(Color.BROADCAST + player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has joined.");
                    } else {
                        onlinePlayer.sendMessage(Color.WHISPER + player.getNick() + " has reappeared.");
                    }
                }
            }
        } else if(numArgsHelp(1)) {
            if("list".equals(subCmd)) {
                player.sendMessage(Color.MESSAGE + "Vanished players:");
                for(SaveablePlayer hiddenPlayer : UDSPlugin.getHiddenPlayers().values()) {
                    player.sendMessage(Color.TEXT + hiddenPlayer.getNick());
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
