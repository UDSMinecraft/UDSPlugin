package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set the spawn point of the current world.
 * @author UndeadScythes
 */
public class SetSpawnCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        new EditableWorld(player.getWorld()).setSpawnLocation(player.getLocation());
        player.sendMessage(Color.MESSAGE + "Spawn location moved.");
    }
}
