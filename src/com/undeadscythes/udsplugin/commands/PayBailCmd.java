package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Pay bail to get out of prison early.
 * @author UndeadScythes
 */
public class PayBailCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(isJailed() && canAfford(player.getBail())) {
            player.debit(player.getBail());
            player.sendMessage(Color.MESSAGE + "You have paid your bail.");
            player.release();
        }
    }
}
