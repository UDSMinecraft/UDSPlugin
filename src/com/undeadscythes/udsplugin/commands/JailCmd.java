package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import org.bukkit.*;

/**
 * Jail a player.
 * @author UndeadScythes
 */
public class JailCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsMoreLessInc(1, 3) && (target = matchesPlayer(args[0])) != null && isOnline(target) && notSelf(target) && notJailed(target)) {
            long sentence = 5;
            int bail = 1000;
            if(args.length > 1 && (sentence = parseInt(args[1])) != -1) {
                if(args.length > 2 && (bail = parseInt(args[2])) != -1) {
                    target.sendMessage(Color.MESSAGE + "You have been jailed for breaking the rules.");
                    try {
                        target.jail(sentence, bail);
                        Bukkit.broadcastMessage(Color.BROADCAST + target.getDisplayName() + " has been jailed for breaking the rules.");
                    } catch (IOException ex) {
                        player.sendMessage(Color.ERROR + "There is no 'jailin' warp point " + target.getDisplayName() + " is wandering free.");
                    }
                }
            }
        }
    }
}
