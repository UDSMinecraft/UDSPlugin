package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsmeta.*;

/**
 * @author UndeadScythes
 */
public class BackCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        try {
            player().quietTeleport(player().getBack());
        } catch (NoMetadataSetException ex) {
            player().sendError("You can't teleport back at this time.");
        }
    }
}
