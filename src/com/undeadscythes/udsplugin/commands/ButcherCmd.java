package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class ButcherCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsLessEq(1)) {
            boolean all = false;
            if(args.length == 1 && (args[0].equals("a") || args[0].equals("all"))) {
                all = true;
            }
            int count = 0;
            for(LivingEntity entity : player.getWorld().getLivingEntities()) {
                if(!(entity instanceof Player) && entity.getLocation().distanceSquared(player.getLocation()) < Config.BUTCHER_RANGE) {
                    if((entity instanceof Wolf) && !all) {
                        if(((Wolf)entity).isAngry()) {
                            entity.remove();
                            count++;
                        }
                    } else if((entity instanceof Ageable || entity instanceof WaterMob) && !all) {
                        continue;
                    } else if((entity instanceof Golem) && !all) {
                        if(((Golem)entity).getTarget() instanceof Player) {
                            entity.remove();
                            count++;
                        }
                    } else {
                        entity.remove();
                        count++;
                    }
                }
            }
            player.sendMessage(Color.MESSAGE + "Butchered " + count + " mobs.");
        }
    }
}
