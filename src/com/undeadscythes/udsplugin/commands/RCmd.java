package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Return a private message from another player.
 * @author UndeadScythes
 */
public class RCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getWhisperer()) != null && isOnline(target)) {
            final String message = player.getNick() + " > " + target.getNick() + ": " + StringUtils.join(args, " ");
            player.sendMessage(Color.WHISPER + message);
            if(target.isAfk()) {
                player.sendMessage(Color.WHISPER + target.getNick() + " is currently AFK.");
            }
            target.sendMessage(Color.WHISPER + message);
            player.setWhisperer(target);
            target.setWhisperer(player);
        }
    }
}
