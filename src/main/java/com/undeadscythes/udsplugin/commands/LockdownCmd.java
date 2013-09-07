package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class LockdownCmd extends CommandHandler {
    @Override
    public void playerExecute() throws NoPlayerFoundException {
        OfflineMember target;
        if(args.length == 0) {
            UDSPlugin.toggleLockdown();
            player.sendNormal("The server is " + (UDSPlugin.isLockedDown() ? "now" : "no longer") + " in lockdown.");
        } else if(numArgsHelp(1) && (target = matchPlayer(args[0])) != null) {
            target.toggleLockdownPass();
            player.sendNormal(target.getNick() + (target.hasLockdownPass() ? " now" : " no longer") + " has a lockdown pass.");
        }
    }
}
