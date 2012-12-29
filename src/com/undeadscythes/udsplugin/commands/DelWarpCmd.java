package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Delete a warp point.
 * @author UndeadScythes
 */
public class DelWarpCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        Warp warp;
        if(numArgsHelp(1) && (warp = getWarp(args[0])) != null) {
            UDSPlugin.getWarps().remove(warp.getName());
            player.sendMessage(Color.MESSAGE + "Warp removed.");
        }
    }

}
