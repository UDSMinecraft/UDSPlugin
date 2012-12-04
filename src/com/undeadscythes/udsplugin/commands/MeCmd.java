package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * Send an action.
 * @author UndeadScythes
 */
public class MeCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        String action = StringUtils.join(args, " ");
        if(minArgsHelp(1) && noCensor(action)) {
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " " + action);
        }
    }
}
