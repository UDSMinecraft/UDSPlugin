package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleports a player to their last recorded position.
 * @author UndeadScythes
 */
public class BackCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(!player.quietTeleport(player.getBack())) {
            player.sendError("You can't teleport back at this time.");
        }
    }

}
