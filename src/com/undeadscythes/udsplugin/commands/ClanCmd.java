package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.text.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

/**
 * Various clan related commands.
 * @author UndeadScythes
 */
public class ClanCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsMoreLessInc(1, 3)) {
            Clan clan;
            Region base;
            SaveablePlayer target;
            if(args.length == 1) {
                if(args[0].equals("base") && (clan = getClan()) != null && (base = getBase(clan)) != null && notJailed() && notPinned()) {
                    player.teleport(base.getWarp());
                } else if(args[0].equals("leave") && (clan = getClan()) != null) {
                    if(clan.delMember(player)) {
                        if((base = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
                            base.delMember(player);
                        }
                        clan.sendMessage(player.getDisplayName() + " has left the clan.");
                    } else {
                        UDSPlugin.getClans().remove(clan.getName());
                        UDSPlugin.getBases().remove(clan.getName() + "base");
                        UDSPlugin.getRegions().remove(clan.getName() + "base");
                        Bukkit.broadcastMessage(Color.BROADCAST + clan.getName() + " no longer exists as all members have left.");
                    }
                    player.setClan(null);
                    player.sendMessage(Color.MESSAGE + "You have left " + clan.getName());
                } else if(args[0].equals("members") && (clan = getClan()) != null) {
                    String members = "";
                    for(SaveablePlayer member : clan.getMembers()) {
                        if(!member.equals(player)) {
                            members = members.concat(member + ", ");
                        }
                    }
                    if(!clan.getLeader().equals(player)) {
                        player.sendMessage(Color.MESSAGE + "Your clan leader is " + clan.getLeader() + ".");
                    }
                    if(!members.isEmpty()) {
                        player.sendMessage(Color.MESSAGE + "Your fellow clan members are:");
                        player.sendMessage(Color.TEXT + members.substring(0, members.length() - 2));
                    } else {
                        player.sendMessage(Color.MESSAGE + "Your clan has no other members.");
                    }
                } else if(args[0].equals("list")) {
                    sendPage(1, player);
                } else if(args[0].equals("disband") && (clan = getClan()) != null && isLeader(clan)) {
                    Bukkit.broadcastMessage(Color.BROADCAST + clan.getName() + " has been disbanded.");
                    UDSPlugin.getClans().remove(clan.getName());
                    clan.sendMessage("Your clan has been disbanded.");
                    for(SaveablePlayer member : clan.getMembers()) {
                        member.setClan(null);
                    }
                    UDSPlugin.getBases().remove(clan.getName() + "base");
                } else if(args[0].equals("stats") && (clan = getClan()) != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s stats:");
                    player.sendMessage(Color.ITEM + "Members: " + Color.TEXT + clan.getMembers().size());
                    player.sendMessage(Color.ITEM + "Kills: " + Color.TEXT + clan.getKills());
                    player.sendMessage(Color.ITEM + "Deaths: " + Color.TEXT + clan.getDeaths());
                    player.sendMessage(Color.ITEM + "KDR: " + Color.TEXT + decimalFormat.format(clan.getRatio()));
                }
            } else if(args.length == 2) {
                int page;
                if(args[0].equals("new") && isClanless() && canAfford(Config.CLAN_COST) && noCensor(args[1]) && notClan(args[1])) {
                    player.debit(Config.CLAN_COST);
                    clan = new Clan(args[1], player);
                    player.setClan(clan);
                    UDSPlugin.getClans().put(args[1], clan);
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " just created " + args[1] + ".");
                } else if(args[0].equals("invite") && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && (clan = getClan()) != null && isLeader(clan) && notSelf(target)) {
                    UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.RequestType.CLAN, clan.getName(), target));
                    player.sendMessage(Message.REQUEST_SENT);
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has invited you to join " + clan.getName() + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                } else if(args[0].equals("kick") && (target = getMatchingPlayer(args[1])) != null && (clan = getClan()) != null && isInClan(target, clan)) {
                    clan.delMember(target);
                    target.setClan(null);
                    if((base = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
                        base.delMember(target);
                    }
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has kicked you out of " + clan.getName() + ".");
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been kicked out of your clan.");
                    clan.sendMessage(target.getDisplayName() + " has left the clan.");
                } else if(args[0].equals("members") && (clan = getClan(args[1])) != null) {
                    String members = "";
                    for(SaveablePlayer member : clan.getMembers()) {
                        members = members.concat(member.getDisplayName() + ", ");
                    }
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s leader is " + clan.getLeader() + ".");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s members are:");
                    player.sendMessage(Color.TEXT + members.substring(0, members.length() - 2));
                } else if(args[0].equals("list") && (page = parseInt(args[1])) != -1) {
                    sendPage(page, player);
                } else if(args[0].equals("stats") && (clan = getClan(args[1])) != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s stats:");
                    player.sendMessage(Color.ITEM + "Members: " + Color.TEXT + clan.getMembers().size());
                    player.sendMessage(Color.ITEM + "Kills: " + Color.TEXT + clan.getKills());
                    player.sendMessage(Color.ITEM + "Deaths: " + Color.TEXT + clan.getDeaths());
                    player.sendMessage(Color.ITEM + "KDR: " + Color.TEXT + decimalFormat.format(clan.getRatio()));
                } else if(args[0].equals("rename") && (clan = getClan()) != null && isLeader(clan) && noCensor(args[1]) && notClan(args[1]) && canAfford(Config.CLAN_COST)) {
                    player.debit(Config.CLAN_COST);
                    UDSPlugin.getClans().remove(clan.getName());
                    if((base = UDSPlugin.getBases().remove(clan.getName() + "base")) != null) {
                        UDSPlugin.getRegions().remove(clan.getName() + "base");
                        base.rename(args[1] + "base");
                        UDSPlugin.getRegions().put(args[1] + "base", base);
                        UDSPlugin.getBases().put(args[1] + "base", base);
                    }
                    clan.rename(args[1]);
                    UDSPlugin.getClans().put(args[1], clan);
                    clan.sendMessage("Your clan has been renamed " + args[1] + ".");
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " has rebranded their clan as " + args[1] + ".");
                } else if(args[0].equals("owner") && (clan = getClan()) != null && isLeader(clan) && (target = getMatchingPlayer(args[1])) != null && isInClan(target, clan)) {
                    clan.changeLeader(target);
                    clan.sendMessage("Your new leader is " + target.getDisplayName());
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You are the new leader of " + clan.getName());
                    }
                    player.sendMessage(Color.MESSAGE + "You have resigned as leader of " + clan.getName());
                } else if(args[0].equals("base") && (clan = getClan()) != null && isLeader(clan)) {
                    if(args[1].equals("make") && noBase(clan) && canAfford(Config.BASE_COST)) {
                        Vector min = player.getLocation().add(-25, 0, -25).toVector().setY(20);
                        Vector max = player.getLocation().add(25, 0, 25).toVector().setY(220);
                        base = new Region(clan.getName() + "base", min, max, player.getLocation(), null, "", Region.RegionType.BASE);
                        if(noOverlaps(base)) {
                            player.debit(Config.BASE_COST);
                            UDSPlugin.getRegions().put(base.getName(), base);
                            UDSPlugin.getBases().put(base.getName(), base);
                            base.placeMoreMarkers();
                            base.placeTowers();
                            clan.sendMessage("Your clan base has just been set up. Use /clan base to teleport to it.");
                            for(SaveablePlayer member : clan.getMembers()) {
                                base.addMember(member);
                            }
                        }
                    } else if(args[1].equals("clear") && (base = getBase(clan)) != null) {
                        UDSPlugin.getRegions().remove(base.getName());
                        UDSPlugin.getBases().remove(base.getName());
                        clan.sendMessage("Your clan base has been removed.");
                    } else if(args[1].equals("set") && (base = getBase(clan)) != null) {
                        base.setWarp(player.getLocation());
                        player.sendMessage(Color.MESSAGE + "Clan base warp point set.");
                    }
                }
            }
        }
    }

    private void sendPage(int page, SaveablePlayer player) {
        ArrayList<Clan> clans = UDSPlugin.getClans().getSortedValues(new SortByKDR());
        int pages = (clans.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There are no clans on the server.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- Current Clans " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            for(Clan clan : clans) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + clan.getName() + " - " + Color.TEXT + clan.getLeader().getDisplayName() + " KDR: " + decimalFormat.format(clan.getRatio()));
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}

class SortByKDR implements Comparator<Clan> {
    /**
     * @inheritDoc
     */
    @Override
    public int compare(Clan clan1, Clan clan2) {
        return (int)((clan2.getRatio() - clan1.getRatio()) * 100);
    }
}

