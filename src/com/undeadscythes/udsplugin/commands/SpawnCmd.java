package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport a player to spawn.
 * @author UndeadScythes
 */
public class SpawnCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(notPinned() && notJailed()) {
            player.teleport(UDSPlugin.getData().getSpawn());
        }
    }
}
