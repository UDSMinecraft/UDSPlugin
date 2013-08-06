package com.undeadscythes.udsplugin.commands;

/**
 * Switches the current world to sunset.
 * 
 * @author UndeadScythes
 */
public class DayCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        player().getWorld().setTime(0);
        player().sendNormal("Summoning the sun.");
    }
}
