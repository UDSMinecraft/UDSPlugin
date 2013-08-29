package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class UnBanCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        if(numArgsHelp(1)) {
            OfflineMember target = matchOtherPlayer(args[0]);
            if(isBanned(target)) {
                target.setBanned(false);
                player.sendNormal(target.getNick() + " is no longer banned.");
            }
        }
    }
}
