package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import com.undeadscythes.udsplugin.*;

/**
 * Sets a warp that players can teleport to.
 * @author UndeadScythes
 */
public class SetWarpCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        PlayerRank rank;
        int price;
        String message = Color.MESSAGE + "Warp point set.";
        if(args.length == 1) {
            if(notWarp(args[0]) && noCensor(args[0])) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), PlayerRank.DEFAULT, 0));
                player.sendMessage(message);
            }
        } else if(args.length == 2) {
            if(notWarp(args[0]) && noCensor(args[0]) && (rank = getRank(args[1])) != null) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, 0));
                player.sendMessage(message);
            }
        } else if(numArgsHelp(3) && notWarp(args[0]) && noCensor(args[0]) && (rank = getRank(args[1])) != null && (price = parseInt(args[2])) != -1) {
            UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, price));
            player.sendMessage(message);
        }
    }
}
