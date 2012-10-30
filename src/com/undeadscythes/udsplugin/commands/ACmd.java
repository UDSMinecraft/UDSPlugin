package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class ACmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(args.length == 0) {
            if(player.toggleChannel(Channel.ADMIN)) {
                player.sendMessage(Message.ADMIN_CHAT);
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(Channel.ADMIN, StringUtils.join(args, " "));
        }
    }
}
