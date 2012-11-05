package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.eventhandlers.AsyncPlayerChat.Channel;
import org.apache.commons.lang.*;

/**
 * Toggles the private chat channel.
 * @author UndeadScythes
 */
public class PCmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(inChatRoom()) {
            if(args.length == 0) {
                if(player.toggleChannel(Channel.PRIVATE)) {
                    player.sendMessage(Color.MESSAGE + "You are now talking in .");
                } else {
                    player.sendMessage(Message.PUBLIC_CHAT);
                }
            } else {
                player.chat(Channel.PRIVATE, StringUtils.join(args, " "));
            }
        }
    }
}
