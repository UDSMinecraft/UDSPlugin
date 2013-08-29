package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class WhoIsCmd extends CommandHandler {
    @Override
    public  void playerExecute() throws NoPlayerFoundException {
        if(numArgsHelp(1)) {
            OfflineMember target = matchPlayer(args[0]);
            player.sendNormal(target.getNick() + " is " + target.getName() + ".");
        }
    }
}
