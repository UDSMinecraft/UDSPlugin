package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggles the private chat channel.
 * 
 * @author UndeadScythes
 */
public class PCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(getChatRoom() != null) {
            if(argsLength() == 0) {
                if(player().toggleChannel(ChatChannel.PRIVATE)) {
                    player().sendNormal("You are now talking in .");
                } else {
                    player().sendMessage(Message.PUBLIC_CHAT);
                }
            } else {
                player().chat(ChatChannel.PRIVATE, argsToMessage());
            }
        }
    }
}
