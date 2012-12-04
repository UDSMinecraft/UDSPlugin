package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.Region.RegionType;
import com.undeadscythes.udsplugin.*;

/**
 * Region related commands.
 * @author UndeadScythes
 */
public class RegionCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(args.length == 1) {
            if(args[0].equals("vert")) {
                vert();
            } else if(args[0].equals("list")) {
                list(RegionType.NORMAL);
            } else if(args[0].equals("flag")) {
                flagList();
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if(args[0].equals("del")) {
                del();
            } else if(args[0].equals("list")) {
                final RegionType type = getRegionType(args[1]);
                if(type != null) {
                    list(type);
                }
            } else if(args[0].equals("tp")) {
                tp();
            } else if(args[0].equals("info")) {
                info();
            } else if(args[0].equals("reset")) {
                reset();
            } else if(args[0].equals("select")) {
                select();
            } else if(args[0].equals("set")) {
                set(RegionType.NORMAL);
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(args[0].equals("addmember")) {
                addMember();
            } else if(args[0].equals("delmember")) {
                delMember();
            } else if(args[0].equals("owner")) {
                owner();
            } else if(args[0].equals("flag")) {
                flag();
            } else if(args[0].equals("rename")) {
                rename();
            } else if(args[0].equals("set")) {
                final RegionType type = getRegionType(args[2]);
                if(type != null) {
                    set(type);
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private void vert() {
        final WESession session = getSession();
        if(session != null && hasTwoPoints(session)) {
            session.vert();
            player.sendMessage(Color.MESSAGE + "Region extended from bedrock to build limit.");
        }
    }

    private void list(final RegionType type) {
        String list = "";
        for(Region test : UDSPlugin.getRegions().values()) {
            if(test.getType().equals(type)) {
                list = list.concat(test.getName() + ", ");
            }
        }
        if(list.isEmpty()) {
            player.sendMessage(Color.MESSAGE + "There are no " + type.name().toLowerCase() + " regions.");
        } else {
            player.sendMessage(Color.MESSAGE + type.name().toLowerCase() + " Regions:");
            player.sendMessage(Color.TEXT + list.substring(0, list.length() - 2));
        }
    }

    private void flagList() {
        player.sendMessage(Color.MESSAGE + "Available region flags:");
        String message = "";
        for(RegionFlag test : RegionFlag.values()) {
            message = message.concat(test.name() + ", ");
        }
        player.sendMessage(Color.TEXT + message.substring(0, message.length() - 2));
    }

    private void flag() {
        final Region region = getRegion(args[1]);
        final RegionFlag flag = getFlag(args[2]);
        if(region != null && flag != null) {
            player.sendMessage(Color.MESSAGE + region.getName() + " flag " + flag.toString() + " now set to " + region.toggleFlag(flag) + ".");
        }
    }

    private void del() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            UDSPlugin.getRegions().remove(region.getName());
            player.sendMessage(Color.MESSAGE + "Region deleted.");
        }
    }

    private void tp() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            player.teleport(region.getWarp());
        }
    }

    private void info() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            region.sendInfo(player);
        }
    }

    private void reset() {
        final WESession session = getSession();
        final Region region = getRegion(args[1]);
        if(session != null && hasTwoPoints(session) && region != null) {
            region.changeV(session.getV1(), session.getV2());
            player.sendMessage(Color.MESSAGE + "Region reset with new points.");
        }
    }

    private void select() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            final WESession session = player.forceSession();
            session.setVPair(region.getV1(), region.getV2(), region.getWorld());
            player.sendMessage(Color.MESSAGE + "Points set. " + session.getVolume() + " blocks selected.");
        }
    }

    private void set(final RegionType type) {
        final WESession session = getSession();
        if(session != null && hasTwoPoints(session) && notRegion(args[1]) && noCensor(args[1])) {
            final Region region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), null, "", type);
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
    }

    private void addMember() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = getMatchingPlayer(args[2]);
        if(region != null && target != null) {
            region.addMember(target);
            player.sendMessage(Color.MESSAGE + target.getNick() + " add to region " + region.getName() + ".");
        }
    }

    private void delMember() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = getMatchingPlayer(args[2]);
        if(region != null && target != null) {
            region.delMember(target);
            player.sendMessage(Color.MESSAGE + target.getNick() + " removed from region " + region.getName() + ".");
        }
    }

    private void owner() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = getMatchingPlayer(args[2]);
        if(region != null && target != null) {
            region.changeOwner(target);
            player.sendMessage(Color.MESSAGE + target.getNick() + " made owner of region " + region.getName() + ".");
        }
    }

    private void rename() {
        final Region region = getRegion(args[1]);
        if(region != null && noCensor(args[1]) && notRegion(args[2])) {
            final String oldName = region.getName();
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
    }
}
