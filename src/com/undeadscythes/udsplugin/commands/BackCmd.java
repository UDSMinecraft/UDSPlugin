package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleports a player to their last recorded position.
 * @author UndeadScythes
 */
public class BackCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(!player.quietTeleport(player.getBack())) {
            player.sendMessage(Color.ERROR + "You can't teleport back at this time.");
        }
    }

}
