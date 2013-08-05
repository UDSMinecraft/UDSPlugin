package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class ACmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 0) {
            if(player().toggleChannel(ChatChannel.ADMIN)) {
                player().sendNormal("You are now talking in admin chat.");
            } else {
                player().sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player().chat(ChatChannel.ADMIN, argsToMessage());
        }
    }
}
