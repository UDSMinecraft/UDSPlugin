package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Ban a player from the server.
 * @author UndeadScythes
 */
public class BanCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsMoreEq(1) && (target = matchesPlayer(args[0])) != null && notSelf(target)) {
            String message = "You have been banned for breaking the rules.";
            if(args.length > 1) {
                message = StringUtils.join(args, " ", 1, args.length - 1);
            }
            if(isOnline(target)) {
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
                target.setBanned(true);
            } else {
                target.setBanned(true);
            }
            Bukkit.broadcastMessage(Color.BROADCAST + target.getDisplayName() + " has been banned for breaking the rules.");
        }
    }
}
