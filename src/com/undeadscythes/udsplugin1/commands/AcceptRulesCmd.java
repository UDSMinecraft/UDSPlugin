package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.Config;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import com.undeadscythes.udsplugin1.Rank;
import com.undeadscythes.udsplugin1.UDSPlugin;
import org.bukkit.Bukkit;

/**
 * Lets a player get build rights and promotes them to member.
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends PlayerCommandExecutor {
    private UDSPlugin plugin;

    public AcceptRulesCmd(UDSPlugin plugin) {
        this.plugin = plugin;
    }
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(hasPerm("acceptrules") && canAfford(plugin.getConfig().getInt(Config.BUILD_COST + ""))) {
            player.setRank(Rank.MEMBER);
            player.debit(plugin.getConfig().getInt(Config.BUILD_COST + ""));
            Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " has accepted the rules.");
            player.sendMessage(Message.ACCEPT_RULES + "");
        }
    }

}
