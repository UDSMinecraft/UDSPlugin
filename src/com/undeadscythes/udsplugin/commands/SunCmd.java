package com.undeadscythes.udsplugin.commands;

/**
 * Make it sunny in the players world.
 * 
 * @author UndeadScythes
 */
public class SunCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        player().getWorld().setStorm(false);
        player().getWorld().setThundering(false);
        player().sendNormal("Clear skies on the way.");
    }
}
