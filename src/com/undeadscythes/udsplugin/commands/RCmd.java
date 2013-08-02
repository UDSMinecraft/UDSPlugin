package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Return a private message from another player.
 * @author UndeadScythes
 */
public class RCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getWhisperer()) != null && isOnline(target)) {
            final String message = player.getNick() + " > " + target.getNick() + ": " + StringUtils.join(args, " ");
            player.sendWhisper(message);
            if(target.isAfk()) {
                player.sendWhisper(target.getNick() + " is currently AFK.");
            }
            target.sendWhisper(message);
            player.setWhisperer(target);
            target.setWhisperer(player);
        }
    }
}
