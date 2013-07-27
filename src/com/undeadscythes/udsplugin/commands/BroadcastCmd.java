package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Broadcast a server wide message.
 * @author UndeadScythes
 */
public class BroadcastCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(minArgsHelp(1)) {
            UDSPlugin.sendBroadcast(StringUtils.join(args, " "));
        }
    }

}
