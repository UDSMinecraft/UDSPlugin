package com.undeadscythes.udsplugin.commands;

/**
 * Pay bail to get out of prison early.
 * 
 * @author UndeadScythes
 */
public class PayBailCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(isJailed() && canAfford(player().getBail())) {
            player().debit(player().getBail());
            player().sendNormal("You have paid your bail.");
            player().release();
        }
    }
}
