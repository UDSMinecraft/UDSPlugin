package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport a player to the spawn of the current world.
 *
 * @author UndeadScythes
 */
public class SpawnCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(notPinned() && notJailed()) {
            player().teleport(UDSPlugin.getWorldSpawn(player().getWorld()));
        }
    }
}
