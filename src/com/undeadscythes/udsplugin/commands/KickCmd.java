package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Kick a player from the server.
 * @author UndeadScythes
 */
public class KickCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isOnline(target)) {
            if(target.getRank().compareTo(PlayerRank.MOD) < 0) {
                String message = "You have been kicked for breaking the rules.";
                if(args.length >= 2) {
                    message = StringUtils.join(args, " ", 1, args.length - 1);
                }
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
                Bukkit.broadcastMessage(Color.BROADCAST + target.getNick() + " has been kicked for breaking the rules.");
            } else {
                player.sendMessage(Color.ERROR + "You cannot kick this player.");
            }
        }
    }
}
