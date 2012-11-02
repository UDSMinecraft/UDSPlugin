package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Delete a warp point.
 * @author UndeadScythes
 */
public class DelWarpCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        Warp warp;
        if(argsEq(1) && (warp = matchesWarp(args[0])) != null) {
            UDSPlugin.getWarps().remove(warp.getName());
            player.sendMessage(Color.MESSAGE + "Warp removed.");
        }
    }

}
