package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;

/**
 * Displays a list of online players.
 * 
 * @author UndeadScythes
 */
public class WhoCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        final EnumMap<PlayerRank, String> lists = new EnumMap<PlayerRank, String>(PlayerRank.class);
        for(PlayerRank rank : PlayerRank.values()) {
            lists.put(rank, "");
        }
        int onlinePlayers = 0;
        for(SaveablePlayer onlinePlayer : PlayerUtils.getOnlinePlayers()) {
            if(!onlinePlayer.isHidden()) {
                final String current = lists.get(onlinePlayer.getRank());
                lists.put(onlinePlayer.getRank(), current + (player().getGameMode() == GameMode.CREATIVE ? "[C]" : (player().hasGodMode() ? "[G]" : "")) + onlinePlayer.getNick() + " ");
                onlinePlayers++;
            }
        }
        player().sendNormal("--- Online Players (" + onlinePlayers + "/" + Bukkit.getMaxPlayers() + ") ---");
        for(Map.Entry<PlayerRank, String> entry : lists.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                player().sendMessage(entry.getKey().getColor() + entry.getValue());
            }
        }
    }
}
