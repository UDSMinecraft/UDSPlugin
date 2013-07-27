package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class CCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(args.length == 0 && isInClan()) {
            if(player.toggleChannel(ChatChannel.CLAN)) {
                player.sendNormal("You are now talking in clan chat.");
            } else {
                player.sendMessage(Message.PUBLIC_CHAT);
            }
        } else {
            player.chat(ChatChannel.CLAN, StringUtils.join(args, " "));
        }
    }
}
