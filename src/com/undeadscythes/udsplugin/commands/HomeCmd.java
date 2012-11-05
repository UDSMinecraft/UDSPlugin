package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.LoadableLocation.Direction;
import com.undeadscythes.udsplugin.Region.Flag;
import com.undeadscythes.udsplugin.*;
import org.bukkit.util.*;

/**
 * Home region related commands.
 * @author UndeadScythes
 */
public class HomeCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(0, 2)) {
            Region home;
            SaveablePlayer target;
            int price;
            if(args.length == 0 && (home = hasHome()) != null && notJailed() && notPinned()) {
                player.teleport(home.getWarp());
            } else if(args.length == 1) {
                if(args[0].equals("make") && canAfford(Config.HOME_COST) && noHome()) {
                    Vector min = player.getLocation().add(-10, 28, -10).toVector();
                    Vector max = player.getLocation().add(10, 12, 10).toVector();
                    home = new Region(player.getName() + "home", min, max, player.getLocation(), player.getName(), "", Region.Type.HOME);
                    if(noOverlaps(home)) {
                        player.debit(Config.HOME_COST);
                        UDSPlugin.getRegions().put(home.getName(), home);
                        UDSPlugin.getHomes().put(home.getName(), home);
                        home.placeCornerMarkers();
                        player.sendMessage(Color.MESSAGE + "Home area protected.");
                    }
                } else if(args[0].equals("clear") && (home = hasHome()) != null) {
                    UDSPlugin.getRegions().remove(home.getName());
                    UDSPlugin.getHomes().remove(home.getName());
                    player.sendMessage(Color.MESSAGE + "Home protection removed.");
                } else if(args[0].equals("set") && (home = hasHome()) != null) {
                    home.setWarp(player.getLocation());
                    player.sendMessage(Color.MESSAGE + "Home warp point set.");
                } else if(args[0].equals("roomies")) {
                    String message = "";
                    for(Region otherHome : UDSPlugin.getHomes().values()) {
                        if(otherHome.hasMember(player.getName())) {
                            message = message.concat(UDSPlugin.getPlayers().get(otherHome.getOwner()).getDisplayName() + ", ");
                        }
                        if(!message.isEmpty()) {
                            player.sendMessage(Color.MESSAGE + "You are room mates with:");
                            player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                        }
                        message = "";
                        if((home = UDSPlugin.getHomes().get(player.getName() + "home")) != null) {
                            for(String name : home.getMembers()) {
                                message = message.concat(UDSPlugin.getPlayers().get(name).getDisplayName() + ", ");
                            }
                        }
                        if(!message.equals("")) {
                            player.sendMessage(Color.MESSAGE + "Your room mates are:");
                            player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
                        } else {
                            player.sendMessage(Color.MESSAGE + "You have no room mates.");
                        }
                    }
                } else if(args[0].equals("lock") && (home = hasHome()) != null) {
                    home.setFlag(Flag.LOCK_DOORS);
                    player.sendMessage(Color.MESSAGE + "Your home is now locked.");
                } else if(args[0].equals("unlock") && (home = hasHome()) != null) {
                    home.setFlag(Flag.LOCK_DOORS);
                    home.toggleFlag(Flag.LOCK_DOORS);
                    player.sendMessage(Color.MESSAGE + "Your home is now unlocked.");
                } else if((target = matchesPlayer(args[0])) != null && (home = hasHome(target)) != null && (isRoomie(home) || hasPerm(Perm.HOME_OTHER)) && notJailed() && notPinned()) {
                    player.teleport(home.getWarp());
                }
            } else if(args.length == 2) {
                Direction direction;
                if(args[0].equals("expand") && (home = hasHome()) != null && canAfford(Config.EXPAND_COST) && (direction = matchesCardinalDirection(args[1])) != null) {
                    home.expand(direction, 1);
                    if(noOverlaps(home)) {
                        player.debit(Config.EXPAND_COST);
                        player.sendMessage(Color.MESSAGE + "Your home ahs been expanded.");
                    } else {
                        home.expand(direction, -1);
                    }
                } else if(args[0].equals("boot") && (home = hasHome()) != null && (target = matchesPlayer(args[1])) != null && isOnline(target) && isInHome(target, home)) {
                    target.teleport(player.getWorld().getSpawnLocation());
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has booted you from their home.");
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been booted.");
                } else if(args[0].equals("add") && (target = matchesPlayer(args[1])) != null && (home = hasHome()) != null) {
                    home.addMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been added as your room mate.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You have been added as " + player.getDisplayName() + "'s room mate.");
                    }
                } else if(args[0].equals("kick") && (target = matchesPlayer(args[1])) != null && (home = hasHome()) != null && isRoomie(target, home)) {
                    home.delMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " is no longer your room mate.");
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You are no longer " + player.getDisplayName() + "'s room mate.");
                    }
                }
            } else if((hasHome()) != null && (target = matchesPlayer(args[1])) != null && isOnline(target) && (price = parseInt(args[2])) != -1) {
                player.sendMessage(Message.REQUEST_SENT);
                target.sendMessage(Color.MESSAGE + player.getDisplayName() + " wants to sell you their house for " + price + " " + Config.CURRENCIES + ".");
                target.sendMessage(Message.REQUEST_Y_N);
                UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.HOME, price, target));
            }
        }
    }
}
