package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Lets a player get build rights and promotes them to member.
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(canAfford(Config.BUILD_COST) && argsEq(0)) {
            player.setRank(ExtendedPlayer.Rank.MEMBER);
            player.debit(Config.BUILD_COST);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " has accepted the rules.");
            player.sendMessage(Message.ACCEPT_RULES);
        }
    }

}
