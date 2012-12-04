package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport to a previously saved checkpoint.
 * @author UndeadScythes
 */
public class CheckCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(!player.quietTeleport(player.getCheckPoint())) {
            player.sendMessage(Color.ERROR + "You do not currently have a checkpoint set.");
        }
    }
}
