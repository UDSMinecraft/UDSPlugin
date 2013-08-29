package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class TellCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        if(minArgsHelp(2)) {
            Member target = matchOnlinePlayer(args[0]);
            if(notIgnoredBy(target.getOfflineMember())) {
                final String message = player.getNick() + " > " + target.getNick() + ": " + argsToMessage(1);
                player.sendWhisper(message);
                if(target.isAfk()) {
                    player.sendWhisper(target.getNick() + " is currently AFK.");
                }
                target.sendWhisper(message);
                player.setWhisperer(target);
                target.setWhisperer(player);
            }
        }
    }
}
