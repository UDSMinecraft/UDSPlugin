package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Handle server lockdown.
 * @author UndeadScythes
 */
public class LockdownCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsLessEq(1)) {
            SaveablePlayer target;
            if(args.length == 0) {
                UDSPlugin.serverInLockdown ^= true;
                player.sendMessage(Color.MESSAGE + "The server is " + (UDSPlugin.serverInLockdown ? "now" : "no longer") + " in lockdown.");
            } else if((target = matchesPlayer(args[0])) != null) {
                target.toggleLockdownPass();
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + (target.hasLockdownPass() ? " now" : " no longer") + " has a lockdown pass.");
            }
        }
    }
}
