package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Change a players nickname.
 * @author UndeadScythes
 */
public class NickCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(argsLength() == 1) {
            if(noBadLang(arg(0))) {
                if(player().getName().toLowerCase().contains(arg(0).toLowerCase()) || hasPerm(Perm.NICK_OTHER)) {
                    player().setDisplayName(arg(0));
                    player().sendNormal("Your nickname has been changed to " + arg(0) + ".");
                } else {
                    player().sendError("Your nickname must be a shortened version of your Minecraft name.");
                }
            }
        } else if(numArgsHelp(2) && hasPerm(Perm.NICK_OTHER) && (target = matchesPlayer(arg(0))) != null && noBadLang(arg(1))) {
            target.setDisplayName(arg(1));
            player().sendNormal(target.getName() + "'s nickname has been changed to " + arg(1) + ".");
            target.sendNormal("Your nickname has been changed to " + arg(1) + ".");
        }
    }
}
