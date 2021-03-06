package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Vanishes a player.
 *
 * @author UndeadScythes
 */
public class VanishCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 0) {
            if(player().toggleHidden()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap hide " + player().getName());
                PlayerUtils.addHiddenPlayer(player());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).hasPerm(Perm.VANISH)) {
                        player().hideFrom(onlinePlayer, true);
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendBroadcast(player().getNick() + (player().isInClan() ? " of " + player().getClan().getName() : "") + " has left.");
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendWhisper(player().getNick() + " has vanished.");
                    }
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap show " + player().getName());
                PlayerUtils.removeHiddenPlayer(player().getName());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).hasPerm(Perm.VANISH)) {
                        player().hideFrom(onlinePlayer, false);
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendBroadcast(player().getNick() + (player().isInClan() ? " of " + player().getClan().getName() : "") + " has joined.");
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer.getName()).sendWhisper(player().getNick() + " has reappeared.");
                    }
                }
            }
        } else if(numArgsHelp(1)) {
            if(subCmdEquals("list")) {
                player().sendNormal("Vanished players:");
                for(SaveablePlayer hiddenPlayer : PlayerUtils.getHiddenPlayers()) {
                    player().sendText(hiddenPlayer.getNick());
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
