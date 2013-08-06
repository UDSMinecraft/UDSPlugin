package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import org.bukkit.*;

/**
 * Send an action.
 * 
 * @author UndeadScythes
 */
public class MeCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        final String action = argsToMessage();
        if(minArgsHelp(1) && noBadLang(action)) {
            Bukkit.broadcastMessage(Color.TEXT + "*" + player().getNick() + " " + action);
        }
    }
}
