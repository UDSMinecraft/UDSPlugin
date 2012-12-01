package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set the spawn point of the current world.
 * @author UndeadScythes
 */
public class SetSpawnCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        new WEWorld(player.getWorld()).setSpawnLocation(player.getLocation());
        player.sendMessage(Color.MESSAGE + "Spawn location moved.");
    }
}
