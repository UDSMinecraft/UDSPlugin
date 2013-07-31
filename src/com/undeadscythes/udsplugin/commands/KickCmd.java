package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Kick a player from the server.
 * @author UndeadScythes
 */
public class KickCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null && isOnline(target)) {
            if(!target.hasPermission(Perm.UNKICKABLE)) {
                String message = "You have been kicked for breaking the rules.";
                if(args.length >= 2) {
                    message = StringUtils.join(args, " ", 1, args.length - 1);
                }
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
                UDSPlugin.sendBroadcast(target.getNick() + " has been kicked for breaking the rules.");
            } else {
                player.sendError("You cannot kick this player.");
            }
        }
    }
}
