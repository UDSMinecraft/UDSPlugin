package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class UnJailCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(numArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null && isJailed(target)) {
            target.release();
            target.sendNormal("You have been released early.");
            player().sendNormal("You have released " + target.getNick() + ".");
        }
    }
}
