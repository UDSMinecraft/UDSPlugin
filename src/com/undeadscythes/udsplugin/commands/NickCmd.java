package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class NickCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(args.length == 1) {
            if(noBadLang(args[0])) {
                if(player().getName().toLowerCase().contains(args[0].toLowerCase()) || hasPerm(Perm.NICK_OTHER)) {
                    player().setDisplayName(args[0]);
                    player().sendNormal("Your nickname has been changed to " + args[0] + ".");
                } else {
                    player().sendError("Your nickname must be a shortened version of your Minecraft name.");
                }
            }
        } else if(numArgsHelp(2) && hasPerm(Perm.NICK_OTHER) && (target = matchOnlinePlayer(args[0])) != null && noBadLang(args[1])) {
            target.setDisplayName(args[1]);
            player().sendNormal(target.getName() + "'s nickname has been changed to " + args[1] + ".");
            target.sendNormal("Your nickname has been changed to " + args[1] + ".");
        }
    }
}
