package com.undeadscythes.udsplugin.commands;

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
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsEq(0)) {
            TreeMap<ExtendedPlayer.Rank, String> lists = new TreeMap<ExtendedPlayer.Rank, String>();
            for(ExtendedPlayer.Rank rank : ExtendedPlayer.Rank.values()) {
                lists.put(rank, "");
            }
            for(ExtendedPlayer onlinePlayer : UDSPlugin.getOnlinePlayers().values()) {
                String current = lists.get(onlinePlayer.getRank());
                lists.put(onlinePlayer.getRank(), current + (player.getGameMode() == GameMode.CREATIVE ? "[C]" : (player.hasGodMode() ? "[G]" : "")) + onlinePlayer.getDisplayName() + " ");
            }
            player.sendMessage(Color.MESSAGE + "--- Online Players (" + UDSPlugin.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ") ---");
            for(Map.Entry<ExtendedPlayer.Rank, String> entry : lists.entrySet()) {
                if(!entry.getValue().equals("")) {
                    player.sendMessage(entry.getKey().color() + entry.getValue());
                }
            }
        }
    }

}
