package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.ExtendedPlayer.Rank;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Rent VIP rank and perform other tasks.
 * @author UndeadScythes
 */
public class VIPCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(argsLessEq(2)) {
            if(args.length == 0) {
                if(player.getRank().equals(Rank.VIP)) {
                    player.sendMessage(Color.MESSAGE + "You have " + player.getVIPTimeString()+ " left in VIP.");
                } else if(canAfford(Config.VIP_COST) && notJailed() && hasPerm(Perm.VIP_BUY)) {
                    player.setRank(Rank.VIP);
                    player.setVIPTime(System.currentTimeMillis());
                    player.setVIPSpawns(Config.VIP_SPAWNS);
                    player.sendMessage(Message.VIP_RANK);
                }
            } else {
                if(args[0].equals("spawns")) {
                    int page;
                    if(args.length == 2 && (page = parseInt(args[1])) != -1) {
                        sendPage(page, player);
                    } else {
                        sendPage(1, player);
                    }
                }
            }
        }
    }

    /**
     * Sends a full page of items to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(int page, ExtendedPlayer player) {
        int pages = (Config.WHITELIST.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Message.NO_WHITELIST_ITEMS);
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- VIP Item Whitelist " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(int i : Config.WHITELIST) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    String item = Material.getMaterial(i).toString();
                    item = item.substring(0, 1).toUpperCase().concat(item.substring(1, item.length()).toLowerCase());
                    player.sendMessage(Color.ITEM + item + " (" + Color.TEXT + i + Color.ITEM  + ")");
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
