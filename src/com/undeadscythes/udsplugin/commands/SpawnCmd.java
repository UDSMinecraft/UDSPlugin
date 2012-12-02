package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport a player to spawn.
 * @author UndeadScythes
 */
public class SpawnCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(notPinned() && notJailed()) {
            player.teleport(Config.mainWorld.getSpawnLocation());
        }
    }
}
