package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class MeCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final String action = argsToMessage();
        if(minArgsHelp(1) && noBadLang(action)) {
            Bukkit.broadcastMessage(Color.TEXT + "*" + player.getNick() + " " + action);
        }
    }
}
