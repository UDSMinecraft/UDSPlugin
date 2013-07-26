package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.Bukkit;

/**
 * Jail a player.
 * @author UndeadScythes
 */
public class JailCmd extends CommandWrapper {
    private static int nextCell = 0;
    
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && maxArgsHelp(3)) {
            if((target = getMatchingPlayer(args[0])) != null && isOnline(target) && notSelf(target) && notJailed(target)) {
                long sentence = 5;
                int bail = 1000;
                if(args.length > 1) {
                    sentence = parseInt(args[1]);
                    if(args.length > 2) {
                        bail = parseInt(args[2]);
                    }
                }
                if(sentence > -1 && bail > -1) {
                    target.sendMessage(Color.MESSAGE + "You have been jailed for breaking the rules.");
                    jail(target, sentence, bail);
                    Bukkit.broadcastMessage(Color.BROADCAST + target.getNick() + " has been jailed for breaking the rules.");
                }
            }
        }
    }
    
    public static void jail(final SaveablePlayer player, final long sentence, final int bail) {
        if(player != null) {
            player.getWorld().strikeLightningEffect(player.getLocation());
            if(player.quietTeleport(UDSPlugin.getWarps().get("jail" + nextCell))) {
                nextCell++;
            } else if(player.quietTeleport(UDSPlugin.getWarps().get("jail0"))) {
                nextCell = 1;
            } else {
                player.quietTeleport(UDSPlugin.getWarps().get("jail"));
            }
            player.jail(sentence, bail);
            player.sendMessage(Color.MESSAGE + "You have been jailed for " + sentence + " minutes.");
            if(bail != 0) {
                player.sendMessage(Color.MESSAGE + "If you can afford it, use /paybail to get out early for " + bail + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
            }
        }
    }
}
