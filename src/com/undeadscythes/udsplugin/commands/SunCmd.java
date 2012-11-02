package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Make it sunn yin the world.
 * @author UndeadScythes
 */
public class SunCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0)) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage(Color.MESSAGE + "Clear skies on the way.");
        }
    }
}
