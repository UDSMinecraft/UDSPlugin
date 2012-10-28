package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

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
        if(hasPerm("broadcast")) {
            Bukkit.broadcastMessage(Color.BROADCAST + StringUtils.join(args, " "));
        }
    }

}
