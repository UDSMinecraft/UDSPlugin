package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import org.bukkit.*;

/**
 * Rent VIP rank and perform other tasks. Sends help on wrong arguments.
 * @author UndeadScythes
 */
public class VIPCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        if(args.length == 0) {
            if(player.getRank().equals(PlayerRank.VIP)) {
                player.sendMessage(Color.MESSAGE + "You have " + player.getVIPTimeString()+ " left in VIP.");
            } else if(canAfford(Config.vipCost) && notJailed() && hasPerm(Perm.VIP_BUY)) {
                player.setRank(PlayerRank.VIP);
                player.setVIPTime(System.currentTimeMillis());
                player.setVIPSpawns(Config.vipSpawns);
                player.sendMessage(Color.MESSAGE + "Welcome to the elite, enjoy your VIP status.");
            }
        } else if(maxArgsHelp(2)) {
            if(args[0].equals("spawns")) {
                int page;
                if(args.length == 2 && (page = parseInt(args[1])) != -1) {
                    sendPage(page, player);
                } else {
                    sendPage(1, player);
                }
            } else {
                subCmdHelp();
            }
        }
    }

    /**
     * Sends a full page of items to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(final int page, final SaveablePlayer player) {
        final int pages = (Config.whitelistVIP.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There are no currently whitelisted items.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- VIP Item Whitelist " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Material i : Config.whitelistVIP) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    String item = i.toString();
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
