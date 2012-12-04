package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.eventhandlers.AsyncPlayerChat.Channel;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class ACmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            if(player.toggleChannel(Channel.ADMIN)) {
                player.sendMessage(Color.MESSAGE + "You are now talking in admin chat.");
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(Channel.ADMIN, StringUtils.join(args, " "));
        }
    }
}
