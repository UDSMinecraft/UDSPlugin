package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Region related commands.
 * 
 * @author UndeadScythes
 */
public class RegionCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(argsLength() == 1) {
            if(subCmdEquals("vert")) {
                vert();
            } else if(subCmdEquals("list")) {
                list(RegionType.GENERIC);
            } else if(subCmdEquals("type")) {
                showTypes();
            } else if(subCmdEquals("flag")) {
                flagList();
            } else {
                subCmdHelp();
            }
        } else if(argsLength() == 2) {
            if(subCmdEquals("del")) {
                del();
            } else if(subCmdEquals("list")) {
                list(arg(1));
            } else if(subCmdEquals("tp")) {
                tp();
            } else if(subCmdEquals("info")) {
                info();
            } else if(subCmdEquals("reset")) {
                reset();
            } else if(subCmdEquals("select")) {
                select();
            } else if(subCmdEquals("set")) {
                set(RegionType.GENERIC);
            } else {
                subCmdHelp();
            }
        } else if(argsLength() == 3) {
            if(subCmdEquals("addmember")) {
                addMember();
            } else if(subCmdEquals("delmember")) {
                delMember();
            } else if(subCmdEquals("owner")) {
                owner();
            } else if(subCmdEquals("flag")) {
                flag();
            } else if(subCmdEquals("rename")) {
                rename();
            } else if(subCmdEquals("set")) {
                set(arg(2));
            } else if(subCmdEquals("type")) {
                changeType();
            } else if(subCmdEquals("rank")) {
                changeRank();
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(4)) {
            if(subCmdEquals("expand")) {
                expand();
            } else if(subCmdEquals("contract")) {
                contract();
            } else {
                subCmdHelp();
            }
        }
    }

    private void changeType() {
        final Region region;
        final RegionType type;
        if((region = getRegion(arg(1))) != null && ((type = getRegionType(arg(2)))) != null) {
            RegionUtils.changeType(region, type);
            player().sendNormal("Region " + region.getName() + " set to type " + type.toString() + ".");
        }
    }

    private void showTypes() {
        player().sendNormal("Available region types:");
        String types = "";
        for(RegionType type : RegionType.values()) {
            types = types.concat(type.toString() + ", ");
        }
        player().sendText(types.substring(0, types.length() - 2));
    }

    private void contract() {
        Direction direction;
        Region region;
        int distance;
        if((region = getRegion(arg(1))) != null && (direction = getCardinalDirection(arg(3))) != null && (distance = getInteger(arg(2))) > -1) {
            region.contract(direction, distance);
            player().sendNormal("Region has been contracted.");
        }
    }

    private void expand() {
        Direction direction;
        Region region;
        int distance;
        if((region = getRegion(arg(1))) != null && (direction = getCardinalDirection(arg(3))) != null && (distance = getInteger(arg(2))) > -1) {
            region.expand(direction, distance);
            player().sendNormal("Region has been expanded.");
        }
    }

    private void vert() {
        final EditSession session = player().forceSession();
        if(session != null && hasTwoPoints(session)) {
            session.vert();
            player().sendNormal("Region extended from bedrock to build limit.");
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
            player().sendNormal("There are no " + regionType + " regions.");
        } else {
            player().sendNormal(regionType + " Regions:");
            player().sendText(list.substring(0, list.length() - 2));
        }
    }

    private void flagList() {
        player().sendNormal("Available region flags:");
        String message = "";
        for(RegionFlag test : RegionFlag.values()) {
            message = message.concat(test.name() + ", ");
        }
        player().sendText(message.substring(0, message.length() - 2));
    }

    private void flag() {
        final Region region = getRegion(arg(1));
        final RegionFlag flag = getRegionFlag(arg(2));
        if(region != null && flag != null) {
            player().sendNormal(region.getName() + " flag " + flag.toString() + " now set to " + region.toggleFlag(flag) + ".");
        }
    }

    private void del() {
        final Region region = getRegion(arg(1));
        if(region != null) {
            RegionUtils.removeRegion(region);
            player().sendNormal("Region deleted.");
        }
    }

    private void tp() {
        final Region region = getRegion(arg(1));
        if(region != null) {
            player().teleport(region.getWarp());
        }
    }

    private void info() {
        final Region region = getRegion(arg(1));
        if(region != null) {
            region.sendInfo(player());
        }
    }

    private void reset() {
        final EditSession session = player().forceSession();
        final Region region = getRegion(arg(1));
        if(session != null && hasTwoPoints(session) && region != null) {
            region.setPoints(session.getV1(), session.getV2());
            player().sendNormal("Region reset with new points.");
        }
    }

    private void select() {
        final Region region = getRegion(arg(1));
        if(region != null) {
            final EditSession session = player().forceSession();
            session.setPoints(region.getV1(), region.getV2());
            session.setWorld(region.getWorld());
            player().sendNormal("Points set. " + session.getVolume() + " blocks selected.");
        }
    }

    private void set(final String type) {
        if(getRegionType(type) != null) {
            set(getRegionType(type));
        }
    }
    
    private void set(final RegionType type) {
        final EditSession session = player().forceSession();
        if(session != null && hasTwoPoints(session) && noRegionExists(arg(1)) && noBadLang(arg(1))) {
            final Region region = new Region(arg(1), session.getV1(), session.getV2(), player().getLocation(), null, "", type);
            RegionUtils.addRegion(region);
            player().sendNormal("Region " + region.getName() + " set.");
        }
    }

    private void addMember() {
        final Region region = getRegion(arg(1));
        final SaveablePlayer target = matchPlayer(arg(2));
        if(region != null && target != null) {
            region.addMember(target);
            player().sendNormal(target.getNick() + " add to region " + region.getName() + ".");
        }
    }

    private void delMember() {
        final Region region = getRegion(arg(1));
        final SaveablePlayer target = matchPlayer(arg(2));
        if(region != null && target != null) {
            region.delMember(target);
            player().sendNormal(target.getNick() + " removed from region " + region.getName() + ".");
        }
    }

    private void owner() {
        final Region region = getRegion(arg(1));
        final SaveablePlayer target = matchPlayer(arg(2));
        if(region != null && target != null) {
            region.changeOwner(target);
            player().sendNormal(target.getNick() + " made owner of region " + region.getName() + ".");
        }
    }

    private void changeRank() {
        final Region region = getRegion(arg(1));
        final PlayerRank rank = getRank(arg(2));
        if(region != null && rank != null) {
            region.setRank(rank);
            player().sendNormal(region.getName() + " now owned by " + rank.name() + ".");
        }
    }

    private void rename() {
        final Region region = getRegion(arg(1));
        if(region != null && noBadLang(arg(1)) && noRegionExists(arg(2))) {
            final String oldName = region.getName();
            RegionUtils.renameRegion(region, arg(2));
            player().sendNormal("Region " + oldName + " renamed to " + region.getName());
        }
    }
}
