package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class SunCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        player.getWorld().setStorm(false);
        player.getWorld().setThundering(false);
        player.sendNormal("Clear skies on the way.");
    }
}
