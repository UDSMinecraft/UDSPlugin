package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Channel;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.apache.commons.lang3.StringUtils;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class CCmd extends PlayerCommandExecutor {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(hasPerm("c")) {
            if(args.length == 0) {
                if(player.toggleChannel(Channel.CLAN)) {
                    player.sendMessage(Message.CLAN_CHAT + "");
                } else {
                    player.sendMessage(Message.PUBLIC_CHAT + "");
                }
            } else {
                player.chat(Channel.CLAN, StringUtils.join(args, " "));
            }
        }
    }
}
