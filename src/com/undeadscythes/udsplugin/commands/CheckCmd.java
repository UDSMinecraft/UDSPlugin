package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleport to a previously saved checkpoint.
 * @author UndeadScythes
 */
public class CheckCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(!player.quietTeleport(player.getCheckPoint())) {
            player.sendError("You do not currently have a checkpoint set.");
        }
    }
}
