package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Heal a player's health.
 * @author UndeadScythes
 */
public class HealCmd extends CommandValidator {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        final String message = "You have been healed.";
        if(args.length == 0) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.sendNormal(message);
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.sendNormal(message);
            if(!player.equals(target)) {
                player.sendNormal(target.getNick() + " has been healed.");
            }
        }
    }

}
