package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.utilities.*;

/**
 * Allows a player to toggle whether or not they can engage in PvP.
 * 
 * @author UndeadScythes
 */
public class PvpCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(!player().hasPvp()) {
            player().togglePvp();
            player().sendNormal("PvP has been enabled, you can't turn it off for 5 minutes.");
        } else {
            if(player().getKills() == 0) {
                final long timeRemaining = player().getPvpTime() + 300000 - System.currentTimeMillis();
                if(timeRemaining < 0) {
                    player().togglePvp();
                    player().sendNormal("PvP has been disabled.");
                } else {
                    player().sendNormal("You must remain in PvP for " + TimeUtils.timeToString(timeRemaining) + ".");
                }
            } else {
                player().sendNormal("You must remain in PvP for at least another " + player().getKills() + " hours.");
            }
        }
    }
}
