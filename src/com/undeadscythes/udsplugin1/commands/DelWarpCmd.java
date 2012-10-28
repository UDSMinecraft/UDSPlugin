package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import com.undeadscythes.udsplugin1.UDSPlugin;
import com.undeadscythes.udsplugin1.Warp;

/**
 * Delete a warp point.
 * @author UndeadScythes
 */
public class DelWarpCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        Warp warp;
        if(hasPerm("delwarp") && argsLength(1) && (warp = matchesWarp(args[0])) != null) {
            UDSPlugin.getWarps().remove(warp.getName());
            player.sendMessage(Message.WARP_REMOVED.toString());
        }
    }

}
