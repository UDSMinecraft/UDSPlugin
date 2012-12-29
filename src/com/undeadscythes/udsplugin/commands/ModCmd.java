package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Allows players to use /mod help.
 * @author UndeadScythes
 */
public class ModCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            sendHelp(1);
        } else {
            subCmdHelp();
        }
    }
}
