package com.undeadscythes.udsplugin.commands;

import org.bukkit.*;

/**
 * Gives a description of the players surroundings.
 *
 * @author UndeadScythes
 */
public class WhereCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        final Location location = player().getLocation();
        final Location spawn = player().getWorld().getSpawnLocation();
        String message = "You are " + (int)location.distance(spawn) + " blocks from spawn in world " + location.getWorld().getName() + ",";
        if(location.getBlockY() < 64) {
            message = message.concat(" " + (64 - location.getBlockY()) + " blocks below sea level");
        } else if(location.getBlockY() > 64) {
            message = message.concat(" " + (location.getBlockY() - 64) + " blocks above sea level");
        } else {
            message = message.concat(" at sea level");
        }
        message = message.concat(" in a " + location.getBlock().getBiome().toString().toLowerCase().replace("_", " ") + " biome.");
        player().sendNormal(message);
        player().sendNormal("Your coordinates are x:" + location.getBlockX() + " y:" + location.getBlockY() + " z:" + location.getBlockZ() + ".");
    }
}
