package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Request to teleport to another player.
 * @author UndeadScythes
 */
public class CallCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = getMatchingOtherOnlinePlayer(args[0])) != null && canRequest(target) && notJailed(target) && canTP()) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.RequestType.TP, "", target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendMessage(Color.MESSAGE + player.getName() + " wishes to teleport to you.");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
