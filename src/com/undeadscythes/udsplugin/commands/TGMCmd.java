package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class TGMCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(args.length == 0) {
            player().sendNormal("You now have creative mode " + (player().toggleGameMode() ? "en" : "dis") + "abled.");
        } else if(numArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null) {
            final boolean gameMode = target.toggleGameMode();
            if(!player().equals(target)) {
                player().sendNormal(target.getNick() + " now has creative mode " + (gameMode ? "en" : "dis") + "abled.");
            }
            target.sendNormal("You now have creative mode " + (gameMode ? "en" : "dis") + "abled.");
        }
    }
}
