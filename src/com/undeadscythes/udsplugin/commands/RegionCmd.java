package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Region related commands.
 * @author UndeadScythes
 */
public class RegionCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(args.length == 1) {
            if(subCmd.equals("vert")) {
                vert();
            } else if(subCmd.equals("list")) {
                list(RegionType.GENERIC);
            } else if(subCmd.equals("type")) {
                showTypes();
            } else if(subCmd.equals("flag")) {
                flagList();
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if(subCmd.equals("del")) {
                del();
            } else if(subCmd.equals("list")) {
                list(args[1]);
            } else if(subCmd.equals("tp")) {
                tp();
            } else if(subCmd.equals("info")) {
                info();
            } else if(subCmd.equals("reset")) {
                reset();
            } else if(subCmd.equals("select")) {
                select();
            } else if(subCmd.equals("set")) {
                set(RegionType.GENERIC);
            } else {
                subCmdHelp();
            }
        } else if(args.length == 3) {
            if(subCmd.equals("addmember")) {
                addMember();
            } else if(subCmd.equals("delmember")) {
                delMember();
            } else if(subCmd.equals("owner")) {
                owner();
            } else if(subCmd.equals("flag")) {
                flag();
            } else if(subCmd.equals("rename")) {
                rename();
            } else if(subCmd.equals("set")) {
                set(args[2]);
            } else if(subCmd.equals("type")) {
                changeType();
            } else if(subCmd.equals("rank")) {
                changeRank();
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(4)) {
            if(subCmd.equals("expand")) {
                expand();
            } else if(subCmd.equals("contract")) {
                contract();
            } else {
                subCmdHelp();
            }
        }
    }

    private void changeType() {
        final Region region;
        final RegionType type;
        if((region = getRegion(args[1])) != null && ((type = getRegionType(args[2]))) != null) {
            RegionUtils.changeType(region, type);
            player.sendNormal("Region " + region.getName() + " set to type " + type.toString() + ".");
        }
    }

    private void showTypes() {
        player.sendNormal("Available region types:");
        String types = "";
        for(RegionType type : RegionType.values()) {
            types = types.concat(type.toString() + ", ");
        }
        player.sendText(types.substring(0, types.length() - 2));
    }

    private void contract() {
        Direction direction;
        Region region;
        int distance;
        if((region = getRegion(args[1])) != null && (direction = getCardinalDirection(args[3])) != null && (distance = parseInt(args[2])) > -1) {
            region.contract(direction, distance);
            player.sendNormal("Region has been contracted.");
        }
    }

    private void expand() {
        Direction direction;
        Region region;
        int distance;
        if((region = getRegion(args[1])) != null && (direction = getCardinalDirection(args[3])) != null && (distance = parseInt(args[2])) > -1) {
            region.expand(direction, distance);
            player.sendNormal("Region has been expanded.");
        }
    }

    private void vert() {
        final EditSession session = getSession();
        if(session != null && hasTwoPoints(session)) {
            session.vert();
            player.sendNormal("Region extended from bedrock to build limit.");
        }
    }

    private void list(final String type) {
        if(getRegionType(type) != null) {
            list(getRegionType(type));
        }
    }
    
    private void list(final RegionType type) {
        String list = "";
        for(Region test : RegionUtils.getRegions(type)) {
            list = list.concat(test.getName() + ", ");
        }
        final String regionType = type.name().replaceFirst("[a-z]", type.name().substring(0, 1).toUpperCase());
        if(list.isEmpty()) {
            player.sendNormal("There are no " + regionType + " regions.");
        } else {
            player.sendNormal(regionType + " Regions:");
            player.sendText(list.substring(0, list.length() - 2));
        }
    }

    private void flagList() {
        player.sendNormal("Available region flags:");
        String message = "";
        for(RegionFlag test : RegionFlag.values()) {
            message = message.concat(test.name() + ", ");
        }
        player.sendText(message.substring(0, message.length() - 2));
    }

    private void flag() {
        final Region region = getRegion(args[1]);
        final RegionFlag flag = getRegionFlag(args[2]);
        if(region != null && flag != null) {
            player.sendNormal(region.getName() + " flag " + flag.toString() + " now set to " + region.toggleFlag(flag) + ".");
        }
    }

    private void del() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            RegionUtils.removeRegion(region);
            player.sendNormal("Region deleted.");
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
        final EditSession session = getSession();
        final Region region = getRegion(args[1]);
        if(session != null && hasTwoPoints(session) && region != null) {
            region.setPoints(session.getV1(), session.getV2());
            player.sendNormal("Region reset with new points.");
        }
    }

    private void select() {
        final Region region = getRegion(args[1]);
        if(region != null) {
            final EditSession session = getSession();
            session.setPoints(region.getV1(), region.getV2());
            session.setWorld(region.getWorld());
            player.sendNormal("Points set. " + session.getVolume() + " blocks selected.");
        }
    }

    private void set(final String type) {
        if(getRegionType(type) != null) {
            set(getRegionType(type));
        }
    }
    
    private void set(final RegionType type) {
        final EditSession session = getSession();
        if(session != null && hasTwoPoints(session) && notRegion(args[1]) && noCensor(args[1])) {
            final Region region = new Region(args[1], session.getV1(), session.getV2(), player.getLocation(), null, "", type);
            RegionUtils.addRegion(region);
            player.sendNormal("Region " + region.getName() + " set.");
        }
    }

    private void addMember() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = matchPlayer(args[2]);
        if(region != null && target != null) {
            region.addMember(target);
            player.sendNormal(target.getNick() + " add to region " + region.getName() + ".");
        }
    }

    private void delMember() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = matchPlayer(args[2]);
        if(region != null && target != null) {
            region.delMember(target);
            player.sendNormal(target.getNick() + " removed from region " + region.getName() + ".");
        }
    }

    private void owner() {
        final Region region = getRegion(args[1]);
        final SaveablePlayer target = matchPlayer(args[2]);
        if(region != null && target != null) {
            region.changeOwner(target);
            player.sendNormal(target.getNick() + " made owner of region " + region.getName() + ".");
        }
    }

    private void changeRank() {
        final Region region = getRegion(args[1]);
        final PlayerRank rank = getRank(args[2]);
        if(region != null && rank != null) {
            region.setRank(rank);
            player.sendNormal(region.getName() + " now owned by " + rank.name() + ".");
        }
    }

    private void rename() {
        final Region region = getRegion(args[1]);
        if(region != null && noCensor(args[1]) && notRegion(args[2])) {
            final String oldName = region.getName();
            RegionUtils.renameRegion(region, args[2]);
            player.sendNormal("Region " + oldName + " renamed to " + region.getName());
        }
    }
}
