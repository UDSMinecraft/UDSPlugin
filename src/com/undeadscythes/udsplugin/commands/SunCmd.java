package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Make it sunny in the players world.
 * @author UndeadScythes
 */
public class SunCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        player.getWorld().setStorm(false);
        player.getWorld().setThundering(false);
        player.sendMessage(Color.MESSAGE + "Clear skies on the way.");
    }
}
