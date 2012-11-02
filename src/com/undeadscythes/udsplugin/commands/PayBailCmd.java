package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Pay bail to get out of prison early.
 * @author UndeadScythes
 */
public class PayBailCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0) && isJailed() && canAfford(player.getBail())) {
            player.debit(player.getBail());
            player.sendMessage(Color.MESSAGE + "You have paid your bail.");
            player.release();
        }
    }
}
