package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.*;

/**
 * Toggles the admin chat channel.
 * 
 * @author UndeadScythes
 */
public class CCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0 && getClan() != null) {
            if(player.toggleChannel(ChatChannel.CLAN)) {
                player.sendNormal("You are now talking in clan chat.");
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(ChatChannel.CLAN, argsToMessage());
        }
    }
}
