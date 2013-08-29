package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class BackCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        try {
            player.quietTeleport(player.getBack());
        } catch(NoMetadataSetException ex) {
            player.sendError("You can't teleport back at this time.");
        }
    }
}
