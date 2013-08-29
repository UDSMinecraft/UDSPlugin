package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.requests.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class NCmd extends CommandHandler {
    @Override
    public void playerExecute() throws NoPlayerFoundException {
        final Request request = getRequest();
        if(request != null) {
            UDSPlugin.removeRequest(player.getName());
            final OfflineMember sender = MemberUtils.getMember(request.getSender().getName());
            try {
                MemberUtils.getOnlineMember(sender).sendNormal(player.getNick() + " has denied your request.");
            } catch(PlayerNotOnlineException ex) {}
        }
    }
}
