package com.undeadscythes.udsplugin.commands;

/**
 * Allows players to use /chat help.
 * 
 * @author UndeadScythes
 */
public class ChatCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
