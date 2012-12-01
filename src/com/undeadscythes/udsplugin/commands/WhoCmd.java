package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;

/**
 * Displays a list of online players.
 * @author UndeadScythes
 */
public class WhoCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        TreeMap<PlayerRank, String> lists = new TreeMap<PlayerRank, String>();
        for(PlayerRank rank : PlayerRank.values()) {
            lists.put(rank, "");
        }
        for(SaveablePlayer onlinePlayer : UDSPlugin.getOnlinePlayers().values()) {
            String current = lists.get(onlinePlayer.getRank());
            lists.put(onlinePlayer.getRank(), current + (player.getGameMode() == GameMode.CREATIVE ? "[C]" : (player.hasGodMode() ? "[G]" : "")) + onlinePlayer.getDisplayName() + " ");
        }
        player.sendMessage(Color.MESSAGE + "--- Online Players (" + UDSPlugin.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ") ---");
        for(Map.Entry<PlayerRank, String> entry : lists.entrySet()) {
            if(!entry.getValue().equals("")) {
                player.sendMessage(entry.getKey().color() + entry.getValue());
            }
        }
    }

}
