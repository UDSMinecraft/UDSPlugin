package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Ban a player from the server.
 * @author UndeadScythes
 */
public class BanCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = matchOtherPlayer(args[0])) != null) {
            String message = "You have been banned for breaking the rules.";
            if(args.length > 1) {
                message = StringUtils.join(args, " ", 1, args.length - 1);
            }
            if(isOnline(target)) {
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.kickPlayer(message);
            }
            target.setBanned(true);
            UDSPlugin.sendBroadcast(target.getNick() + " has been banned for breaking the rules.");
        }
    }
}
