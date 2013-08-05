package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;


/**
 * Various money handling commands.
 * @author UndeadScythes
 */
public class MoneyCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(argsLength() == 0) {
            player().sendNormal("You have " + player().getMoney() + " credits.");
        } else if(argsLength() == 1) {
            if(subCmdEquals("prices")) {
                player().sendNormal("--- Server Prices ---");
                player().sendListItem("Membership: ", Config.BUILD_COST + " " + Config.CURRENCIES);
                player().sendListItem("Map of spawn: ", Config.MAP_COST + " " + Config.CURRENCIES);
                player().sendListItem("Home protection: ", Config.HOME_COST + " " + Config.CURRENCIES);
                player().sendListItem("City shop: ", Config.SHOP_COST + " " + Config.CURRENCIES);
                player().sendListItem("VIP rank: ", Config.VIP_COST + " " + Config.CURRENCIES);
                player().sendListItem("Clan cost: ", Config.CLAN_COST + " " + Config.CURRENCIES);
                player().sendListItem("Clan base cost: ", Config.BASE_COST + " " + Config.CURRENCIES);
                player().sendListItem("City cost: ", Config.CITY_COST + " " + Config.CURRENCIES);
            } else if(subCmdEquals("rank")) {
                final List<SaveablePlayer> players = PlayerUtils.getSortedPlayers(new SortByMoney());
                int printed = 0;
                player().sendNormal("Top 5 Richest Players:");
                for(SaveablePlayer ranker : players) {
                    if(printed < 5 && !ranker.hasPermission(Perm.MIDAS)) {
                        player().sendText("" + (printed + 1) + ": " + ranker.getRankColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.CURRENCIES);
                        printed++;
                    }
                }
                final int rank = players.indexOf(player());
                if(rank > 5 && !player().hasPermission(Perm.MIDAS)) {
                    player().sendNormal("Your rank is " + rank + ".");
                }
            } else if(subCmdEquals("help")) {
                sendHelp(1);
            } else if((target = matchesPlayer(arg(0))) != null && notSelf(target) && hasPerm(Perm.MONEY_OTHER)) {
                player().sendNormal(target.getNick() + " has " + target.getMoney() + " " + Config.CURRENCIES + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(subCmdEquals("set")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = matchesPlayer(arg(1))) != null && (amount = isInteger(arg(2))) != -1) {
                    target.setMoney(amount);
                    player().sendNormal(target.getNick() + "'s account has been set to " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmdEquals("grant")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = matchesPlayer(arg(1))) != null && (amount = isInteger(arg(2))) != -1) {
                    target.credit(amount);
                    player().sendNormal(target.getNick() + "'s account has been credited " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmdEquals("pay")) {
                if((target = matchesPlayer(arg(1))) != null && (amount = isInteger(arg(2))) != -1 && canAfford(amount) && notSelf(target)) {
                    target.credit(amount);
                    player().debit(amount);
                    player().sendNormal(amount + " " + Config.CURRENCIES + " have been sent to " + target.getNick() + ".");
                    target.sendNormal(player().getNick() + " has just paid you " + amount + " " + Config.CURRENCIES + ".");
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
