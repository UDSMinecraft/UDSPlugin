package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Change a players nickname.
 * @author UndeadScythes
 */
public class NickCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        SaveablePlayer target;
        if(args.length == 1) {
            if(noCensor(args[0])) {
                if(player.getName().toLowerCase().contains(args[0].toLowerCase()) || hasPerm(Perm.NICK_OTHER)) {
                    player.setDisplayName(args[0]);
                    player.sendMessage(Color.MESSAGE + "Your nickname has been changed to " + args[0] + ".");
                } else {
                    player.sendMessage(Color.ERROR + "Your nickname must be a shortened version of your Minecraft name.");
                }
            }
        } else if(numArgsHelp(2) && hasPerm(Perm.NICK_OTHER) && (target = getMatchingPlayer(args[0])) != null && noCensor(args[1])) {
            target.setDisplayName(args[1]);
            player.sendMessage(Color.MESSAGE + player.getName() + "'s nickname has been changed to " + args[1] + ".");
            target.sendMessage(Color.MESSAGE + "Your nickname has been changed to " + args[1] + ".");
        }
    }
}
