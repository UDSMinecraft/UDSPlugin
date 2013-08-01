package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;


/**
 * Various money handling commands.
 * @author UndeadScythes
 */
public class MoneyCmd extends CommandValidator {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            player.sendNormal("You have " + player.getMoney() + " credits.");
        } else if(args.length == 1) {
            if(subCmd.equals("prices")) {
                player.sendNormal("--- Server Prices ---");
                player.sendListItem("Membership: ", Config.BUILD_COST + " " + Config.CURRENCIES);
                player.sendListItem("Map of spawn: ", Config.MAP_COST + " " + Config.CURRENCIES);
                player.sendListItem("Home protection: ", Config.HOME_COST + " " + Config.CURRENCIES);
                player.sendListItem("City shop: ", Config.SHOP_COST + " " + Config.CURRENCIES);
                player.sendListItem("VIP rank: ", Config.VIP_COST + " " + Config.CURRENCIES);
                player.sendListItem("Clan cost: ", Config.CLAN_COST + " " + Config.CURRENCIES);
                player.sendListItem("Clan base cost: ", Config.BASE_COST + " " + Config.CURRENCIES);
                player.sendListItem("City cost: ", Config.CITY_COST + " " + Config.CURRENCIES);
            } else if(subCmd.equals("rank")) {
                final List<SaveablePlayer> players = PlayerUtils.getSortedPlayers(new SortByMoney());
                int printed = 0;
                player.sendNormal("Top 5 Richest Players:");
                for(SaveablePlayer ranker : players) {
                    if(printed < 5 && !ranker.hasPermission(Perm.MIDAS)) {
                        player.sendText("" + (printed + 1) + ": " + ranker.getRankColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.CURRENCIES);
                        printed++;
                    }
                }
                final int rank = players.indexOf(player);
                if(rank > 5 && !player.hasPermission(Perm.MIDAS)) {
                    player.sendNormal("Your rank is " + rank + ".");
                }
            } else if(subCmd.equals("help")) {
                sendHelp(1);
            } else if((target = getMatchingPlayer(args[0])) != null && notSelf(target) && hasPerm(Perm.MONEY_OTHER)) {
                player.sendNormal(target.getNick() + " has " + target.getMoney() + " " + Config.CURRENCIES + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(subCmd.equals("set")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.setMoney(amount);
                    player.sendNormal(target.getNick() + "'s account has been set to " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmd.equals("grant")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.credit(amount);
                    player.sendNormal(target.getNick() + "'s account has been credited " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmd.equals("pay")) {
                if((target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1 && canAfford(amount) && notSelf(target)) {
                    target.credit(amount);
                    player.debit(amount);
                    player.sendNormal(amount + " " + Config.CURRENCIES + " have been sent to " + target.getNick() + ".");
                    target.sendNormal(player.getNick() + " has just paid you " + amount + " " + Config.CURRENCIES + ".");
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
