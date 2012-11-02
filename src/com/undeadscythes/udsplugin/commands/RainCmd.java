package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Make it rainy in the current world.
 * @author UndeadScythes
 */
public class RainCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(0,1)) {
            int duration;
            if(args.length == 0) {
                player.getWorld().setStorm(true);
                player.getWorld().setWeatherDuration(6000);
                player.sendMessage(Color.MESSAGE + "5 minute storm on the way.");
            } else if((duration = parseInt(args[0])) != -1) {
                player.getWorld().setStorm(true);
                player.getWorld().setWeatherDuration((int)(duration * Timer.MINUTE / Timer.TICKS));
                player.sendMessage(Color.MESSAGE.toString() + duration + " minute storm on the way.");
            }
        }
    }
}
