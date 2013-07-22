package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Various money handling commands.
 * @author UndeadScythes
 */
public class MoneyCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        if(args.length == 0) {
            player.sendMessage(Color.MESSAGE + "You have " + player.getMoney() + " credits.");
        } else if(args.length == 1) {
            if(subCmd.equals("prices")) {
                player.sendMessage(Color.MESSAGE + "--- Server Prices ---");
                player.sendMessage(Color.ITEM + "Build rights: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.BUILD_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "Map of spawn: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.MAP_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "Home protection: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.HOME_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "City shop: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.SHOP_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "VIP rank: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.VIP_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "Clan cost: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.CLAN_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "Clan base cost: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.BASE_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                player.sendMessage(Color.ITEM + "City cost: " + Color.TEXT + UDSPlugin.getConfigInt(ConfigRef.CITY_COST) + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
            } else if(subCmd.equals("rank")) {
                final List<SaveablePlayer> players = UDSPlugin.getPlayers().getSortedValues(new SortByMoney());
                int printed = 0;
                player.sendMessage(Color.MESSAGE + "Top 5 Richest Players:");
                for(SaveablePlayer ranker : players) {
                    if(printed < 5 && !ranker.hasRank(PlayerRank.MOD)) {
                        player.sendMessage(Color.TEXT.toString() + (printed + 1) + ": " + ranker.getRankColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES));
                        printed++;
                    }
                }
                final int rank = players.indexOf(player);
                if(rank > 5 && !player.hasRank(PlayerRank.MOD)) {
                    player.sendMessage(Color.MESSAGE + "Your rank is " + rank + ".");
                }
            } else if(subCmd.equals("help")) {
                sendHelp(1);
            } else if((target = getMatchingPlayer(args[0])) != null && notSelf(target) && hasPerm(Perm.MONEY_OTHER)) {
                player.sendMessage(Color.MESSAGE + target.getNick() + " has " + target.getMoney() + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(subCmd.equals("set")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.setMoney(amount);
                    player.sendMessage(Color.MESSAGE + target.getNick() + "'s account has been set to " + amount + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
                }
            } else if(subCmd.equals("grant")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.credit(amount);
                    player.sendMessage(Color.MESSAGE + target.getNick() + "'s account has been credited " + amount + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
                }
            } else if(subCmd.equals("pay")) {
                if((target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1 && canAfford(amount) && notSelf(target)) {
                    target.credit(amount);
                    player.debit(amount);
                    player.sendMessage(Color.MESSAGE.toString() + amount + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + " have been sent to " + target.getNick() + ".");
                    target.sendMessage(Color.MESSAGE + player.getNick() + " has just paid you " + amount + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
                }
            } else {
                subCmdHelp();
            }
        }
    }
}

/**
 * Compare players by money.
 * @author UndeadScythe
 */
class SortByMoney implements Comparator<SaveablePlayer> {
    @Override
    public int compare(final SaveablePlayer player1, final SaveablePlayer player2) {
        return player2.getMoney() - player1.getMoney();
    }
}

