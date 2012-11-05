package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
import com.undeadscythes.udsplugin.*;

/**
 * Sets a warp that players can teleport to.
 * @author UndeadScythes
 */
public class SetWarpCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(1, 3)) {
            Rank rank;
            int price;
            String message = Color.MESSAGE + "Warp point set.";
            if(args.length == 1 && noWarp(args[0]) && noCensor(args[0])) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), Rank.DEFAULT, 0));
                player.sendMessage(message);
            } else if(args.length == 2 && noWarp(args[0]) && noCensor(args[0]) && (rank = matchesRank(args[1])) != null) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, 0));
                player.sendMessage(message);

            } else if(args.length == 3 && noWarp(args[0]) && noCensor(args[0]) && (rank = matchesRank(args[1])) != null && (price = parseInt(args[2])) != -1) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, price));
                player.sendMessage(message);
            }
        }
    }
}
