package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Various money handling commands.
 * @author UndeadScythes
 */
public class MoneyCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        if(argsMoreLessInc(0, 3)) {
            if(args.length == 0) {
                player.sendMessage(Color.MESSAGE + "You have " + player.getMoney() + " credits.");
            } else if(args.length == 1) {
                if(args[0].equals("prices")) {
                    player.sendMessage(Color.MESSAGE + "--- Server Prices ---");
                    player.sendMessage(Color.ITEM + "Build rights: " + Color.TEXT + Config.BUILD_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "Map of spawn: " + Color.TEXT + Config.MAP_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "Home protection: " + Color.TEXT + Config.HOME_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "City shop: " + Color.TEXT + Config.SHOP_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "VIP rank: " + Color.TEXT + Config.VIP_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "Clan cost: " + Color.TEXT + Config.CLAN_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "Clan base cost: " + Color.TEXT + Config.BASE_COST + " " + Config.CURRENCIES);
                    player.sendMessage(Color.ITEM + "City cost: " + Color.TEXT + Config.CITY_COST + " " + Config.CURRENCIES);
                } else if(args[0].equals("rank")) {
                    ArrayList<SaveablePlayer> players = UDSPlugin.getPlayers().getSortedValues(new SortByMoney());
                    int printed = 0;
                    int rank = 0;
                    for(SaveablePlayer ranker : players) {
                        if(ranker.getRank().compareTo(Rank.MOD) < 0 && printed < 5) {
                            player.sendMessage(Color.TEXT.toString() + (printed + 1) + ": " + ranker.getRank().color() + ranker.getDisplayName() + ", " + Color.TEXT + ranker.getMoney() + " " + Config.CURRENCIES);
                            printed++;
                            if(!ranker.equals(player) && rank == 0) {
                                rank++;
                            }
                        } else if(rank > 0 || player.getRank().compareTo(Rank.MOD) >= 0) {
                            break;
                        }
                    }
                    if(rank > 5 && player.getRank().compareTo(Rank.MOD) < 0) {
                        player.sendMessage(Color.MESSAGE + "Your rank is " + rank + ".");
                    }
                } else if((target = matchesPlayer(args[0])) != null && notSelf(target) && hasPerm(Perm.MONEY_OTHER)) {
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has " + target.getMoney() + " " + Config.CURRENCIES + ".");
                }
            } else if(args.length == 3) {
                int amount;
                if(args[0].equals("set") && hasPerm(Perm.MONEY_ADMIN) && (target = matchesPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.setMoney(amount);
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + "'s account has been set to " + amount + " " + Config.CURRENCIES + ".");
                } else if(args[0].equals("grant") && hasPerm(Perm.MONEY_ADMIN) && (target = matchesPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1) {
                    target.credit(amount);
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + "'s account has been credited " + amount + " " + Config.CURRENCIES + ".");
                } else if(args[0].equals("pay") && (target = matchesPlayer(args[1])) != null && (amount = parseInt(args[2])) != -1 && canAfford(amount) && notSelf(target)) {
                    target.credit(amount);
                    player.debit(amount);
                    player.sendMessage(Color.MESSAGE.toString() + amount + " " + Config.CURRENCIES + " have been sent to " + target.getDisplayName() + ".");
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has just paid you " + amount + " " + Config.CURRENCIES + ".");
                }
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

