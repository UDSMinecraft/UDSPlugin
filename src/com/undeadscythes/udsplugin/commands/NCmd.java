package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Deny a request.
 * @author UndeadScythes
 */
public class NCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        Request request;
        if((request = getRequest()) != null) {
            UDSPlugin.getRequests().remove(player.getName());
            SaveablePlayer sender = UDSPlugin.getPlayers().get(request.getSender().getName());
            if(sender.isOnline()) {
                sender.sendMessage(Color.MESSAGE + player.getNick() + " has denied your request.");
            }
        }
    }
}
