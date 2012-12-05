package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Set the spawn point of the current world.
 * @author UndeadScythes
 */
public class SetSpawnCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        final Location location = player.getLocation();
        player.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        UDSPlugin.getData().setSpawn(location);
        player.sendMessage(Color.MESSAGE + "Spawn location moved.");
    }
}
