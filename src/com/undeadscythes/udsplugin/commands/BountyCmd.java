package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class BountyCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsLessEq(2)) {
            int bounty;
            SaveablePlayer target;
            if(args.length == 0) {
                sendPage(1, player);
            } else if(args.length == 1) {
                int page;
                if((page = parseInt(args[0])) != -1) {
                    sendPage(page, player);
                }
            } else if(args.length == 2 && (target = matchesPlayer(args[0])) != null && (bounty = parseInt(args[1])) != -1 && canAfford(bounty) && notSelf(target)) {
                player.debit(bounty);
                target.addBounty(bounty);
                Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " placed a bounty on " + target.getDisplayName());
            }
        }
    }

    /**
     * Sends a full page of bounties to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(int page, SaveablePlayer player) {
        TreeMap<Integer, String> bounties = getBounties();
        int pages = (bounties.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There are no bounties to collect.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- Current Bounties " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Map.Entry<Integer, String> entry : bounties.entrySet()) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + "- " + entry.getValue() + "'s reward: " + Color.TEXT + entry.getKey() + " " + Config.CURRENCIES);
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }

    /**
     * Gets a map of bounties and players.
     * @return Bounty-player map.
     */
    private TreeMap<Integer, String> getBounties() {
        TreeMap<Integer, String> bounties = new TreeMap<Integer, String>();
        for(SaveablePlayer player : UDSPlugin.getPlayers().values()) {
            if(player.getBounty() > 0) {
                bounties.put(player.getBounty(), player.getDisplayName());
            }
        }
        return bounties;
    }
}
