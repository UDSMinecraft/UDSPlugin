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
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(1) && (target = matchesOtherOnlinePlayer(args[0])) != null && canRequest(target) && notJailed(target) && canTP()) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.TP, "", target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendMessage(Color.MESSAGE + player.getName() + " wishes to teleport to you.");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
