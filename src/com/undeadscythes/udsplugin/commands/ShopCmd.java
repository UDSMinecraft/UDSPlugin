package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.inventory.*;

/**
 * Shop related commands.
 * @author UndeadScythes
 */
public class ShopCmd extends AbstractPlayerCommand {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        Region shop;
        SaveablePlayer target;
        int price;
        if(args.length == 0) {
            if( (shop = getShop()) != null && notJailed() && notPinned()) {
                player.teleport(shop.getWarp());
            }
        } else if(args.length == 1) {
            if(args[0].equals("make")) {
                if(hasPerm(Perm.SHOP_ADMIN)) {
                    player.performCommand("region set " + nextShopName() + " shop");
                }
            } else if(args[0].equals("clear")) {
                if((shop = getShop()) != null) {
                    UDSPlugin.getRegions().replace(shop.getName(), nextShopName(), shop);
                    UDSPlugin.getShops().replace(shop.getName(), nextShopName(), shop);
                    shop.clearMembers();
                    shop.changeOwner(null);
                    player.sendMessage(Color.MESSAGE + "Shop put back up for sale.");
                    player.credit(Config.shopCost / 2);
                }
            } else if(args[0].equals("set")) {
                if((shop = getShop()) != null) {
                    shop.setWarp(player.getLocation());
                    player.sendMessage(Color.MESSAGE + "Shop warp point set.");
                }
            } else if(args[0].equals("workers")) {
                String message = "";
                for(Region otherShop : UDSPlugin.getShops().values()) {
                    if(otherShop.hasMember(player)) {
                        message = message.concat(otherShop.getOwner().getNick() + ", ");
                    }
                    if(!message.isEmpty()) {
                        player.sendMessage(Color.MESSAGE + "You work for:");
                        player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                    }
                    message = "";
                    if((shop = UDSPlugin.getShops().get(player.getName() + "shop")) != null) {
                        for(SaveablePlayer member : shop.getMembers()) {
                            message = message.concat(member.getNick() + ", ");
                        }
                    }
                    if(!message.equals("")) {
                        player.sendMessage(Color.MESSAGE + "Your workers are:");
                        player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                    } else {
                        player.sendMessage(Color.MESSAGE + "You have no workers.");
                    }
                }
            } else if(args[0].equals("buy")) {
                if((shop = getContainingShop()) != null && canAfford(Config.shopCost) && isEmptyShop(shop)) {
                    player.debit(Config.shopCost);
                    UDSPlugin.getRegions().replace(shop.getName(), player.getName() + "shop", shop);
                    UDSPlugin.getShops().replace(shop.getName(), player.getName() + "shop", shop);
                    shop.changeName(player.getName() + "shop");
                    player.sendMessage(Color.MESSAGE + "Shop bought.");
                }
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
            } else if(args[0].equals("help")) {
                sendHelp(1);
            } else if((target = getMatchingPlayer(args[0])) != null && (shop = getShop(target)) != null && notJailed() && notPinned()) {
                player.teleport(shop.getWarp());
            }
        } else if(args.length == 2) {
            if(args[0].equals("hire")) {
                if((target = getMatchingPlayer(args[1])) != null && (shop = getShop()) != null) {
                    shop.addMember(target);
                    player.sendMessage(Color.MESSAGE + target.getNick() + " has been added as your worker.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You have been added as " + player.getNick() + "'s worker.");
                    }
                }
            } else if(args[0].equals("fire")) {
                if((target = getMatchingPlayer(args[1])) != null && (shop = getShop()) != null && isWorker(target, shop)) {
                    shop.delMember(target);
                    player.sendMessage(Color.MESSAGE + target.getNick() + " is no longer your worker.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You are no longer " + player.getNick() + "'s worker.");
                    }
                }
            } else {
                subCmdHelp(args);
            }
        } else if(numArgsHelp(3)) {
            if(args[0].equals("shop")) {
                if((getShop()) != null && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && (price = parseInt(args[2])) != -1) {
                    player.sendMessage(Message.REQUEST_SENT);
                    target.sendMessage(Color.MESSAGE + player.getNick() + " wants to sell you their shop for " + price + " " + Config.currencies + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                    UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.RequestType.SHOP, price, target));
                }
            } else {
                subCmdHelp();
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
