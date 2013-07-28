package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Sets a warp that players can teleport to.
 * @author UndeadScythes
 */
public class SetWarpCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        PlayerRank rank;
        int price;
        final String message = "Warp point set.";
        if(args.length == 1) {
            if(notWarp(args[0]) && noCensor(args[0])) {
                WarpUtils.addWarp(new Warp(args[0], player.getLocation(), PlayerRank.NEWBIE, 0));
                player.sendNormal(message);
            }
        } else if(args.length == 2) {
            if(notWarp(args[0]) && noCensor(args[0]) && (rank = getRank(args[1])) != null) {
                WarpUtils.addWarp(new Warp(args[0], player.getLocation(), rank, 0));
                player.sendNormal(message);
            }
        } else if(numArgsHelp(3) && notWarp(args[0]) && noCensor(args[0]) && (rank = getRank(args[1])) != null && (price = parseInt(args[2])) != -1) {
            WarpUtils.addWarp(new Warp(args[0], player.getLocation(), rank, price));
            player.sendNormal(message);
        }
    }
}
