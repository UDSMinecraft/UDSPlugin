package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class DebugCmd extends CommandHandler {
    @Override
    public  void playerExecute() {
        if(this.numArgsHelp(1)) {
            if(args[0].equalsIgnoreCase("dragon")) {
                for(Entity entity : player.getWorld().getEntitiesByClass(EnderDragon.class)) {
                    ((EnderDragon)entity).setHealth(1);
                }
                player.sendNormal("Ender Dragons reduced to 1 HP.");
            }
        }
    }
}
