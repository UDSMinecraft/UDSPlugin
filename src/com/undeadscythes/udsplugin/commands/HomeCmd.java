package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.util.*;

/**
 * @author UndeadScythes
 */
public class HomeCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Region home;
        Member target;
        Member onlineTarget;
        int price;
        if(args.length == 0) {
            if((home = getHome()) != null && notJailed() && notPinned()) {
                player().teleport(home.getWarp());
            }
        } else if(args.length == 1) {
            if(subCmdEquals("make")) {
                if(canAfford(Config.HOME_COST) && hasNoHome()) {
                    final Vector min = player().getLocation().add(-10, -12, -10).toVector();
                    final Vector max = player().getLocation().add(10, 28, 10).toVector();
                    home = new Region(player().getName() + "home", min, max, player().getLocation(), player(), "", RegionType.HOME);
                    if(noOverlaps(home)) {
                        player().debit(Config.HOME_COST);
                        RegionUtils.addRegion(home);
                        home.placeCornerMarkers();
                        player().sendNormal("Home area protected.");
                    }
                }
            } else if(subCmdEquals("clear")) {
                if((home = getHome()) != null) {
                    RegionUtils.removeRegion(home);
                    player().sendNormal("Home protection removed.");
                }
            } else if(subCmdEquals("power")) {
                if((home = getHome()) != null) {
                    player().sendNormal("Your home " + (home.toggleFlag(RegionFlag.POWER) ? "now" : "no longer") + " has power.");
                }
            } else if(subCmdEquals("set")) {
                if((home = getHome()) != null) {
                    home.setWarp(player().getLocation());
                    player().sendNormal("Home warp point set.");
                }
            } else if(subCmdEquals("roomies")) {
                String message = "";
                for(Region otherHome : RegionUtils.getRegions(RegionType.HOME)) {
                    if(otherHome.hasMember(player())) {
                        message = message.concat(otherHome.getOwner().getNick() + ", ");
                    }
                    if(!message.isEmpty()) {
                        player().sendNormal("You are room mates with:");
                        player().sendText(message.substring(0, message.length() - 2));
                    }
                    message = "";
                    if((home = RegionUtils.getRegion(RegionType.HOME, player().getName() + "home")) != null) {
                        for(Member member : home.getMembers()) {
                            message = message.concat(member.getNick() + ", ");
                        }
                    }
                    if(message.isEmpty()) {
                        player().sendNormal("You have no room mates.");
                    } else {
                        player().sendNormal("Your room mates are:");
                        player().sendText(message.substring(0, message.length() - 2));
                    }
                }
            } else if(subCmdEquals("lock")) {
                if((home = getHome()) != null) {
                    home.setFlag(RegionFlag.LOCK);
                    player().sendNormal("Your home is now locked.");
                }
            } else if(subCmdEquals("unlock")) {
                if((home = getHome()) != null) {
                    home.setFlag(RegionFlag.LOCK);
                    home.toggleFlag(RegionFlag.LOCK);
                    player().sendNormal("Your home is now unlocked.");
                }
            } else if(subCmdEquals("help")) {
                sendHelp(1);
            } else if((target = matchPlayer(args[0])) != null && (home = getHome(target)) != null && (isRoomie(home) || hasPerm(Perm.HOME_OTHER)) && notJailed() && notPinned()) {
                player().teleport(home.getWarp());
            }
        } else if(args.length == 2) {
            Direction direction;
            if(subCmdEquals("expand")) {
                if(hasPerm(Perm.HOME_EXPAND) && (home = getHome()) != null && canAfford(Config.EXPAND_COST) && (direction = getCardinalDirection(args[1])) != null) {
                    home.expand(direction, 1);
                    if(noOverlaps(home)) {
                        player().debit(Config.EXPAND_COST);
                        player().sendNormal("Your home has been expanded.");
                    } else {
                        home.expand(direction, -1);
                    }
                }
            } else if(subCmdEquals("boot")) {
                if((home = getHome()) != null && (onlineTarget = matchOnlinePlayer(args[1])) != null && isInHome(onlineTarget, home)) {
                    onlineTarget.teleport(UDSPlugin.getWorldSpawn(player().getWorld()));
                    onlineTarget.sendNormal(player().getNick() + " has booted you from their home.");
                    player().sendNormal(onlineTarget.getNick() + " has been booted.");
                }
            } else if(subCmdEquals("add")) {
                if((target = matchPlayer(args[1])) != null && (home = getHome()) != null) {
                    home.addMember(target);
                    player().sendNormal(target.getNick() + " has been added as your room mate.");
                    try {
                        PlayerUtils.getOnlinePlayer(target).sendNormal("You have been added as " + player().getNick() + "'s room mate.");
                    } catch (PlayerNotOnlineException ex) {}
                }
            } else if(subCmdEquals("kick")) {
                if((target = matchPlayer(args[1])) != null && (home = getHome()) != null && isRoomie(target, home)) {
                    home.delMember(target);
                    player().sendNormal(target.getNick() + " is no longer your room mate.");
                    try {
                        PlayerUtils.getOnlinePlayer(target).sendNormal("You are no longer " + player().getNick() + "'s room mate.");
                    } catch (PlayerNotOnlineException ex) {}
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(subCmdEquals("sell")) {
                if((getHome()) != null && (onlineTarget = matchOnlinePlayer(args[1])) != null && canRequest(onlineTarget) && (price = getInteger(args[2])) != -1) {
                    player().sendMessage(Message.REQUEST_SENT);
                    onlineTarget.sendNormal(player().getNick() + " wants to sell you their house for " + price + " " + Config.CURRENCIES + ".");
                    onlineTarget.sendMessage(Message.REQUEST_Y_N);
                    UDSPlugin.addRequest(onlineTarget.getName(), new Request(player(), RequestType.HOME, price, onlineTarget));
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
