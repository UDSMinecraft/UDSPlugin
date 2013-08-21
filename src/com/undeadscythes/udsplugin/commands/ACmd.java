package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * Toggles the {@link ChatChannel#ADMIN} chat channel and allows players to
 * talk in this channel.
 * 
 * @author UndeadScythes
 */
public class ACmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(args.length == 0) {
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
