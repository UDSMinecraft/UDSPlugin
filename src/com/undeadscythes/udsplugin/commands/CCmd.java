package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class CCmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(args.length == 0 && isInClan()) {
            if(player.toggleChannel(Channel.CLAN)) {
                player.sendMessage(Color.MESSAGE + "You are now talking in clan chat.");
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(Channel.CLAN, StringUtils.join(args, " "));
        }
    }
}
