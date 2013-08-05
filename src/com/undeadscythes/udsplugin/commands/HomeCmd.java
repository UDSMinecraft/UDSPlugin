package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.util.*;

/**
 * Home region related commands.
 * @author UndeadScythes
 */
public class HomeCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Region home;
        SaveablePlayer target;
        int price;
        if(args.length == 0) {
            if((home = hasHome()) != null && notJailed() && notPinned()) {
                player.teleport(home.getWarp());
            }
        } else if(args.length == 1) {
            if(subCmd.equals("make")) {
                if(canAfford(Config.HOME_COST) && noHome()) {
                    final Vector min = player.getLocation().add(-10, -12, -10).toVector();
                    final Vector max = player.getLocation().add(10, 28, 10).toVector();
                    home = new Region(player.getName() + "home", min, max, player.getLocation(), player, "", RegionType.HOME);
                    if(noOverlaps(home)) {
                        player.debit(Config.HOME_COST);
                        RegionUtils.addRegion(home);
                        home.placeCornerMarkers();
                        player.sendNormal("Home area protected.");
                    }
                }
            } else if(subCmd.equals("clear")) {
                if((home = hasHome()) != null) {
                    RegionUtils.removeRegion(home);
                    player.sendNormal("Home protection removed.");
                }
            } else if(subCmd.equals("power")) {
                if((home = hasHome()) != null) {
                    player.sendNormal("Your home " + (home.toggleFlag(RegionFlag.POWER) ? "now" : "no longer") + " has power.");
                }
            } else if(subCmd.equals("set")) {
                if((home = hasHome()) != null) {
                    home.setWarp(player.getLocation());
                    player.sendNormal("Home warp point set.");
                }
            } else if(subCmd.equals("roomies")) {
                String message = "";
                for(Region otherHome : RegionUtils.getRegions(RegionType.HOME)) {
                    if(otherHome.hasMember(player)) {
                        message = message.concat(otherHome.getOwner().getNick() + ", ");
                    }
                    if(!message.isEmpty()) {
                        player.sendNormal("You are room mates with:");
                        player.sendText(message.substring(0, message.length() - 2));
                    }
                    message = "";
                    if((home = RegionUtils.getRegion(RegionType.HOME, player.getName() + "home")) != null) {
                        for(SaveablePlayer member : home.getMembers()) {
                            message = message.concat(member.getNick() + ", ");
                        }
                    }
                    if(message.isEmpty()) {
                        player.sendNormal("You have no room mates.");
                    } else {
                        player.sendNormal("Your room mates are:");
                        player.sendText(message.substring(0, message.length() - 2));
                    }
                }
            } else if(subCmd.equals("lock")) {
                if((home = hasHome()) != null) {
                    home.setFlag(RegionFlag.LOCK);
                    player.sendNormal("Your home is now locked.");
                }
            } else if(subCmd.equals("unlock")) {
                if((home = hasHome()) != null) {
                    home.setFlag(RegionFlag.LOCK);
                    home.toggleFlag(RegionFlag.LOCK);
                    player.sendNormal("Your home is now unlocked.");
                }
            } else if(subCmd.equals("help")) {
                sendHelp(1);
            } else if((target = matchesPlayer(args[0])) != null && (home = hasHome(target)) != null && (isRoomie(home) || hasPerm(Perm.HOME_OTHER)) && notJailed() && notPinned()) {
                player.teleport(home.getWarp());
            }
        } else if(args.length == 2) {
            Direction direction;
            if(subCmd.equals("expand")) {
                if(hasPerm(Perm.HOME_EXPAND) && (home = hasHome()) != null && canAfford(Config.EXPAND_COST) && (direction = isCardinalDirection(args[1])) != null) {
                    home.expand(direction, 1);
                    if(noOverlaps(home)) {
                        player.debit(Config.EXPAND_COST);
                        player.sendNormal("Your home has been expanded.");
                    } else {
                        home.expand(direction, -1);
                    }
                }
            } else if(subCmd.equals("boot")) {
                if((home = hasHome()) != null && (target = matchesPlayer(args[1])) != null && isOnline(target) && isInHome(target, home)) {
                    target.teleport(player.getWorld().getSpawnLocation());
                    target.sendNormal(player.getNick() + " has booted you from their home.");
                    player.sendNormal(target.getNick() + " has been booted.");
                }
            } else if(subCmd.equals("add")) {
                if((target = matchesPlayer(args[1])) != null && (home = hasHome()) != null) {
                    home.addMember(target);
                    player.sendNormal(target.getNick() + " has been added as your room mate.");
                    if(target.isOnline()) {
                        target.sendNormal("You have been added as " + player.getNick() + "'s room mate.");
                    }
                }
            } else if(subCmd.equals("kick")) {
                if((target = matchesPlayer(args[1])) != null && (home = hasHome()) != null && isRoomie(target, home)) {
                    home.delMember(target);
                    player.sendNormal(target.getNick() + " is no longer your room mate.");
                    if(target.isOnline()) {
                        target.sendNormal("You are no longer " + player.getNick() + "'s room mate.");
                    }
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(subCmd.equals("sell")) {
                if((hasHome()) != null && (target = matchesPlayer(args[1])) != null && canRequest(target) && isOnline(target) && (price = isInteger(args[2])) != -1) {
                    player.sendMessage(Message.REQUEST_SENT);
                    target.sendNormal(player.getNick() + " wants to sell you their house for " + price + " " + Config.CURRENCIES + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                    UDSPlugin.addRequest(target.getName(), new Request(player, RequestType.HOME, price, target));
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
