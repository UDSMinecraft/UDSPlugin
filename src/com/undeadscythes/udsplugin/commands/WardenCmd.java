package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Allows players to use /warden help.
 * @author UndeadScythes
 */
public class WardenCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
