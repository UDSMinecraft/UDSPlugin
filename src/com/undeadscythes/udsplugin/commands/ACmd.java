package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class ACmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        if(args.length == 0) {
            if(player.toggleChannel(ChatChannel.ADMIN)) {
                player.sendNormal("You are now talking in admin chat.");
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(ChatChannel.ADMIN, StringUtils.join(args, " "));
        }
    }
}
