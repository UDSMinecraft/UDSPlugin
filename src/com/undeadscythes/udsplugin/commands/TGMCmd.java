package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class TGMCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        if(args.length == 0) {
            player.sendNormal("You now have creative mode " + (player.toggleGameMode() ? "en" : "dis") + "abled.");
        } else if(numArgsHelp(1)) {
            Member target = matchOnlinePlayer(args[0]);
            final boolean gameMode = target.toggleGameMode();
            if(!player.equals(target)) {
                player.sendNormal(target.getNick() + " now has creative mode " + (gameMode ? "en" : "dis") + "abled.");
            }
            target.sendNormal("You now have creative mode " + (gameMode ? "en" : "dis") + "abled.");
        }
    }
}
