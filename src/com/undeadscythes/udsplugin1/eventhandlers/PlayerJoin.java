package com.undeadscythes.udsplugin1.eventhandlers;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.Config;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.Rank;
import com.undeadscythes.udsplugin1.UDSPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Description
 * @author UndeadScythes
 */
public class PlayerJoin implements Listener {
    UDSPlugin plugin;

    public PlayerJoin(UDSPlugin plugin) {
        this.plugin = plugin;
    }

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
            Bukkit.broadcastMessage(Message.NEW_PLAYER + "");
            for(ExtendedPlayer onlinePlayer : UDSPlugin.getOnlinePlayers().values()) {
                onlinePlayer.getInventory().addItem(new ItemStack(Material.getMaterial(plugin.getConfig().getString(Config.WELCOME_GIFT + ""))));
            }
            player.quietTeleport(UDSPlugin.getWarps().get("spawn").getLocation());
        }
        if(UDSPlugin.serverInLockdown && !player.hasLockdownPass()) {
            player.kickPlayer(Message.SERVER_LOCKDOWN + "");
        } else {
            player.sendMessage(Color.MESSAGE + plugin.getConfig().getString(Config.WELCOME + ""));
            if(player.getRank().equals(Rank.DEFAULT)) {
                player.sendMessage(Color.MESSAGE + "Kill monsters or trade with players to earn " + plugin.getConfig().getInt(Config.BUILD_COST + "") + " credits then type /acceptrules in chat.");
            } else if(player.getRank().compareTo(Rank.MOD) >= 0) {
                player.sendMessage(Message.ADMIN_WELCOME + "");
            }
            event.setJoinMessage(Color.BROADCAST + player.getDisplayName() + (player.isInClan() ? " of " + player.getClan() : "") + " has joined.");
        }
    }
}
