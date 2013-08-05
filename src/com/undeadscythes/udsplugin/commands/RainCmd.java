package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.utilities.*;

/**
 * Make it rainy in the current world.
 * @author UndeadScythes
 */
public class RainCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        int duration;
        if(argsLength() == 0) {
            player().getWorld().setStorm(true);
            player().getWorld().setWeatherDuration(6000);
            player().sendNormal("5 minute storm on the way.");
        } else if(numArgsHelp(1) && (duration = isInteger(arg(0))) != -1) {
            player().getWorld().setStorm(true);
            player().getWorld().setWeatherDuration((int)(duration * TimeUtils.MINUTE / TimeUtils.TICKS));
            player().sendNormal(duration + " minute storm on the way.");
        }
    }
}
