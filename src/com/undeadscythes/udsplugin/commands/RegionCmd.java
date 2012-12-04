package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.Region.RegionType;
import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Region related commands.
 * @author UndeadScythes
 */
public class RegionCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        WESession session;
        Region region;
        Region.RegionType type;
        SaveablePlayer target;
        RegionFlag flag;
        if(args.length == 1) {
            if(args[0].equals("vert")) {
                if((session = getSession()) != null && hasTwoPoints(session)) {
                    session.vert();
                    player.sendMessage(Color.MESSAGE + "Region extended from bedrock to build limit.");
                }
            } else if(args[0].equals("list")) {
                String list = "";
                for(Region test : UDSPlugin.getRegions().values()) {
                    if(test.getType().equals(RegionType.NORMAL)) {
                        list = list.concat(test.getName() + ", ");
                    }
                }
                if(!list.isEmpty()) {
                    player.sendMessage(Color.MESSAGE + RegionType.NORMAL.name().toLowerCase() + " Regions:");
                    player.sendMessage(Color.TEXT + list.substring(0, list.length() - 2));
                } else {
                    player.sendMessage(Color.MESSAGE + "There are no " + RegionType.NORMAL.name().toLowerCase() + " regions.");
                }
            } else if(args[0].equals("flag")) {
                player.sendMessage(Color.MESSAGE + "Available region flags:");
                String message = "";
                for(RegionFlag test : RegionFlag.values()) {
                    message = message.concat(test.name() + ", ");
                }
                player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
            } else {
                subCmdHelp(args);
            }
        } else if(args.length == 2) {
            if(args[0].equals("del")) {
                if((region = getRegion(args[1])) != null) {
                    UDSPlugin.getRegions().remove(region.getName());
                    player.sendMessage(Color.MESSAGE + "Region deleted.");
                }
            } else if(args[0].equals("list")) {
                if((type = getRegionType(args[1])) != null) {
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
                }
            } else if(args[0].equals("tp")) {
                if((region = getRegion(args[1])) != null) {
                    player.teleport(region.getWarp());
                }
            } else if(args[0].equals("info")) {
                if((region = getRegion(args[1])) != null) {
                    region.sendInfo(player);
                }
            } else if(args[0].equals("reset")) {
                if((session = getSession()) != null && hasTwoPoints(session) && (region = getRegion(args[1])) != null) {
                    region.changeV(session.getV1(), session.getV2());
                    player.sendMessage(Color.MESSAGE + "Region reset with new points.");
                }
            } else if(args[0].equals("select")) {
                if((region = getRegion(args[1])) != null) {
                    session = player.forceSession();
                    session.setVPair(region.getV1(), region.getV2(), region.getWorld());
                    player.sendMessage(Color.MESSAGE + "Points set. " + session.getVolume() + " blocks selected.");
                }
            } else if(args[0].equals("set")) {
                if((session = getSession()) != null && hasTwoPoints(session) && notRegion(args[1]) && noCensor(args[1])) {
                    region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), null, "", RegionType.NORMAL);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    player.sendMessage(Color.MESSAGE + "Region " + region.getName() + " set.");
                }
            } else {
                subCmdHelp(args);
            }
        } else if(numArgsHelp(3)) {
            if(args[0].equals("addmember")) {
                if((region = getRegion(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    region.addMember(target);
                    player.sendMessage(Color.MESSAGE + target.getNick() + " add to region " + region.getName() + ".");
                }
            } else if(args[0].equals("delmember")) {
                if((region = getRegion(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    region.delMember(target);
                    player.sendMessage(Color.MESSAGE + target.getNick() + " removed from region " + region.getName() + ".");
                }
            } else if(args[0].equals("owner")) {
                if((region = getRegion(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    region.changeOwner(target);
                    player.sendMessage(Color.MESSAGE + target.getNick() + " made owner of region " + region.getName() + ".");
                }
            } else if(args[0].equals("flag")) {
                if((region = getRegion(args[1])) != null && (flag = getFlag(args[2])) != null) {
                    player.sendMessage(Color.MESSAGE + region.getName() + " flag " + flag.toString() + " now set to " + region.toggleFlag(flag) + ".");
                }
            } else if(args[0].equals("rename")) {
                if((region = getRegion(args[1])) != null && noCensor(args[1]) && notRegion(args[2])) {
                    String oldName = region.getName();
                    UDSPlugin.getRegions().remove(oldName);
                    region.changeName(args[2]);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    if(region.getType().equals(RegionType.ARENA)) {
                        UDSPlugin.getArenas().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(RegionType.BASE)) {
                        UDSPlugin.getBases().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(RegionType.CITY)) {
                        UDSPlugin.getCities().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(RegionType.HOME)) {
                        UDSPlugin.getHomes().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(RegionType.QUARRY)) {
                        UDSPlugin.getQuarries().replace(oldName, region.getName(), region);
                    } else if(region.getType().equals(RegionType.SHOP)) {
                        UDSPlugin.getShops().replace(oldName, region.getName(), region);
                    }
                    player.sendMessage(Color.MESSAGE + "Region " + oldName + " renamed to " + region.getName());
                }
            } else if(args[0].equals("set")) {
                if((session = getSession()) != null && hasTwoPoints(session) && notRegion(args[1]) && noCensor(args[1]) && (type = getRegionType(args[2])) != null) {
                    region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), null, "", type);
                    UDSPlugin.getRegions().put(region.getName(), region);
                    if(region.getType().equals(RegionType.ARENA)) {
                        UDSPlugin.getArenas().put(region.getName(), region);
                    } else if(region.getType().equals(RegionType.BASE)) {
                        UDSPlugin.getBases().put(region.getName(), region);
                    } else if(region.getType().equals(RegionType.CITY)) {
                        UDSPlugin.getCities().put(region.getName(), region);
                    } else if(region.getType().equals(RegionType.HOME)) {
                        UDSPlugin.getHomes().put(region.getName(), region);
                    } else if(region.getType().equals(RegionType.QUARRY)) {
                        UDSPlugin.getQuarries().put(region.getName(), region);
                    } else if(region.getType().equals(RegionType.SHOP)) {
                        UDSPlugin.getShops().put(region.getName(), region);
                    }
                    player.sendMessage(Color.MESSAGE + "Region " + region.getName() + " set.");
                }
            } else {
                subCmdHelp();
            }
        }
    }
}
