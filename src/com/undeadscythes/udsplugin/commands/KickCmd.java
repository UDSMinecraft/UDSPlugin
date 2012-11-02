package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Kick a player from the server.
 * @author UndeadScythes
 */
public class KickCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsMoreEq(1) && (target = matchesPlayer(args[0])) != null && isOnline(target)) {
            if(target.getRank().compareTo(Rank.MOD) < 0) {
                String message = "You have been kicked for breaking the rules.";
                if(args.length > 1) {
                    message = StringUtils.join(args, " ", 1, args.length - 1);
                }
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
                Bukkit.broadcastMessage(Color.BROADCAST + target.getDisplayName() + " has been kicked for breaking the rules.");
            } else {
                player.sendMessage(Color.ERROR + "You cannot kick this player.");
            }
        }
    }
}
