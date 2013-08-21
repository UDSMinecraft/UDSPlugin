package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class CallCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(numArgsHelp(1) && (target = matchOtherOnlinePlayer(args[0])) != null && canRequest(target) && notJailed(target) && canTeleport()) {
            UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.TP, "", target));
            player().sendMessage(Message.REQUEST_SENT);
            target.sendNormal(player().getName() + " wishes to teleport to you.");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
