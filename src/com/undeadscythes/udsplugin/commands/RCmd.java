package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Return a private message from another player.
 * @author UndeadScythes
 */
public class RCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getWhisperer()) != null && isOnline(target)) {
            String message = player.getDisplayName() + " > " + target.getDisplayName() + ": " + StringUtils.join(args, " ");
            player.sendMessage(Color.WHISPER + message);
            target.sendMessage(Color.WHISPER + message);
            player.setWhisperer(target);
            target.setWhisperer(player);
        }
    }
}
