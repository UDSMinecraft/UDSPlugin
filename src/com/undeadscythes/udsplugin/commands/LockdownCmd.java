package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Handle server lockdown.
 * @author UndeadScythes
 */
public class LockdownCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            UDSPlugin.serverInLockdown ^= true;
            player.sendMessage(Color.MESSAGE + "The server is " + (UDSPlugin.serverInLockdown ? "now" : "no longer") + " in lockdown.");
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            target.toggleLockdownPass();
            player.sendMessage(Color.MESSAGE + target.getNick() + (target.hasLockdownPass() ? " now" : " no longer") + " has a lockdown pass.");
        }
    }
}
