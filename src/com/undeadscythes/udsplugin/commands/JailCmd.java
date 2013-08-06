package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Jail a player.
 * 
 * @author UndeadScythes
 */
public class JailCmd extends CommandHandler {
    private static int nextCell = 0;
    
    public static void jail(final SaveablePlayer player, final long sentence, final int bail) {
        if(player != null) {
            player.getWorld().strikeLightningEffect(player.getLocation());
            if(player.quietTeleport(WarpUtils.getWarp("jail" + nextCell))) {
                nextCell++;
            } else if(player.quietTeleport(WarpUtils.getWarp("jail0"))) {
                nextCell = 1;
            } else {
                player.quietTeleport(WarpUtils.getWarp("jail"));
            }
            player.jail(sentence, bail);
            player.sendNormal("You have been jailed for " + sentence + " minutes.");
            if(bail != 0) {
                player.sendNormal("If you can afford it, use /paybail to get out early for " + bail + " " + Config.CURRENCIES + ".");
            }
        }
    }
    
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && maxArgsHelp(3)) {
            if((target = matchOnlinePlayer(arg(0))) != null && notSelf(target) && notJailed(target)) {
                long sentence = 5;
                int bail = 1000;
                if(argsLength() > 1) {
                    sentence = getInteger(arg(1));
                    if(argsLength() > 2) {
                        bail = getInteger(arg(2));
                    }
                }
                if(sentence > -1 && bail > -1) {
                    target.sendNormal("You have been jailed for breaking the rules.");
                    jail(target, sentence, bail);
                    UDSPlugin.sendBroadcast(target.getNick() + " has been jailed for breaking the rules.");
                }
            }
        }
    }
}
