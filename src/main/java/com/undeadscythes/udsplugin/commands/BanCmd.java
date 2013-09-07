package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class BanCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        OfflineMember target;
        if(minArgsHelp(1) && (target = matchOtherPlayer(args[0])) != null) {
            String message = "You have been banned for breaking the rules.";
            if(args.length > 1) {
                message = argsToMessage(1);
            }
            try {
                MemberUtils.getOnlineMember(target).getWorld().strikeLightningEffect(MemberUtils.getOnlineMember(target).getLocation());
                MemberUtils.getOnlineMember(target).kickPlayer(message);
            } catch(PlayerNotOnlineException ex) {}
            target.setBanned(true);
            UDSPlugin.sendBroadcast(target.getNick() + " has been banned for breaking the rules.");
        }
    }
}
