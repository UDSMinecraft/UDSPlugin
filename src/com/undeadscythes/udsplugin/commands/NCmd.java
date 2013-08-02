package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Deny a request.
 * @author UndeadScythes
 */
public class NCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final Request request = getRequest();
        if(request != null) {
            UDSPlugin.removeRequest(player.getName());
            final SaveablePlayer sender = PlayerUtils.getPlayer(request.getSender().getName());
            if(sender.isOnline()) {
                sender.sendNormal(player.getNick() + " has denied your request.");
            }
        }
    }
}
