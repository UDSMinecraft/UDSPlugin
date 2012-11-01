package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.apache.commons.lang.*;

/**
 * Use warp points.
 * @author UndeadScythes
 */
public class WarpCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsLessEq(1)) {
            Warp warp;
            if(args.length == 0) {
                TreeSet<String> warps = new TreeSet<String>();
                for(Warp test : UDSPlugin.getWarps().values()) {
                    if(player.getRank().compareTo(test.getRank()) >= 0) {
                        warps.add(test.getName() + (test.getPrice() > 0 ? "(" + test.getPrice() + ")" : ""));
                    }
                }
                player.sendMessage(Color.MESSAGE + "Available warps:");
                String list = StringUtils.join(warps.toArray(), ", ");
                player.sendMessage(Color.TEXT + list.substring(0, list.length() - 3));
            } else if((warp = matchesWarp(args[0])) != null && hasRank(warp.getRank()) && canAfford(warp.getPrice())) {
                player.debit(warp.getPrice());
                player.teleport(warp);
            }
        }
    }
}
