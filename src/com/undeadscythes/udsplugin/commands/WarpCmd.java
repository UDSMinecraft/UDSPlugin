package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;

/**
 * Use warp points.
 * @author UndeadScythes
 */
public class WarpCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Warp warp;
        if(argsLength() == 0) {
            final Set<String> warps = new TreeSet<String>();
            for(Warp test : WarpUtils.getWarps()) {
                if(player().hasRank(test.getRank())) {
                    warps.add(test.getName() + (test.getPrice() > 0 ? " (" + test.getPrice() + ")" : ""));
                }
            }
            if(warps.isEmpty()) {
                player().sendNormal("You don't have access to any warps.");
            } else {
                player().sendNormal("Available warps (with prices):");
                player().sendText(StringUtils.join(warps.toArray(), ", "));
            }
        } else if(numArgsHelp(1) && (warp = warpExists(arg(0))) != null && hasRank(warp.getRank()) && canAfford(warp.getPrice())) {
            player().debit(warp.getPrice());
            player().teleport(warp.getLocation());
        }
    }
}
