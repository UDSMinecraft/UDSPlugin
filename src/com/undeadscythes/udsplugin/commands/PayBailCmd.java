package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class PayBailCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(isJailed() && canAfford(player.getBail())) {
            player.debit(player.getBail());
            player.sendNormal("You have paid your bail.");
            player.release();
        }
    }
}
