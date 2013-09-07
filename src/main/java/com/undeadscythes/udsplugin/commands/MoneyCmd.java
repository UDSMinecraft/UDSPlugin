package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class MoneyCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        if(args.length == 0) {
            player.sendNormal("You have " + player.getMoney() + " credits.");
        } else if(args.length == 1) {
            if(subCmdEquals("prices")) {
                player.sendNormal("--- Server Prices ---");
                player.sendListItem("Membership: ", Config.BUILD_COST + " " + Config.CURRENCIES);
                player.sendListItem("Map of spawn: ", Config.MAP_COST + " " + Config.CURRENCIES);
                player.sendListItem("Home protection: ", Config.HOME_COST + " " + Config.CURRENCIES);
                player.sendListItem("City shop: ", Config.SHOP_COST + " " + Config.CURRENCIES);
                player.sendListItem("VIP rank: ", Config.VIP_COST + " " + Config.CURRENCIES);
                player.sendListItem("Clan cost: ", Config.CLAN_COST + " " + Config.CURRENCIES);
                player.sendListItem("Clan base cost: ", Config.BASE_COST + " " + Config.CURRENCIES);
                player.sendListItem("City cost: ", Config.CITY_COST + " " + Config.CURRENCIES);
            } else if(subCmdEquals("rank")) {
                final List<OfflineMember> players = MemberUtils.getSortedMembers(new SortByMoney());
                int printed = 0;
                player.sendNormal("Top 5 Richest Players:");
                for(OfflineMember ranker : players) {
                    if(printed < 5 && !ranker.hasPerm(Perm.MIDAS)) {
                        player.sendText("" + (printed + 1) + ": " + ranker.getRankColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.CURRENCIES);
                        printed++;
                    }
                }
                final int rank = players.indexOf(player);
                if(rank > 5 && !player.hasPerm(Perm.MIDAS)) {
                    player.sendNormal("Your rank is " + rank + ".");
                }
            } else if(subCmdEquals("help")) {
                sendHelp(1);
            } else if(hasPerm(Perm.MONEY_OTHER)) {
                OfflineMember target = matchOtherPlayer(args[0]);
                player.sendNormal(target.getNick() + " has " + target.getMoney() + " " + Config.CURRENCIES + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(subCmdEquals("set")) {
                OfflineMember target = matchPlayer(args[1]);
                if(hasPerm(Perm.MONEY_ADMIN) && (amount = getInteger(args[2])) != -1) {
                    target.setMoney(amount);
                    player.sendNormal(target.getNick() + "'s account has been set to " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmdEquals("grant")) {
                OfflineMember target = matchPlayer(args[1]);
                if(hasPerm(Perm.MONEY_ADMIN) && (amount = getInteger(args[2])) != -1) {
                    target.credit(amount);
                    player.sendNormal(target.getNick() + "'s account has been credited " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmdEquals("pay")) {
                OfflineMember target = matchOtherPlayer(args[1]);
                if(((amount = getInteger(args[2])) != -1 && canAfford(amount))) {
                    target.credit(amount);
                    player.debit(amount);
                    player.sendNormal(amount + " " + Config.CURRENCIES + " have been sent to " + target.getNick() + ".");
                    try {
                        MemberUtils.getOnlineMember(target).sendNormal(player.getNick() + " has just paid you " + amount + " " + Config.CURRENCIES + ".");
                    } catch(PlayerNotOnlineException ex) {}
                }
            } else {
                subCmdHelp();
            }
        }
    }

    @Override
    public void consoleExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        if(args.length == 1) {
            if(subCmdEquals("prices")) {
                sender.sendNormal("--- Server Prices ---");
                sender.sendListItem("Membership: ", Config.BUILD_COST + " " + Config.CURRENCIES);
                sender.sendListItem("Map of spawn: ", Config.MAP_COST + " " + Config.CURRENCIES);
                sender.sendListItem("Home protection: ", Config.HOME_COST + " " + Config.CURRENCIES);
                sender.sendListItem("City shop: ", Config.SHOP_COST + " " + Config.CURRENCIES);
                sender.sendListItem("VIP rank: ", Config.VIP_COST + " " + Config.CURRENCIES);
                sender.sendListItem("Clan cost: ", Config.CLAN_COST + " " + Config.CURRENCIES);
                sender.sendListItem("Clan base cost: ", Config.BASE_COST + " " + Config.CURRENCIES);
                sender.sendListItem("City cost: ", Config.CITY_COST + " " + Config.CURRENCIES);
            } else if(subCmdEquals("rank")) {
                final List<OfflineMember> players = MemberUtils.getSortedMembers(new SortByMoney());
                int printed = 0;
                sender.sendNormal("Top 5 Richest Players:");
                for(OfflineMember ranker : players) {
                    if(printed < 5 && !ranker.hasPerm(Perm.MIDAS)) {
                        sender.sendText("" + (printed + 1) + ": " + ranker.getRankColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.CURRENCIES);
                        printed++;
                    }
                }
            } else if(subCmdEquals("help")) {
                sendHelp(1);
            } else {
                OfflineMember target = matchOtherPlayer(args[0]);
                sender.sendNormal(target.getNick() + " has " + target.getMoney() + " " + Config.CURRENCIES + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(subCmdEquals("set")) {
                OfflineMember target = matchPlayer(args[1]);
                if((amount = getInteger(args[2])) != -1) {
                    target.setMoney(amount);
                    sender.sendNormal(target.getNick() + "'s account has been set to " + amount + " " + Config.CURRENCIES + ".");
                }
            } else if(subCmdEquals("grant")) {
                OfflineMember target = matchPlayer(args[1]);
                if((amount = getInteger(args[2])) != -1) {
                    target.credit(amount);
                    sender.sendNormal(target.getNick() + "'s account has been credited " + amount + " " + Config.CURRENCIES + ".");
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
