package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;

/**
 * Allows players to use /mod help.
 * 
 * @author UndeadScythes
 */
public class ModCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(args.length == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
