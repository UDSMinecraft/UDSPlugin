package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class RCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        Member target = getWhisperer();
        if(minArgsHelp(1) && matchOnlinePlayer(target.getName()) != null) {
            final String message = player.getNick() + " > " + target.getNick() + ": " + argsToMessage();
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
