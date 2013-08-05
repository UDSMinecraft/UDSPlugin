package com.undeadscythes.udsplugin.commands;

/**
 * Allows players to use /admin help.
 * @author UndeadScythes
 */
public class AdminCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(argsLength() == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
