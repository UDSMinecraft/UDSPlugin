package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import org.bukkit.*;

/**
 * Lets a player get build rights and promotes them to member. Ignores extra arguments.
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(canAfford(Config.buildCost)) {
            player.setRank(PlayerRank.MEMBER);
            player.debit(Config.buildCost);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " has accepted the rules.");
            player.sendMessage(Color.MESSAGE + "Thanks for accepting the rules, enjoy your stay on " + Bukkit.getServerName() + ".");
        }
    }

}
