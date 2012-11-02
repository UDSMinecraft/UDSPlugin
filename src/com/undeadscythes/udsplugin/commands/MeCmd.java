package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Send an action.
 * @author UndeadScythes
 */
public class MeCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        String action = StringUtils.join(args, " ");
        if(argsMoreEq(1) && censor(action)) {
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getDisplayName() + " " + action);
        }
    }
}
