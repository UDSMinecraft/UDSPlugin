package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class UnJailCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        if(numArgsHelp(1)) {
            Member target = matchOnlinePlayer(args[0]);
            if(isJailed(target)) {
                target.release();
                target.sendNormal("You have been released early.");
                player.sendNormal("You have released " + target.getNick() + ".");
            }
        }
    }
}
