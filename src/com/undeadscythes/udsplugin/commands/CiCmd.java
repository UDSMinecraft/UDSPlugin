package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Clears all items from a player's inventory.
 * @author UndeadScythes
 */
public class CiCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        player.getInventory().clear(-1, -1);
        player.sendMessage(Color.MESSAGE + "Inventory cleared.");
    }
}
