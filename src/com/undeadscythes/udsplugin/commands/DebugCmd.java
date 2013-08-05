package com.undeadscythes.udsplugin.commands;

import org.bukkit.entity.*;

/**
 * Various debugging commands.
 * @author UndeadScythes
 */
public class DebugCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(this.numArgsHelp(1)) {
            if(arg(0).equalsIgnoreCase("dragon")) {
                for(Entity entity : player().getWorld().getEntitiesByClass(EnderDragon.class)) {
                    ((EnderDragon)entity).setHealth(1);
                }
                player().sendNormal("Ender Dragons reduced to 1 HP.");
            }
        }
    }
}
