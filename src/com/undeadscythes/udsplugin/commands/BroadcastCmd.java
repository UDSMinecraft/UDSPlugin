package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Broadcast a server wide message.
 * @author UndeadScythes
 */
public class BroadcastCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(minArgsHelp(1)) {
            Bukkit.broadcastMessage(Color.BROADCAST + StringUtils.join(args, " "));
        }
    }

}
