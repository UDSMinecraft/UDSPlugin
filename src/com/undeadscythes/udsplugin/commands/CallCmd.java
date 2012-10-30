package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class CallCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer target;
        if(argsEq(1) && (target = matchesPlayer(args[0])) != null && isOnline(target) && notIgnored(target) && notBusy(target) && notJailed() && notJailed(target) && notPinned() && notSelf(target)) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.TP, "", target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendMessage(Color.MESSAGE + player.getName() + " wishes to teleport to you.");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
