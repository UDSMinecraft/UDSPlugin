package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport a player to spawn.
 * @author UndeadScythes
 */
public class SpawnCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(notPinned() && notJailed()) {
            player.teleport(Config.mainWorld.getSpawnLocation());
        }
    }
}
