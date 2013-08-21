package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class TPCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member targetFrom;
        Member targetTo;
        if(args.length == 1) {
            if((targetTo = matchOnlinePlayer(args[0])) != null && notSelf(targetTo)) {
                player().setBackPoint();
                player().teleport(targetTo);
            }
        } else if(numArgsHelp(2) && (targetFrom = matchOnlinePlayer(args[0])) != null && (targetTo = matchOnlinePlayer(args[1])) != null) {
            targetFrom.setBackPoint();
            targetFrom.teleport(targetTo);
        }
    }
}
