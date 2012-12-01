package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Hold a private conversation with another player.
 * @author UndeadScythes
 */
public class TellCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(minArgsHelp(2) && (target = getMatchingPlayer(args[0])) != null && isOnline(target) && notIgnored(target)) {
            String message = player.getDisplayName() + " > " + target.getDisplayName() + ": " + StringUtils.join(args, " ", 1, args.length);
            player.sendMessage(Color.WHISPER + message);
            target.sendMessage(Color.WHISPER + message);
            player.setWhisperer(target);
            target.setWhisperer(player);
        }
    }
}
