package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Sets a warp that players can teleport to.
 * @author UndeadScythes
 */
public class SetWarpCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        PlayerRank rank;
        int price;
        final String message = "Warp point set.";
        if(argsLength() == 1) {
            if(notWarp(arg(0)) && noBadLang(arg(0))) {
                WarpUtils.addWarp(new Warp(arg(0), player().getLocation(), PlayerRank.NEWBIE, 0));
                player().sendNormal(message);
            }
        } else if(argsLength() == 2) {
            if(notWarp(arg(0)) && noBadLang(arg(0)) && (rank = rankExists(arg(1))) != null) {
                WarpUtils.addWarp(new Warp(arg(0), player().getLocation(), rank, 0));
                player().sendNormal(message);
            }
        } else if(numArgsHelp(3) && notWarp(arg(0)) && noBadLang(arg(0)) && (rank = rankExists(arg(1))) != null && (price = isInteger(arg(2))) != -1) {
            WarpUtils.addWarp(new Warp(arg(0), player().getLocation(), rank, price));
            player().sendNormal(message);
        }
    }
}
