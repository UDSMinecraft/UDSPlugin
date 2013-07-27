package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.timers.Timer;
import com.undeadscythes.udsplugin.*;

/**
 * Make it rainy in the current world.
 * @author UndeadScythes
 */
public class RainCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        int duration;
        if(args.length == 0) {
            player.getWorld().setStorm(true);
            player.getWorld().setWeatherDuration(6000);
            player.sendNormal("5 minute storm on the way.");
        } else if(numArgsHelp(1) && (duration = parseInt(args[0])) != -1) {
            player.getWorld().setStorm(true);
            player.getWorld().setWeatherDuration((int)(duration * Timer.MINUTE / Timer.TICKS));
            player.sendNormal(duration + " minute storm on the way.");
        }
    }
}
