package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set the world the players is in to night.
 * @author UndeadScythes
 */
public class NightCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        player.getWorld().setTime(14000);
        player.sendMessage(Color.MESSAGE + "Summoning the moon.");
    }

}
