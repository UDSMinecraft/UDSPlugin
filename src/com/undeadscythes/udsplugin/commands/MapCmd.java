package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Give a player a spawn map.
 * @author UndeadScythes
 */
public class MapCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(canAfford(Config.mapCost)) {
            player.giveAndDrop(new ItemStack(Material.MAP, 1, (short)0, Config.mapData));
        }
    }
}
