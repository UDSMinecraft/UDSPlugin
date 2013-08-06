package com.undeadscythes.udsplugin.commands;

/**
 * Allows players to use /admin help.
 * 
 * @author UndeadScythes
 */
public class AdminCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
