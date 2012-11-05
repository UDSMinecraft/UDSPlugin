package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport a player to spawn.
 * @author UndeadScythes
 */
public class SpawnCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0) && notPinned() && notJailed()) {
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}
