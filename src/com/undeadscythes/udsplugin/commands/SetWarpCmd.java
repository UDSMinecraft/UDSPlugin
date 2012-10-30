package com.undeadscythes.udsplugin.commands;

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
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsMoreLessInc(1, 2)) {
            Rank rank;
            int price;
            if(args.length == 1 && !UDSPlugin.getWarps().containsKey(args[0]) && censor(args[0])) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), Rank.DEFAULT, 0));
                player.sendMessage(Message.WARP_SET);
            } else if(args.length == 2 && !UDSPlugin.getWarps().containsKey(args[0]) && censor(args[0]) && (rank = matchesRank(args[1])) != null) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, 0));
                player.sendMessage(Message.WARP_SET);

            } else if(args.length == 3 && !UDSPlugin.getWarps().containsKey(args[0]) && censor(args[0]) && (rank = matchesRank(args[1])) != null && (price = parseInt(args[2])) != -1) {
                UDSPlugin.getWarps().put(args[0], new Warp(args[0], player.getLocation(), rank, price));
                player.sendMessage(Message.WARP_SET);
            }
        }
    }
}
