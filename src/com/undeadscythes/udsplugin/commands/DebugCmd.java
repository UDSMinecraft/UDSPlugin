package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * Various debugging commands.
 * @author UndeadScythes
 */
public class DebugCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        if(this.numArgsHelp(1)) {
            if(args[0].equalsIgnoreCase("dragon")) {
                for(Entity entity : player.getWorld().getEntitiesByClass(EnderDragon.class)) {
                    ((EnderDragon)entity).setHealth(1);
                }
                player.sendMessage(Color.MESSAGE + "Ender Dragons reduced to 1 HP.");
            }
        }
    }
}
