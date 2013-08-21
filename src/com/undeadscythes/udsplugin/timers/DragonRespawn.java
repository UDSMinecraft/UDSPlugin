package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.UDSPlugin;
import com.undeadscythes.udsplugin.Config;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

/**
 * Scheduled task that allows the Ender Dragon to respawn after a certain time.
 * 
 * @author UndeadScythes
 */
public class DragonRespawn implements Runnable {
    public DragonRespawn() {}

    @Override
    public final void run() {
        if(UDSPlugin.getData().getLastEnderDeath() > -1 && UDSPlugin.getData().getLastEnderDeath() + Config.DRAGON_RESPAWN < System.currentTimeMillis()) {
            for(World world : Bukkit.getWorlds()) {
                if(world.getEnvironment().equals(World.Environment.THE_END) && world.getEntitiesByClass(EnderDragon.class).isEmpty()) {
                    world.spawnEntity(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 20, 0), EntityType.ENDER_DRAGON);
                    UDSPlugin.getData().setLastEnderDeath(-1);
                    Block block;
                    for(int x = -100; x < 100; x++) {
                        for(int z = -100; z < 100; z++) {
                            block = world.getHighestBlockAt(x, z);
                            if(block.getType().equals(Material.BEDROCK)) {
                                spawnNewEnderCrystal(block.getLocation().add(0.5, 0, 0.5));
                                block.getRelative(BlockFace.UP).setType(Material.FIRE);
                            } else if(block.getType().equals(Material.FIRE) && block.getRelative(BlockFace.DOWN).getType().equals(Material.BEDROCK)) {
                                spawnNewEnderCrystal(block.getRelative(BlockFace.DOWN).getLocation().add(0.5, 0, 0.5));
                            }
                        }
                    }
                    UDSPlugin.sendBroadcast("The Ender Dragon has regained his strength and awaits brave warriors in The End.");
                }
            }
        }
    }

    private void spawnNewEnderCrystal(final Location location) {
        for(Entity entity : location.getWorld().getEntitiesByClass(EnderCrystal.class)) {
            if(entity.getLocation().distanceSquared(location) < 2) {
                return;
            }
        }
        location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
    }
}
