package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set a player's health to maximum.
 * 
 * @author UndeadScythes
 */
public class HealCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        final String message = "You have been healed.";
        if(argsLength() == 0) {
            player().setHealth(player().getMaxHealth());
            player().setFoodLevel(20);
            player().sendNormal(message);
        } else if(numArgsHelp(1) && (target = matchPlayer(arg(0))) != null) {
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.sendNormal(message);
            if(!player().equals(target)) {
                player().sendNormal(target.getNick() + " has been healed.");
            }
        }
    }

}
