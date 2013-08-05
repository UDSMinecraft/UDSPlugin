package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Request to teleport to another player.
 * @author UndeadScythes
 */
public class CallCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(numArgsHelp(1) && (target = matchesOtherOnlinePlayer(arg(0))) != null && canRequest(target) && notJailed(target) && canTeleport()) {
            UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.TP, "", target));
            player().sendMessage(Message.REQUEST_SENT);
            target.sendNormal(player().getName() + " wishes to teleport to you.");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
