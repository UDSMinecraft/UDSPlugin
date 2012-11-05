package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Give a player a spawn map.
 * @author UndeadScythes
 */
public class MapCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0) && canAfford(Config.MAP_COST)) {
            player.giveAndDrop(new ItemStack(Material.MAP, 1, (short)0, Config.MAP_DATA));
        }
    }
}
