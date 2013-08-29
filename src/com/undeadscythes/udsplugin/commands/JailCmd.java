package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class JailCmd extends CommandHandler {
    private static int nextCell = 0;

    public static boolean jail(final Member player, final long sentence, final int bail) {
        if(player != null) {
            Location location = player.getLocation().clone();
            if(player.quietTeleport(WarpUtils.getWarp("jail" + nextCell))) {
                nextCell++;
            } else if(player.quietTeleport(WarpUtils.getWarp("jail0"))) {
                nextCell = 1;
            } else if(!player.quietTeleport(WarpUtils.getWarp("jail"))) {
                return false;
            }
            player.getWorld().strikeLightningEffect(location);
            player.jail(sentence, bail);
            player.sendNormal("You have been jailed for " + sentence + " minutes.");
            if(bail != 0) {
                player.sendNormal("If you can afford it, use /paybail to get out early for " + bail + " " + Config.CURRENCIES + ".");
            }
        }
        return true;
    }

    @Override
    public void playerExecute() throws TargetMatchesSenderException, PlayerNotOnlineException {
        if(minArgsHelp(1) && maxArgsHelp(3)) {
            Member target = matchOtherOnlinePlayer(args[0]);
            if(notJailed(target)) {
                long sentence = 5;
                int bail = 1000;
                if(args.length > 1) {
                    sentence = getInteger(args[1]);
                    if(args.length > 2) {
                        bail = getInteger(args[2]);
                    }
                }
                if(sentence > -1 && bail > -1) {
                    if(jail(target, sentence, bail)) {
                        target.sendNormal("You have been jailed for breaking the rules.");
                        UDSPlugin.sendBroadcast(target.getNick() + " has been jailed for breaking the rules.");
                    } else {
                        player.sendError("There are no jail points set.");
                    }
                }
            }
        }
    }
}
