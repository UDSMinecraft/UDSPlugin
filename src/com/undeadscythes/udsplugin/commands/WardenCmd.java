package com.undeadscythes.udsplugin.commands;

/**
 * Allows players to use /warden help.
 * 
 * @author UndeadScythes
 */
public class WardenCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
