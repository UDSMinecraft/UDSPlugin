package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Toggles the private chat channel.
 * @author UndeadScythes
 */
public class PCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(inChatRoom()) {
            if(args.length == 0) {
                if(player.toggleChannel(ChatChannel.PRIVATE)) {
                    player.sendMessage(Color.MESSAGE + "You are now talking in .");
                } else {
                    player.sendMessage(Message.PUBLIC_CHAT);
                }
            } else {
                player.chat(ChatChannel.PRIVATE, StringUtils.join(args, " "));
            }
        }
    }
}
