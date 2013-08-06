package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Handle server lockdown.
 * 
 * @author UndeadScythes
 */
public class LockdownCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(argsLength() == 0) {
            UDSPlugin.toggleLockdown();
            player().sendNormal("The server is " + (UDSPlugin.isLockedDown() ? "now" : "no longer") + " in lockdown.");
        } else if(numArgsHelp(1) && (target = matchPlayer(arg(0))) != null) {
            target.toggleLockdownPass();
            player().sendNormal(target.getNick() + (target.hasLockdownPass() ? " now" : " no longer") + " has a lockdown pass.");
        }
    }
}
