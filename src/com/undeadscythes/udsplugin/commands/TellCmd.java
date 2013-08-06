package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Hold a private conversation with another player.
 * 
 * @author UndeadScythes
 */
public class TellCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(2) && (target = matchOnlinePlayer(arg(0))) != null && notIgnoredBy(target)) {
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
