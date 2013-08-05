package com.undeadscythes.udsplugin.commands;

/**
 * Allows players to use /mod help.
 * @author UndeadScythes
 */
public class ModCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(argsLength() == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
