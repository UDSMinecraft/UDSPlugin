package com.undeadscythes.udsplugin.commands;

/**
 * Teleport a player to spawn.
 * @author UndeadScythes
 */
public class SpawnCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(notPinned() && notJailed()) {
            player().teleport(player().getLocation().getWorld().getSpawnLocation());
        }
    }
}
