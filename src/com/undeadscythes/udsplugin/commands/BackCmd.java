package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Teleports a player to their last recorded position.
 * @author UndeadScythes
 */
public class BackCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(0)) {
            if(!player.quietTeleport(player.getBack())) {
                player.sendMessage(Message.BACK_FAIL);
            }
        }
    }

}
