package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class NightCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        player.getWorld().setTime(14000);
        player.sendNormal("Summoning the moon.");
    }

}
