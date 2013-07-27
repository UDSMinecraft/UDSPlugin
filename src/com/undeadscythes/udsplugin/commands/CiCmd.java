package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Clears all items from a player's inventory.
 * @author UndeadScythes
 */
public class CiCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        player.getInventory().clear(-1, -1);
        player.sendNormal("Inventory cleared.");
    }
}
