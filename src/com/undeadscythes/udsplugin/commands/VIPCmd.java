package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class VIPCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            if(player().hasPerm(Perm.VIP_RANK)) {
                player().sendNormal("You have " + player().getVIPTimeString() + " left in VIP.");
            } else if(canAfford(Config.VIP_COST) && notJailed() && hasPerm(Perm.VIP_BUY)) {
                player().setRank(PlayerRank.VIP);
                player().setVIPTime(System.currentTimeMillis());
                player().setVIPSpawns(Config.VIP_SPAWNS);
                player().debit(Config.VIP_COST);
                player().sendNormal("Welcome to the elite, enjoy your VIP status.");
            }
        } else if(maxArgsHelp(2)) {
            if(subCmdEquals("spawns")) {
                if(args.length != 2) {
                    sendPage(1, player());
                } else {
                    final int page = getInteger(args[1]);
                    if(page != -1) {
                        sendPage(page, player());
                    }
                }
            } else if(player().hasPerm(Perm.VIP_FOR_LIFE)) {
                Member target;
                if((target = matchPlayer(args[0])) != null) {
                    target.setRank(PlayerRank.VIP);
                    target.setVipForLife(true);
                    try {
                        PlayerUtils.getOnlinePlayer(target).sendNormal("You have been granted VIP for life.");
                    } catch (PlayerNotOnlineException ex) {}
                    player().sendNormal(target.getNick() + " has been granted VIP for life.");
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private void sendPage(final int page, final Member player) {
        final int pages = (Config.VIP_WHITELIST.size() + 8) / 9;
        if(pages == 0) {
            player.sendNormal("There are no currently whitelisted items.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- VIP Item Whitelist " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Material i : Config.VIP_WHITELIST) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    String item = i.toString();
                    item = item.substring(0, 1).toUpperCase().concat(item.substring(1, item.length()).toLowerCase());
                    player.sendListItem(item + " (", i + "" + Color.ITEM  + ")");
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
