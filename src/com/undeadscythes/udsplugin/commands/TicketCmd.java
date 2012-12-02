package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class TicketCmd extends AbstractPlayerCommand {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        player.sendMessage(Color.MESSAGE + "Please post issues to:");
        player.sendMessage(Color.LINK + "https://github.com/UndeadScythes/UDSPlugin1/issues");
    }
}
