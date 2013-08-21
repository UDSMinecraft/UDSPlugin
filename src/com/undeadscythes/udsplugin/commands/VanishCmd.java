package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class VanishCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            if(player().toggleHidden()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap hide " + player().getName());
                PlayerUtils.addHiddenPlayer(player());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer).hasPerm(Perm.VANISH)) {
                        player().hideFrom(onlinePlayer, true);
                        try {
                            PlayerUtils.getOnlinePlayer(onlinePlayer).sendBroadcast(player().getNick() + " of " + player().getClan().getName() + " has left.");
                        } catch (NoMetadataSetException ex) {
                            PlayerUtils.getOnlinePlayer(onlinePlayer).sendBroadcast(player().getNick() + " has left.");
                        }
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer).sendWhisper(player().getNick() + " has vanished.");
                    }
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap show " + player().getName());
                PlayerUtils.removeHiddenPlayer(player().getName());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!PlayerUtils.getOnlinePlayer(onlinePlayer).hasPerm(Perm.VANISH)) {
                        player().hideFrom(onlinePlayer, false);
                        try {
                            PlayerUtils.getOnlinePlayer(onlinePlayer).sendBroadcast(player().getNick() + " of " + player().getClan().getName() + " has joined.");
                        } catch (NoMetadataSetException ex) {
                            PlayerUtils.getOnlinePlayer(onlinePlayer).sendBroadcast(player().getNick() + " has joined.");
                        }
                    } else {
                        PlayerUtils.getOnlinePlayer(onlinePlayer).sendWhisper(player().getNick() + " has reappeared.");
                    }
                }
            }
        } else if(numArgsHelp(1)) {
            if(subCmdEquals("list")) {
                player().sendNormal("Vanished players:");
                for(Member hiddenPlayer : PlayerUtils.getHiddenPlayers()) {
                    player().sendText(hiddenPlayer.getNick());
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
