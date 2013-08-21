package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class KickCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(minArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null) {
            if(!target.hasPerm(Perm.UNKICKABLE)) {
                String message = "You have been kicked for breaking the rules.";
                if(args.length > 1) {
                    message = argsToMessage(1);
                }
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
                UDSPlugin.sendBroadcast(target.getNick() + " has been kicked for breaking the rules.");
            } else {
                player().sendError("You cannot kick this player.");
            }
        }
    }
}
