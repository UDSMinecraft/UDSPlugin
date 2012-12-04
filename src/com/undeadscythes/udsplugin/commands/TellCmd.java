package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Hold a private conversation with another player.
 * @author UndeadScythes
 */
public class TellCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(2) && (target = getMatchingPlayer(args[0])) != null && isOnline(target) && notIgnored(target)) {
            final String message = player.getNick() + " > " + target.getNick() + ": " + StringUtils.join(args, " ", 1, args.length);
            player.sendMessage(Color.WHISPER + message);
            target.sendMessage(Color.WHISPER + message);
            player.setWhisperer(target);
            target.setWhisperer(player);
        }
    }
}
