package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Shop related commands.
 * @author UndeadScythes
 */
public class ShopCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(0, 3)) {
            Region shop;
            SaveablePlayer target;
            int price;
            if(args.length == 0 && (shop = hasShop()) != null && notJailed() && notPinned()) {
                player.teleport(shop.getWarp());
            } else if(args.length == 1) {
                if(args[0].equals("make") && hasPerm(Perm.SHOP_ADMIN)) {
                    player.performCommand("region set " + nextShopName() + " shop");
                } else if(args[0].equals("clear") && (shop = hasShop()) != null) {
                    UDSPlugin.getRegions().replace(shop.getName(), nextShopName(), shop);
                    UDSPlugin.getShops().replace(shop.getName(), nextShopName(), shop);
                    shop.clearMembers();
                    shop.changeOwner("");
                    player.sendMessage(Color.MESSAGE + "Shop put back up for sale.");
                    player.credit(Config.SHOP_COST / 2);
                } else if(args[0].equals("set") && (shop = hasShop()) != null) {
                    shop.setWarp(player.getLocation());
                    player.sendMessage(Color.MESSAGE + "Shop warp point set.");
                } else if(args[0].equals("workers")) {
                    String message = "";
                    for(Region otherShop : UDSPlugin.getShops().values()) {
                        if(otherShop.hasMember(player.getName())) {
                            message = message.concat(UDSPlugin.getPlayers().get(otherShop.getOwner()).getDisplayName() + ", ");
                        }
                        if(!message.isEmpty()) {
                            player.sendMessage(Color.MESSAGE + "You work for:");
                            player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                        }
                        message = "";
                        if((shop = UDSPlugin.getShops().get(player.getName() + "shop")) != null) {
                            for(String name : shop.getMembers()) {
                                message = message.concat(UDSPlugin.getPlayers().get(name).getDisplayName() + ", ");
                            }
                        }
                        if(!message.equals("")) {
                            player.sendMessage(Color.MESSAGE + "Your workers are:");
                            player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                        } else {
                            player.sendMessage(Color.MESSAGE + "You have no workers.");
                        }
                    }
                } else if(args[0].equals("buy") && (shop = isInShop()) != null && canAfford(Config.SHOP_COST) && isEmpty(shop)) {
                    player.debit(Config.SHOP_COST);
                    UDSPlugin.getRegions().replace(shop.getName(), player.getName() + "shop", shop);
                    UDSPlugin.getShops().replace(shop.getName(), player.getName() + "shop", shop);
                    shop.changeName(player.getName() + "shop");
                    player.sendMessage(Color.MESSAGE + "Shop bought.");
                } else if(args[0].equals("sign")) {
                    player.sendMessage(Color.MESSAGE + "Correct shop sign format:");
                    player.sendMessage(Color.ITEM + "Line 1 - " + Color.TEXT + "Leave this line blank.\n");
                    player.sendMessage(Color.ITEM + "Line 2 - " + Color.TEXT + "shop\n");
                    player.sendMessage(Color.ITEM + "Line 3 - " + Color.TEXT + "<item>\n");
                    player.sendMessage(Color.ITEM + "Line 4 - " + Color.TEXT + "<buy price>:<sell price>");
                    player.sendMessage(Color.TEXT + "The buy price is how much people pay to buy from the shop.");
                    player.sendMessage(Color.TEXT + "The sell price is how much people pay to sell to the shop.");
                } else if(args[0].equals("item")) {
                    ItemStack item = player.getItemInHand();
                    player.sendMessage(Color.MESSAGE + item.getType().name() + " - " + Color.TEXT + item.getTypeId() + ":" + item.getData().getData());
                } else if((target = matchesPlayer(args[0])) != null && (shop = hasShop(target)) != null && notJailed() && notPinned()) {
                    player.teleport(shop.getWarp());
                }
            } else if(args.length == 2) {
                if(args[0].equals("hire") && (target = matchesPlayer(args[1])) != null && (shop = hasShop()) != null) {
                    shop.addMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been added as your worker.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You have been added as " + player.getDisplayName() + "'s worker.");
                    }
                } else if(args[0].equals("fire") && (target = matchesPlayer(args[1])) != null && (shop = hasShop()) != null && isWorker(target, shop)) {
                    shop.delMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " is no longer your worker.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You are no longer " + player.getDisplayName() + "'s worker.");
                    }
                }
            } else if(args[0].equals("shop") && (hasShop()) != null && (target = matchesPlayer(args[1])) != null && isOnline(target) && (price = parseInt(args[2])) != -1) {
                player.sendMessage(Message.REQUEST_SENT);
                target.sendMessage(Color.MESSAGE + player.getDisplayName() + " wants to sell you their shop for " + price + " " + Config.CURRENCIES + ".");
                target.sendMessage(Message.REQUEST_Y_N);
                UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.SHOP, price, target));
            }
        }
    }

    private String nextShopName() {
        int high = 0;
        for(Region shop : UDSPlugin.getShops().values()) {
            if(shop.getName().startsWith("shop")) {
                if(Integer.parseInt(shop.getName().replace("shop", "")) > high) {
                    high = Integer.parseInt(shop.getName().replace("shop", ""));
                }
            }
        }
        return "shop" + high + 1;
    }
}
