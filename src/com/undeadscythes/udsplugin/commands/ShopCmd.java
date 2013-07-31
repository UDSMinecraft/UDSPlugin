package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.inventory.*;

/**
 * Shop related commands.
 * @author UndeadScythes
 */
public class ShopCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        Region shop;
        SaveablePlayer target;
        int price;
        if(args.length == 0) {
            if((shop = getShop()) != null && notJailed() && notPinned()) {
                player.teleport(shop.getWarp());
            }
        } else if(args.length == 1) {
            if(subCmd.equals("make")) {
                if(hasPerm(Perm.SHOP_ADMIN)) {
                    player.performCommand("region set " + nextShopName() + " shop");
                }
            } else if(subCmd.equals("clear")) {
                if((shop = getShop()) != null) {
                    RegionUtils.renameRegion(shop, nextShopName());
                    shop.clearMembers();
                    shop.changeOwner(null);
                    player.sendNormal("Shop put back up for sale.");
                    player.credit(UDSPlugin.getConfigInt(ConfigRef.SHOP_COST) / 2);
                }
            } else if(subCmd.equals("set")) {
                if((shop = getShop()) != null) {
                    shop.setWarp(player.getLocation());
                    player.sendNormal("Shop warp point set.");
                }
            } else if(subCmd.equals("workers")) {
                String message = "";
                for(Region otherShop : RegionUtils.getRegions(RegionType.SHOP)) {
                    if(otherShop.hasMember(player)) {
                        message = message.concat(otherShop.getOwner().getNick() + ", ");
                    }
                    if(!message.isEmpty()) {
                        player.sendNormal("You work for:");
                        player.sendText(message.substring(0, message.length() - 2));
                    }
                    message = "";
                    if((shop = RegionUtils.getRegion(RegionType.SHOP, player.getName() + "shop")) != null) {
                        for(SaveablePlayer member : shop.getMembers()) {
                            message = message.concat(member.getNick() + ", ");
                        }
                    }
                    if(!message.equals("")) {
                        player.sendNormal("Your workers are:");
                        player.sendText(message.substring(0, message.length() - 2));
                    } else {
                        player.sendNormal("You have no workers.");
                    }
                }
            } else if(subCmd.equals("buy")) {
                if((shop = getContainingShop()) != null && canAfford(UDSPlugin.getConfigInt(ConfigRef.SHOP_COST)) && isEmptyShop(shop)) {
                    player.debit(UDSPlugin.getConfigInt(ConfigRef.SHOP_COST));
                    RegionUtils.renameRegion(shop, player.getName() + "shop");
                    shop.changeName(player.getName() + "shop");
                    player.sendNormal("Shop bought.");
                }
            } else if(subCmd.equals("sign")) {
                player.sendNormal("Correct shop sign format:");
                player.sendListItem("Line 1 - ", "The word '[shop]' with square brackets.");
                player.sendListItem("Line 2 - ", "The price of one item.");
                player.sendListItem("Lines 3 & 4 - ", "Anything else you want to write.");
                player.sendListItem("Example:", "");
                player.sendText("[shop]");
                player.sendText("50");
                player.sendText("Lovely home");
                player.sendText("cooked treats!");
            } else if(subCmd.equals("item")) {
                final ItemStack item = player.getItemInHand();
                player.sendNormal(item.getType().name() + " - " + Color.TEXT + item.getTypeId() + ":" + item.getData().getData());
            } else if("changes".equals(subCmd)) {
                player.sendNormal("Changes to the shop system:");
                player.sendText("Shops no longer buy items, to sell items buy your own shop.");
                player.sendText("You will pay for the items you take when you close the chest.");
                player.sendText("The chest doesn't know which items are yours so don't put your items in unless you want to pay to get them back.");
                player.sendText("Shops are not item specific, pay one price for whatever items are in a chest.");
                player.sendText("Check out /shop sign to see how to format new shop signs.");
            } else if(subCmd.equals("help")) {
                sendHelp(1);
            } else if((target = getMatchingPlayer(args[0])) != null && (shop = getShop(target)) != null && notJailed() && notPinned()) {
                player.teleport(shop.getWarp());
            }
        } else if(args.length == 2) {
            if(subCmd.equals("hire")) {
                if((target = getMatchingPlayer(args[1])) != null && (shop = getShop()) != null) {
                    shop.addMember(target);
                    player.sendNormal(target.getNick() + " has been added as your worker.");
                    if(target.isOnline()) {
                        target.sendNormal("You have been added as " + player.getNick() + "'s worker.");
                    }
                }
            } else if(subCmd.equals("fire")) {
                if((target = getMatchingPlayer(args[1])) != null && (shop = getShop()) != null && isWorker(target, shop)) {
                    shop.delMember(target);
                    player.sendNormal(target.getNick() + " is no longer your worker.");
                    if(target.isOnline()) {
                        target.sendNormal("You are no longer " + player.getNick() + "'s worker.");
                    }
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(subCmd.equals("sell")) {
                if((getShop()) != null && (target = getMatchingPlayer(args[1])) != null && canRequest(target) && isOnline(target) && (price = parseInt(args[2])) != -1) {
                    player.sendMessage(Message.REQUEST_SENT);
                    target.sendNormal(player.getNick() + " wants to sell you their shop for " + price + " " + UDSPlugin.getConfigInt(ConfigRef.CURRENCIES) + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                    UDSPlugin.addRequest(target.getName(), new Request(player, RequestType.SHOP, price, target));
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private String nextShopName() {
        int high = 0;
        for(Region shop : RegionUtils.getRegions(RegionType.SHOP)) {
            if(shop.getName().startsWith("shop") && Integer.parseInt(shop.getName().replace("shop", "")) > high) {
                high = Integer.parseInt(shop.getName().replace("shop", ""));
            }
        }
        return "shop" + (high + 1);
    }
}
