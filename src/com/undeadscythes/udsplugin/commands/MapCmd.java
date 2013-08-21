package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.material.*;

/**
 * Give a player a spawn map.
 * 
 * @author UndeadScythes
 */
public class MapCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(canAfford(Config.MAP_COST)) {
            final MaterialData map = new MaterialData(Material.MAP, Config.MAP_DATA);
            player().giveAndDrop(map.toItemStack(1));
        }
    }
}
