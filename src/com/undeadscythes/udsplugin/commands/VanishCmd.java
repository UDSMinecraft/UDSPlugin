package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
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
                PlayerUtils.addHiddenPlayer(player);
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, true);
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendBroadcast(player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has left.");
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendWhisper(player.getNick() + " has vanished.");
                    }
                }
            } else {
                PlayerUtils.removeHiddenPlayer(player.getName());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).hasPermission(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, false);
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendBroadcast(player.getNick() + (player.isInClan() ? " of " + player.getClan().getName() : "") + " has joined.");
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendWhisper(player.getNick() + " has reappeared.");
                    }
                }
            }
        } else if(numArgsHelp(1)) {
            if("list".equals(subCmd)) {
                player.sendNormal("Vanished players:");
                for(SaveablePlayer hiddenPlayer : PlayerUtils.getHiddenPlayers()) {
                    player.sendText(hiddenPlayer.getNick());
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
