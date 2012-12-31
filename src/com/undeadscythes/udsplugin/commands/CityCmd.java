package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

/**
 * City related commands.
 * @author UndeadScythes
 */
public class CityCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        Region city;
        if(args.length == 1) {
            if(args[0].equals("set")) {
                if((city = getCurrentRegion()).getType() == RegionType.CITY && getMunicipality(city.getName()) != null) {
                    city.setWarp(player.getLocation());
                    player.sendMessage(Color.MESSAGE + "City spawn point set.");
                }
            } else if(args[0].equals("list")) {
                sendPage(1, player);
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            int page;
            if(args[0].equals("new")) {
                if(canAfford(UDSPlugin.getConfigInt(ConfigRef.CITY_COST)) && noCensor(args[1]) && notRegion(args[1])) {
                    final Vector min = player.getLocation().add(-100, 0, -100).toVector().setY(0);
                    final Vector max = player.getLocation().add(100, 0, 100).toVector().setY(player.getWorld().getMaxHeight());
                    city = new Region(args[1], min, max, player.getLocation(), player, "", RegionType.CITY);
                    if(noOverlaps(city)) {
                        player.debit(UDSPlugin.getConfigInt(ConfigRef.CITY_COST));
                        UDSPlugin.getRegions(RegionType.NORMAL).put(args[1], city);
                        UDSPlugin.getRegions(RegionType.CITY).put(args[1], city);
                        city.placeMoreMarkers();
                        city.placeTowers();
                        player.sendMessage(Color.MESSAGE + "City founded.");
                        Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " has just founded " + args[1] + ".");
                    }
                }
            } else if(args[0].equals("leave")) {
                if((city = getMatchingCity(args[1])) != null && notMayor(city)) {
                    if(city.delMember(player)) {
                        player.sendMessage(Color.MESSAGE + "You have left " + city.getName() + ".");
                    } else {
                        player.sendMessage(Color.ERROR + "You are not a citizen of " + city.getName() + ".");
                    }
                }
            } else if(args[0].equals("warp")) {
                if((city = getMatchingCity(args[1])) != null && notJailed() && notPinned()) {
                    player.quietTeleport(city.getWarp());
                }
            } else if(args[0].equals("list")) {
                if((page = parseInt(args[1])) != -1) {
                    sendPage(page, player);
                }
            } else if(args[0].equals("clear")) {
                if((city = getMunicipality(args[1])) != null) {
                    UDSPlugin.getRegions(RegionType.NORMAL).remove(city.getName());
                    UDSPlugin.getRegions(RegionType.CITY).remove(city.getName());
                    Bukkit.broadcastMessage(Color.BROADCAST + city.getName() + " has been abandoned.");
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            SaveablePlayer target;
            if(args[0].equals("invite")) {
                if((city = getMunicipality(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    if(city.addMember(target)) {
                        player.sendMessage(Color.MESSAGE + target.getNick() + " was added as a citizen of " + city.getName() + ".");
                        target.sendMessage(Color.MESSAGE + "You have been added as a citizen of " + city.getName());
                    } else {
                        player.sendMessage(Color.ERROR + target.getNick() + " is already a citizen of " + city.getName() + ".");
                    }
                }
            } else if(args[0].equals("banish")) {
                if((city = getMunicipality(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    if(city.delMember(target)) {
                        player.sendMessage(Color.MESSAGE + target.getNick() + " has been banished from " + city.getName() + ".");
                        target.sendMessage(Color.MESSAGE + "You have been banished from " + city.getName() + ".");
                        target.quietTeleport(city.getWarp());
                    } else {
                        player.sendMessage(Color.ERROR + target.getNick() + " is not a citizen of " + city.getName() + ".");
                    }
                }
            } else if(args[0].equals("mayor")) {
                if((city = getMunicipality(args[1])) != null && (target = getMatchingPlayer(args[2])) != null) {
                    city.addMember(city.getOwner());
                    city.delMember(target);
                    city.changeOwner(target);
                    Bukkit.broadcastMessage(Color.BROADCAST + target.getNick() + " has been elected as the new mayor of " + city.getName());
                }
            } else {
                subCmdHelp();
            }
        }
    }

    /**
     * Sends a full page of cities to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(final int page, final SaveablePlayer player) {
        final List<Region> cities = UDSPlugin.getRegions(RegionType.CITY).getSortedValues(new SortByPop());
        final int pages = (cities.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There are no cities yet.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- Current Cities " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Region city : cities) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + city.getName() + " - " + Color.TEXT + "Mayor " + city.getOwner() + ", Pop. " + (city.getMemberNo() + 1));
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}

/**
 * Compare regions by member size.
 * @author UndeadScythe
 */
class SortByPop implements Comparator<Region> {
    @Override
    public int compare(final Region region1, final Region region2) {
        return region2.getMemberNo() - region1.getMemberNo();
    }
}
