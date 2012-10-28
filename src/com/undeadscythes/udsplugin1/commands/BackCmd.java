package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;

/**
 * Teleports a player to their last recorded position.
 * @author UndeadScythes
 */
public class BackCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(hasPerm("back")) {
            if(!player.quietTeleport(player.getBack())) {
                player.sendMessage(Message.BACK_FAIL + "");
            }
        }
    }

}
