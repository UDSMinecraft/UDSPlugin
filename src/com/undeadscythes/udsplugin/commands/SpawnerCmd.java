package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandWrapper;
import com.undeadscythes.udsplugin.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

/**
 * Change the mob type of a spawner.
 * @author UndeadScythes
 */
public class SpawnerCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            final Block block = player.getTargetBlock(null, 5);
            if(block.getType() == Material.MOB_SPAWNER) {
                final EntityType mob = EntityType.fromName(args[0]);
                if(mob == null) {
                    player.sendError("Not a valid mob type.");
                } else {
                    ((CreatureSpawner)block.getState()).setSpawnedType(mob);
                    player.sendNormal("Spawner set to " + mob.toString().toLowerCase().replace("_", " ") + ".");
                }
            } else {
                player.sendError("You need to be looking at a mob spawner.");
            }
        }
    }
}
