package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;

/**
 * Broadcast a server wide message.
 * 
 * @author UndeadScythes
 */
public class BroadcastCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(minArgsHelp(1)) {
            UDSPlugin.sendBroadcast(argsToMessage());
        }
    }

}
