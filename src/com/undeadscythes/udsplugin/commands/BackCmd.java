package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleports a player to their last recorded position.
 * @author UndeadScythes
 */
public class BackCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(!player.quietTeleport(player.getBack())) {
            player.sendMessage(Color.ERROR + "You can't teleport back at this time.");
        }
    }

}
