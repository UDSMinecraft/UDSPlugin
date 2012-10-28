package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;

/**
 * Heal a player's health.
 * @author UndeadScythes
 */
public class HealCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        ExtendedPlayer target;
        if(hasPerm("heal")) {
            if(args.length == 0) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.sendMessage(Message.HEALED.toString());
            } else if((target = matchesPlayer(args[0])) != null) {
                target.setHealth(player.getMaxHealth());
                target.sendMessage(Message.HEALED.toString());
                target.setFoodLevel(20);
                player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been healed.");
            }
        }
    }

}
