package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class WhoCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final EnumMap<MemberRank, String> lists = new EnumMap<MemberRank, String>(MemberRank.class);
        for(MemberRank rank : MemberRank.values()) {
            lists.put(rank, "");
        }
        int onlinePlayers = 0;
        for(Member onlinePlayer : MemberUtils.getOnlineMembers()) {
            if(!onlinePlayer.isHidden()) {
                final String current = lists.get(onlinePlayer.getRank());
                lists.put(onlinePlayer.getRank(), current + (player.getGameMode() == GameMode.CREATIVE ? "[C]" : (player.hasGodMode() ? "[G]" : "")) + onlinePlayer.getNick() + " ");
                onlinePlayers++;
            }
        }
        player.sendNormal("--- Online Players (" + onlinePlayers + "/" + Bukkit.getMaxPlayers() + ") ---");
        for(Map.Entry<MemberRank, String> entry : lists.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                player.sendMessage(entry.getKey().getColor() + entry.getValue());
            }
        }
    }
}
