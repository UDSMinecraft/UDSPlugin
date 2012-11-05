package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Region.Flag;
import com.undeadscythes.udsplugin.Region.Type;
import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Description.
 * @author UndeadScythes
 */
public class RegionCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(1, 4)) {
            Session session;
            Region region;
            Region.Type type;
            SaveablePlayer target;
            Flag flag;
            if(args.length == 1 && args[0].equals("vert") && (session = hasSession()) != null && hasTwoPoints(session)) {
                session.vert();
                player.sendMessage(Color.MESSAGE + "Region extended from bedrock to build limit.");
            } else if(args.length == 2) {
                if(args[0].equals("del") && (region = matchesRegion(args[1])) != null) {
                    UDSPlugin.getRegions().remove(region.getName());
                    player.sendMessage(Color.MESSAGE + "Region deleted.");
                } else if(args[0].equals("list") && (type = matchesRegionType(args[1])) != null) {
                    String list = "";
                    for(Region test : UDSPlugin.getRegions().values()) {
                        if(test.getType().equals(type)) {
                            list = list.concat(test.getName() + ", ");
                        }
                    }
                    if(!list.isEmpty()) {
                        player.sendMessage(Color.MESSAGE + type.name().toLowerCase() + " Regions:");
                        player.sendMessage(Color.TEXT + list.substring(0, list.length() - 2));
                    } else {
                        player.sendMessage(Color.MESSAGE + "There are no " + type.name().toLowerCase() + " regions.");
                    }
                } else if(args[0].equals("tp") && (region = matchesRegion(args[1])) != null) {
                    player.teleport(region.getWarp());
                } else if(args[0].equals("info") && (region = matchesRegion(args[1])) != null) {
                    player.sendMessage(Color.MESSAGE + "Region " + region.getName() + " info:");
                    player.sendMessage(Color.TEXT + "Owner: " + region.getOwner());
                    player.sendMessage(Color.TEXT + "Members: " + StringUtils.join(region.getMembers(), ", "));
                    player.sendMessage(Color.TEXT + "Type: " + region.getType().toString());
                    String flags = "";
                    for(Flag test : region.getFlags()) {
                        flags = flags.concat(test.toString() + ", ");
                    }
                    if(!flags.isEmpty()) {
                        player.sendMessage(Color.TEXT + "Flags: " + flags.substring(0, flags.length() - 2));
                    }
                } else if(args[0].equals("reset") && (session = hasSession()) != null && hasTwoPoints(session) && (region = matchesRegion(args[1])) != null) {
                    region.changeV(session.getV1(), session.getV2());
                    player.sendMessage(Color.MESSAGE + "Region reset with new points.");
                } else if(args[0].equals("select") && (region = matchesRegion(args[1])) != null) {
                    session = player.forceSession();
                    session.setV1(region.getV1());
                    session.setV2(region.getV2());
                    player.sendMessage(Color.MESSAGE + "Points set. " + session.getVolume() + " blocks selected.");
                } else if(args[0].equals("set") && (session = hasSession()) != null && hasTwoPoints(session) && noRegion(args[1]) && noCensor(args[1])) {
                    region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), "", "", Type.ARBITRARY);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    player.sendMessage(Color.MESSAGE + "Region " + region.getName() + " set.");
                }
            } else if(args.length == 3) {
                if(args[0].equals("addmember") && (region = matchesRegion(args[1])) != null && (target = matchesPlayer(args[2])) != null) {
                    region.addMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " add to region " + region.getName() + ".");
                } else if(args[0].equals("delmember") && (region = matchesRegion(args[1])) != null && (target = matchesPlayer(args[2])) != null) {
                    region.delMember(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " removed from region " + region.getName() + ".");
                } else if(args[0].equals("owner") && (region = matchesRegion(args[1])) != null && (target = matchesPlayer(args[2])) != null) {
                    region.changeOwner(target.getName());
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " made owner of region " + region.getName() + ".");
                } else if(args[0].equals("flag") && (region = matchesRegion(args[1])) != null && (flag = matchesFlag(args[2])) != null) {
                    player.sendMessage(Color.MESSAGE + region.getName() + " flag " + flag.toString() + " now set to " + region.toggleFlag(flag) + ".");
                } else if(args[0].equals("rename") && (region = matchesRegion(args[1])) != null && noCensor(args[1]) && noRegion(args[2])) {
                    String oldName = region.getName();
                    UDSPlugin.getRegions().remove(oldName);
                    region.changeName(args[2]);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    if(region.getType().equals(Type.ARENA)) {
                        UDSPlugin.getArenas().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(Type.BASE)) {
                        UDSPlugin.getBases().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(Type.CITY)) {
                        UDSPlugin.getCities().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(Type.HOME)) {
                        UDSPlugin.getHomes().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(Type.QUARRY)) {
                        UDSPlugin.getQuarries().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(Type.SHOP)) {
                        UDSPlugin.getShops().replace(oldName, region.getName(), region);
                    }
                    player.sendMessage(Color.MESSAGE + "Region " + oldName + " renamed to " + region.getName());
                } else if(args[0].equals("set") && (session = hasSession()) != null && hasTwoPoints(session) && noRegion(args[1]) && noCensor(args[1]) && (type = matchesRegionType(args[2])) != null) {
                    region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), "", "", type);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    if(region.getType().equals(Type.ARENA)) {
                        UDSPlugin.getArenas().put(region.getName(), region);
                    } else if(region.getType().equals(Type.BASE)) {
                        UDSPlugin.getBases().put(region.getName(), region);
                    } else if(region.getType().equals(Type.CITY)) {
                        UDSPlugin.getCities().put(region.getName(), region);
                    } else if(region.getType().equals(Type.HOME)) {
                        UDSPlugin.getHomes().put(region.getName(), region);
                    } else if(region.getType().equals(Type.QUARRY)) {
                        UDSPlugin.getQuarries().put(region.getName(), region);
                    } else if(region.getType().equals(Type.SHOP)) {
                        UDSPlugin.getShops().put(region.getName(), region);
                    }
                    player.sendMessage(Color.MESSAGE + "Region " + region.getName() + " set.");
                }
            }
        }
    }
}
