package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class CityCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        Region city;
        if(args.length == 1) {
            if(subCmdEquals("set")) {
                if((city = getCurrentCity()).getType() == RegionType.CITY && getCity(city.getName()) != null) {
                    city.setWarp(player.getLocation());
                    player.sendNormal("City spawn point set.");
                }
            } else if(subCmdEquals("list")) {
                sendPage(1, player);
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            int page;
            if(subCmdEquals("new")) {
                if(canAfford(Config.CITY_COST) && noBadLang(args[1]) && noRegionExists(args[1])) {
                    final Vector min = player.getLocation().add(-100, 0, -100).toVector().setY(0);
                    final Vector max = player.getLocation().add(100, 0, 100).toVector().setY(player.getWorld().getMaxHeight());
                    city = new Region(args[1], min, max, player.getLocation(), player.getOfflineMember(), "", RegionType.CITY);
                    if(noOverlaps(city)) {
                        player.debit(Config.CITY_COST);
                        RegionUtils.addRegion(city);
                        city.placeMoreMarkers();
                        city.placeTowers();
                        player.sendNormal("City founded.");
                        UDSPlugin.sendBroadcast(player.getNick() + " has just founded " + args[1] + ".");
                    }
                }
            } else if(subCmdEquals("leave")) {
                if((city = matchCity(args[1])) != null && notMayor(city)) {
                    if(city.delMember(player.getOfflineMember())) {
                        player.sendNormal("You have left " + city.getName() + ".");
                    } else {
                        player.sendError("You are not a citizen of " + city.getName() + ".");
                    }
                }
            } else if(subCmdEquals("warp")) {
                if((city = matchCity(args[1])) != null && notJailed() && notPinned()) {
                    player.quietTeleport(city.getWarp());
                }
            } else if(subCmdEquals("list")) {
                if((page = getInteger(args[1])) != -1) {
                    sendPage(page, player);
                }
            } else if(subCmdEquals("clear")) {
                if((city = getCity(args[1])) != null) {
                    RegionUtils.removeRegion(city);
                    UDSPlugin.sendBroadcast(city.getName() + " has been abandoned.");
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            Member target;
            if(subCmdEquals("invite")) {
                if((city = getCity(args[1])) != null && (target = matchOnlinePlayer(args[2])) != null) {
                    if(city.addMember(target.getOfflineMember())) {
                        player.sendNormal(target.getNick() + " was added as a citizen of " + city.getName() + ".");
                        target.sendNormal("You have been added as a citizen of " + city.getName());
                    } else {
                        player.sendError(target.getNick() + " is already a citizen of " + city.getName() + ".");
                    }
                }
            } else if(subCmdEquals("banish")) {
                if((city = getCity(args[1])) != null && (target = matchOnlinePlayer(args[2])) != null) {
                    if(city.delMember(target.getOfflineMember())) {
                        player.sendNormal(target.getNick() + " has been banished from " + city.getName() + ".");
                        target.sendNormal("You have been banished from " + city.getName() + ".");
                        target.quietTeleport(city.getWarp());
                    } else {
                        player.sendError(target.getNick() + " is not a citizen of " + city.getName() + ".");
                    }
                }
            } else if(subCmdEquals("mayor")) {
                if((city = getCity(args[1])) != null && (target = matchOnlinePlayer(args[2])) != null) {
                    city.addMember(city.getOwner());
                    city.delMember(target.getOfflineMember());
                    city.changeOwner(target.getOfflineMember());
                    UDSPlugin.sendBroadcast(target.getNick() + " has been elected as the new mayor of " + city.getName());
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private void sendPage(final int page, final Member player) {
        final List<Region> cities = RegionUtils.getSortedRegions(RegionType.CITY, new SortByPop());
        final int pages = (cities.size() + 8) / 9;
        if(pages == 0) {
            player.sendNormal("There are no cities yet.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- Current Cities " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Region city : cities) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendListItem(city.getName() + " - ", "Mayor " + city.getOwner() + ", Pop. " + (city.getMemberNo() + 1));
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
