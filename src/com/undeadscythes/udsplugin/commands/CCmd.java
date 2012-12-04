package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.eventhandlers.AsyncPlayerChat.Channel;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class CCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
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
