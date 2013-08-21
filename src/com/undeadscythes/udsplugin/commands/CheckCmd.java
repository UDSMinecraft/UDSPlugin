package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsmeta.*;

/**
 * @author UndeadScythes
 */
public class CheckCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        try {
            player().quietTeleport(player().getCheckPoint());
        } catch (NoMetadataSetException ex) {
            player().sendError("You do not currently have a checkpoint set.");
        }
    }
}
