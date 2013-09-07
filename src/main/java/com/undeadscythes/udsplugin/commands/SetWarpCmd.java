package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class SetWarpCmd extends CommandHandler {
    @Override
    public void playerExecute() throws NoEnumFoundException {
        int price;
        final String message = "Warp point set.";
        if(args.length == 1) {
            if(noWarpExists(args[0]) && noBadLang(args[0])) {
                WarpUtils.addWarp(new Warp(args[0], player.getLocation(), MemberRank.NEWBIE, 0));
                player.sendNormal(message);
            }
        } else if(args.length == 2) {
            MemberRank rank = getRank(args[1]);
            if(noWarpExists(args[0]) && noBadLang(args[0])) {
                WarpUtils.addWarp(new Warp(args[0], player.getLocation(), rank, 0));
                player.sendNormal(message);
            }
        } else if(numArgsHelp(3) && noWarpExists(args[0]) && noBadLang(args[0]) && (price = getInteger(args[2])) != -1) {
            MemberRank rank = getRank(args[1]);
            WarpUtils.addWarp(new Warp(args[0], player.getLocation(), rank, price));
            player.sendNormal(message);
        }
    }
}
