package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Deny a request.
 * @author UndeadScythes
 */
public class NCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        Request request;
        if((request = getRequest()) != null) {
            UDSPlugin.getRequests().remove(player.getName());
            final SaveablePlayer sender = PlayerUtils.getPlayer(request.getSender().getName());
            if(sender.isOnline()) {
                sender.sendMessage(Color.MESSAGE + player.getNick() + " has denied your request.");
            }
        }
    }
}
