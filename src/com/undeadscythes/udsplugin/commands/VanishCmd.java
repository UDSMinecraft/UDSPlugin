package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class VanishCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            if(player.toggleHidden()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap hide " + player.getName());
                MemberUtils.addHiddenMember(player.getOfflineMember());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!MemberUtils.getOnlineMember(onlinePlayer).hasPerm(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, true);
                        try {
                            MemberUtils.getOnlineMember(onlinePlayer).sendBroadcast(player.getNick() + " of " + player.getClan().getName() + " has left.");
                        } catch(NoMetadataSetException ex) {
                            MemberUtils.getOnlineMember(onlinePlayer).sendBroadcast(player.getNick() + " has left.");
                        }
                    } else {
                        MemberUtils.getOnlineMember(onlinePlayer).sendWhisper(player.getNick() + " has vanished.");
                    }
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dynmap show " + player.getName());
                MemberUtils.removeHiddenMember(player.getName());
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(!MemberUtils.getOnlineMember(onlinePlayer).hasPerm(Perm.VANISH)) {
                        player.hideFrom(onlinePlayer, false);
                        try {
                            MemberUtils.getOnlineMember(onlinePlayer).sendBroadcast(player.getNick() + " of " + player.getClan().getName() + " has joined.");
                        } catch(NoMetadataSetException ex) {
                            MemberUtils.getOnlineMember(onlinePlayer).sendBroadcast(player.getNick() + " has joined.");
                        }
                    } else {
                        MemberUtils.getOnlineMember(onlinePlayer).sendWhisper(player.getNick() + " has reappeared.");
                    }
                }
            }
        } else if(numArgsHelp(1)) {
            if(subCmdEquals("list")) {
                player.sendNormal("Vanished players:");
                for(OfflineMember hiddenPlayer : MemberUtils.getHiddenMembers()) {
                    player.sendText(hiddenPlayer.getNick());
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
