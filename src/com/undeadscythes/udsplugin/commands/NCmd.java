package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class NCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final Request request = getRequest();
        if(request != null) {
            UDSPlugin.removeRequest(player().getName());
            final Member sender = PlayerUtils.getPlayer(request.getSender().getName());
            try {
                PlayerUtils.getOnlinePlayer(sender).sendNormal(player().getNick() + " has denied your request.");
            } catch (PlayerNotOnlineException ex) {}
        }
    }
}
