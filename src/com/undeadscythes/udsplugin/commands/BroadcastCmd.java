package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.Bukkit;

/**
 * Broadcast a server wide message.
 * @author UndeadScythes
 */
public class BroadcastCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(minArgsHelp(1)) {
            Bukkit.broadcastMessage(Color.BROADCAST + StringUtils.join(args, " "));
        }
    }

}
