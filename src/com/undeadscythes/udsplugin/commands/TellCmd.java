package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class TellCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(minArgsHelp(2) && (target = matchOnlinePlayer(args[0])) != null && notIgnoredBy(target)) {
            final String message = player().getNick() + " > " + target.getNick() + ": " + argsToMessage(1);
            player().sendWhisper(message);
            if(target.isAfk()) {
                player().sendWhisper(target.getNick() + " is currently AFK.");
            }
            target.sendWhisper(message);
            player().setWhisperer(target);
            target.setWhisperer(player());
        }
    }
}
