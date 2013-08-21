package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Sets a warp that players can teleport to.
 * 
 * @author UndeadScythes
 */
public class SetWarpCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        PlayerRank rank;
        int price;
        final String message = "Warp point set.";
        if(args.length == 1) {
            if(noWarpExists(args[0]) && noBadLang(args[0])) {
                WarpUtils.addWarp(new Warp(args[0], player().getLocation(), PlayerRank.NEWBIE, 0));
                player().sendNormal(message);
            }
        } else if(args.length == 2) {
            if(noWarpExists(args[0]) && noBadLang(args[0]) && (rank = getRank(args[1])) != null) {
                WarpUtils.addWarp(new Warp(args[0], player().getLocation(), rank, 0));
                player().sendNormal(message);
            }
        } else if(numArgsHelp(3) && noWarpExists(args[0]) && noBadLang(args[0]) && (rank = getRank(args[1])) != null && (price = getInteger(args[2])) != -1) {
            WarpUtils.addWarp(new Warp(args[0], player().getLocation(), rank, price));
            player().sendNormal(message);
        }
    }
}
