package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class BanCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(minArgsHelp(1) && (target = matchOtherPlayer(args[0])) != null) {
            String message = "You have been banned for breaking the rules.";
            if(args.length > 1) {
                message = argsToMessage(1);
            }
            try {
                PlayerUtils.getOnlinePlayer(target).getWorld().strikeLightningEffect(PlayerUtils.getOnlinePlayer(target).getLocation());
                PlayerUtils.getOnlinePlayer(target).kickPlayer(message);
            } catch (PlayerNotOnlineException ex) {}
            target.setBanned(true);
            UDSPlugin.sendBroadcast(target.getNick() + " has been banned for breaking the rules.");
        }
    }
}
