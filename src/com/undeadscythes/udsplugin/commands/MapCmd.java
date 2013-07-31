package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Give a player a spawn map.
 * @author UndeadScythes
 */
public class MapCmd extends CommandValidator {
    @Override
    @SuppressWarnings("deprecation")
    public final void playerExecute() {
        if(canAfford(UDSPlugin.getConfigInt(ConfigRef.MAP_COST))) {
            player.giveAndDrop(new ItemStack(Material.MAP, 1, (short)0, UDSPlugin.getConfigByte(ConfigRef.MAP_COST)));
        }
    }
}
