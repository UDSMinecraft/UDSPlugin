package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Start a storm in the world.
 * @author UndeadScythes
 */
public class StormCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        int duration;
        if(args.length == 0) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
            player.getWorld().setWeatherDuration(6000);
            player.getWorld().setThunderDuration(6000);
            player.sendNormal("5 minutes thunder storm on the way.");
        } else if(numArgsHelp(1) && (duration = parseInt(args[0])) != -1) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
            player.getWorld().setWeatherDuration((int)(duration * TimeUtils.MINUTE / TimeUtils.TICKS));
            player.getWorld().setThunderDuration((int)(duration * TimeUtils.MINUTE / TimeUtils.TICKS));
            player.sendNormal(duration + " minutes thunder storm on the way.");
        }
    }
}
