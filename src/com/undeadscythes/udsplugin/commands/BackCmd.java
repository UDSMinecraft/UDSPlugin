package com.undeadscythes.udsplugin.commands;

/**
 * Teleports a player to their last recorded position.
 * 
 * @author UndeadScythes
 */
public class BackCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(!player().quietTeleport(player().getBack())) {
            player().sendError("You can't teleport back at this time.");
        }
    }

}
