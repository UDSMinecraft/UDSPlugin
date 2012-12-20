package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.AbstractPlayerCommand;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.Config;
import com.undeadscythes.udsplugin.PlayerRank;
import org.bukkit.Bukkit;



/**
 * Lets a player get build rights and promotes them to member. Ignores extra arguments.
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends AbstractPlayerCommand {
    @Override
    public final void playerExecute() {
        if(canAfford(Config.buildCost)) {
            player.setRank(PlayerRank.MEMBER);
            player.debit(Config.buildCost);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " has accepted the rules.");
            player.sendMessage(Color.MESSAGE + "Thanks for accepting the rules, enjoy your stay on " + Bukkit.getServerName() + ".");
        }
    }

}
