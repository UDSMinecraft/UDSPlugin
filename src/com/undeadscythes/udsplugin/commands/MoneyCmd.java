package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Various money handling commands.
 * @author UndeadScythes
 */
public class MoneyCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        SaveablePlayer target;
        if(args.length == 0) {
            player.sendMessage(Color.MESSAGE + "You have " + player.getMoney() + " credits.");
        } else if(args.length == 1) {
            if(args[0].equals("prices")) {
                player.sendMessage(Color.MESSAGE + "--- Server Prices ---");
                player.sendMessage(Color.ITEM + "Build rights: " + Color.TEXT + Config.buildCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "Map of spawn: " + Color.TEXT + Config.mapCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "Home protection: " + Color.TEXT + Config.homeCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "City shop: " + Color.TEXT + Config.shopCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "VIP rank: " + Color.TEXT + Config.vipCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "Clan cost: " + Color.TEXT + Config.clanCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "Clan base cost: " + Color.TEXT + Config.baseCost + " " + Config.currencies);
                player.sendMessage(Color.ITEM + "City cost: " + Color.TEXT + Config.cityCost + " " + Config.currencies);
            } else if(args[0].equals("rank")) {
                List<SaveablePlayer> players = UDSPlugin.getPlayers().getSortedValues(new SortByMoney());
                int printed = 0;
                player.sendMessage(Color.MESSAGE + "Top 5 Richest Players:");
                for(SaveablePlayer ranker : players) {
                    if(printed < 5 && ranker.getRank().compareTo(PlayerRank.MOD) < 0) {
                        player.sendMessage(Color.TEXT.toString() + (printed + 1) + ": " + ranker.getRank().getColor() + ranker.getNick() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.currencies);
                        printed++;
                    }
                }
                int rank = players.indexOf(player);
                if(rank > 5 && player.getRank().compareTo(PlayerRank.MOD) < 0) {
                    player.sendMessage(Color.MESSAGE + "Your rank is " + rank + ".");
                }
            } else if(args[0].equals("help")) {
                sendHelp(1);
            } else if((target = getMatchingPlayer(args[0])) != null && notSelf(target) && hasPerm(Perm.MONEY_OTHER)) {
                player.sendMessage(Color.MESSAGE + target.getNick() + " has " + target.getMoney() + " " + Config.currencies + ".");
            }
        } else if(numArgsHelp(3)) {
            int amount;
            if(args[0].equals("set")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.setMoney(amount);
                    player.sendMessage(Color.MESSAGE + target.getNick() + "'s account has been set to " + amount + " " + Config.currencies + ".");
                }
            } else if(args[0].equals("grant")) {
                if(hasPerm(Perm.MONEY_ADMIN) && (target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.credit(amount);
                    player.sendMessage(Color.MESSAGE + target.getNick() + "'s account has been credited " + amount + " " + Config.currencies + ".");
                }
            } else if(args[0].equals("pay")) {
                if((target = getMatchingPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1 && canAfford(amount) && notSelf(target)) {
                    target.credit(amount);
                    player.debit(amount);
                    player.sendMessage(Color.MESSAGE.toString() + amount + " " + Config.currencies + " have been sent to " + target.getNick() + ".");
                    target.sendMessage(Color.MESSAGE + player.getNick() + " has just paid you " + amount + " " + Config.currencies + ".");
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
    /**
     * @inheritDoc
     */
    @Override
    public int compare(SaveablePlayer player1, SaveablePlayer player2) {
        return player2.getMoney() - player1.getMoney();
    }
}

