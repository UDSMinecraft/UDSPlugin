package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;

/**
 * Description.
 * @author UndeadScythes
 */
public class WhoIsCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer target;
        if(hasPerm("whois") && argsLength(1) && (target = matchesPlayer(args[0])) != null) {
            player.sendMessage(Color.MESSAGE + target.getDisplayName() + " is " + target.getName() + ".");
        }
    }

}
