package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Deny a request.
 * @author UndeadScythes
 */
public class NCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        Request request;
        if(argsEq(0) && (request = hasRequest()) != null) {
            UDSPlugin.getRequests().remove(player.getName());
            SaveablePlayer sender = UDSPlugin.getPlayers().get(request.getSender().getName());
            if(sender.isOnline()) {
                sender.sendMessage(Color.MESSAGE + player.getDisplayName() + " has denied your request.");
            }
        }
    }
}
