package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class CheckCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        try {
            player.quietTeleport(player.getCheckPoint());
        } catch(NoMetadataSetException ex) {
            player.sendError("You do not currently have a checkpoint set.");
        }
    }
}
