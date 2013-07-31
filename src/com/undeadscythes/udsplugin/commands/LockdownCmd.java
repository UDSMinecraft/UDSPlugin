package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Handle server lockdown.
 * @author UndeadScythes
 */
public class LockdownCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            UDSPlugin.toggleLockdown();
            player.sendNormal("The server is " + (UDSPlugin.isLockedDown() ? "now" : "no longer") + " in lockdown.");
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            target.toggleLockdownPass();
            player.sendNormal(target.getNick() + (target.hasLockdownPass() ? " now" : " no longer") + " has a lockdown pass.");
        }
    }
}
