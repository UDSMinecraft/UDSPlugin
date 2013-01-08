package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Heal a player's health.
 * @author UndeadScythes
 */
public class HealCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        final String message = Color.MESSAGE + "You have been healed.";
        if(args.length == 0) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.sendMessage(message);
        } else if(numArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.sendMessage(message);
            if(!player.equals(target)) {
                player.sendMessage(Color.MESSAGE + target.getNick() + " has been healed.");
            }
        }
    }

}
