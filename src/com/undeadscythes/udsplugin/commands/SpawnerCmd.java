package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

/**
 * Change the mob type of a spawner.
 * @author UndeadScythes
 */
public class SpawnerCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(1)) {
            final Block block = player.getTargetBlock(null, 5);
            if(block.getType() == Material.MOB_SPAWNER) {
                final EntityType mob = EntityType.fromName(args[0]);
                if(mob != null) {
                    ((CreatureSpawner)block.getState()).setSpawnedType(mob);
                    player.sendMessage(Color.MESSAGE + "Spawner set to " + mob.toString().toLowerCase().replace("_", " ") + ".");
                } else {
                    player.sendMessage(Color.ERROR + "Not a vliad mob type.");
                }
            } else {
                player.sendMessage(Color.ERROR + "You need to be looking at a mob spawner.");
            }
        }
    }
}
