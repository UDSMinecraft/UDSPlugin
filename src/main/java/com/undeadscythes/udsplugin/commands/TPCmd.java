package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class TPCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, PlayerNotOnlineException {
        if(args.length == 1) {
            Member targetTo = matchOtherOnlinePlayer(args[0]);
            player.setBackPoint();
            player.teleport(targetTo);
        } else if(numArgsHelp(2)) {
            Member targetTo = matchOnlinePlayer(args[1]);
            Member targetFrom = matchOnlinePlayer(args[0]);
            targetFrom.setBackPoint();
            targetFrom.teleport(targetTo);
        }
    }
}
