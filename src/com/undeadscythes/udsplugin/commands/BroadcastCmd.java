package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Broadcast a server wide message.
 * @author UndeadScythes
 */
public class BroadcastCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsMoreEq(1)) {
            Bukkit.broadcastMessage(Color.BROADCAST + StringUtils.join(args, " "));
        }
    }

}
