package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Start a storm in the world.
 * 
 * @author UndeadScythes
 */
public class StormCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        int duration;
        if(args.length == 0) {
            player().getWorld().setStorm(true);
            player().getWorld().setThundering(true);
            player().getWorld().setWeatherDuration(6000);
            player().getWorld().setThunderDuration(6000);
            player().sendNormal("5 minutes thunder storm on the way.");
        } else if(numArgsHelp(1) && (duration = getInteger(args[0])) != -1) {
            player().getWorld().setStorm(true);
            player().getWorld().setThundering(true);
            player().getWorld().setWeatherDuration((int)(duration * TimeUtils.MINUTE * TimeUtils.TICK));
            player().getWorld().setThunderDuration((int)(duration * TimeUtils.MINUTE * TimeUtils.TICK));
            player().sendNormal(duration + " minutes thunder storm on the way.");
        }
    }
}
