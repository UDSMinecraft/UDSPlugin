package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ExtendedPlayer.Rank;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

/**
 * Description
 * @author UndeadScythes
 */
public class PlayerJoin implements Listener {
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        ExtendedPlayer player;
        if(UDSPlugin.getPlayers().containsKey(playerName)) {
            player = UDSPlugin.getPlayers().get(playerName);
            player.wrapPlayer(event.getPlayer());
            UDSPlugin.getOnlinePlayers().put(playerName, player);
        } else {
            player = new ExtendedPlayer(event.getPlayer());
            UDSPlugin.getPlayers().put(playerName, player);
            UDSPlugin.getOnlinePlayers().put(playerName, player);
            if(player.getName().equals(Config.SERVER_OWNER)) {
                player.setRank(Rank.OWNER);
                player.sendMessage(Message.OWNER_FIRST_LOG);
            } else {
                Bukkit.broadcastMessage(Message.NEW_PLAYER.toString());
                for(ExtendedPlayer onlinePlayer : UDSPlugin.getOnlinePlayers().values()) {
                    onlinePlayer.giveAndDrop(new ItemStack(Config.WELCOME_GIFT));
                }
            }
            player.quietTeleport(UDSPlugin.getWarps().get("spawn"));
        }
        if(UDSPlugin.serverInLockdown && !player.hasLockdownPass()) {
            player.kickPlayer(Message.SERVER_LOCKDOWN.toString());
        } else {
            player.sendMessage(Color.MESSAGE + Config.WELCOME);
            if(player.getRank().equals(Rank.DEFAULT)) {
                player.sendMessage(Color.MESSAGE + "Kill monsters or trade with players to earn " + Config.BUILD_COST + " credits then type /acceptrules in chat.");
            } else if(player.getRank().compareTo(Rank.MOD) >= 0) {
                player.sendMessage(Config.WELCOME_ADMIN);
            }
            event.setJoinMessage(Color.BROADCAST + player.getDisplayName() + (player.isInClan() ? " of " + player.getClan() : "") + " has joined.");
        }
    }
}
