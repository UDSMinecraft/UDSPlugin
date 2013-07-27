package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Hold a private conversation with another player.
 * @author UndeadScythes
 */
public class TellCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(2) && (target = getMatchingPlayer(args[0])) != null && isOnline(target) && notIgnored(target)) {
            final String message = player.getNick() + " > " + target.getNick() + ": " + StringUtils.join(args, " ", 1, args.length);
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
