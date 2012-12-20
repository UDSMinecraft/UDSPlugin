package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.Bukkit;

/**
 * Jail a player.
 * @author UndeadScythes
 */
public class JailCmd extends AbstractPlayerCommand {
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
                    target.jail(sentence, bail);
                    Bukkit.broadcastMessage(Color.BROADCAST + target.getNick() + " has been jailed for breaking the rules.");
                }
            }
        }
    }
}
