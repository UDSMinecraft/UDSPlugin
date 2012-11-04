package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * Change a players experience level.
 * @author UndeadScythes
 */
public class XPCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsEq(2) && (target = matchesPlayer(args[0])) != null && isOnline(target)) {
            if(args[1].matches("[0-9][0-9]*")) {
                for(int i = 0; i < 10; i++) {
                    target.getWorld().spawnEntity(target.getLocation(), EntityType.EXPERIENCE_ORB);
                }
                target.giveExpLevels(Integer.parseInt(args[1]));
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been granted experience.");
            } else if(args[1].matches("reset")) {
                target.setExp(0);
                target.setLevel(0);
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + "'s experience has been reset.");
            } else {
                player.performCommand("help xp");
            }
        }
    }
}
