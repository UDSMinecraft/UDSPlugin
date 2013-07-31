package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Give a description of the players surroundings.
 * @author UndeadScythes
 */
public class WhereCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        final Location playerLocation = player.getLocation();
        final Location spawnLocation = player.getWorld().getSpawnLocation();
        final int distance = (int)playerLocation.distance(spawnLocation);
        String message = "You are " + distance + " blocks from spawn in world " + playerLocation.getWorld().getName() + ",";
        if(player.getLocation().getBlockY() < 64) {
            message = message.concat(" " + (64 - player.getLocation().getBlockY()) + " blocks below sea level");
        } else if(player.getLocation().getBlockY() > 64) {
            message = message.concat(" " + (player.getLocation().getBlockY() - 64) + " blocks above sea level");
        } else {
            message = message.concat(" at sea level");
        }
        message = message.concat(" in a " + player.getLocation().getBlock().getBiome().toString().toLowerCase().replace("_", " ") + " biome");
        player.sendNormal(message);
    }
}
