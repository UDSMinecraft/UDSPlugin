package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Change a players nickname.
 * @author UndeadScythes
 */
public class NickCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(1, 2)) {
            SaveablePlayer target;
            if(args.length == 1 && censor(args[0])) {
                if(player.getName().toLowerCase().contains(args[0].toLowerCase())) {
                    player.setDisplayName(args[0]);
                    player.sendMessage(Color.MESSAGE + "Your nickname has been changed to " + args[0] + ".");
                }
            } else if(hasPerm(Perm.NICK_OTHER) && (target = matchesPlayer(args[0])) != null && censor(args[1])) {
                target.setDisplayName(args[1]);
                player.sendMessage(Color.MESSAGE + player.getName() + "'s nickname has been changed to " + args[1] + ".");
                target.sendMessage(Color.MESSAGE + "Your nickname has been changed to " + args[1] + ".");
            }
        }
    }
}
