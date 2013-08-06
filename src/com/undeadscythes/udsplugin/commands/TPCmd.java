package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport players to each other.
 * 
 * @author UndeadScythes
 */
public class TPCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer targetFrom;
        SaveablePlayer targetTo;
        if(argsLength() == 1) {
            if((targetTo = matchOnlinePlayer(arg(0))) != null && notSelf(targetTo)) {
                player().setBackPoint();
                player().teleport(targetTo);
            }
        } else if(numArgsHelp(2) && (targetFrom = matchOnlinePlayer(arg(0))) != null && (targetTo = matchOnlinePlayer(arg(1))) != null) {
            targetFrom.setBackPoint();
            targetFrom.teleport(targetTo);
        }
    }
}
