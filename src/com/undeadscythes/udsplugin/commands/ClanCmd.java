package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Comparator;
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
                if(args[0].equals("base") && (clan = hasClan()) != null && (base = hasBase(clan)) != null) {
                    player.teleport(base.getWarp());
                } else if(args[0].equals("leave") && (clan = hasClan()) != null) {
                    if(clan.delMember(player.getName())) {
                        if((base = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
                            base.delMember(player.getName());
                        }
                        clan.sendMessage(player.getDisplayName() + " has left the clan.");
                    } else {
                        UDSPlugin.getClans().remove(clan.getName());
                        UDSPlugin.getBases().remove(clan.getName() + "base");
                        UDSPlugin.getRegions().remove(clan.getName() + "base");
                        Bukkit.broadcastMessage(Color.BROADCAST + clan.getName() + " no longer exists as all members have left.");
                    }
                    player.sendMessage(Color.MESSAGE + "You have left " + clan.getName());
                } else if(args[0].equals("members") && (clan = hasClan()) != null) {
                    String members = "";
                    for(String name : clan.getMembers()) {
                        if(!name.equals(player.getName())) {
                            members = members.concat(name + ", ");
                        }
                    }
                    if(!clan.getLeader().equals(player.getName())) {
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
                } else if(args[0].equals("disband") && (clan = hasClan()) != null && isLeader(clan)) {
                    Bukkit.broadcastMessage(Color.BROADCAST + clan.getName() + " has been disbanded.");
                    UDSPlugin.getClans().remove(clan.getName());
                    clan.sendMessage("Your clan has been disbanded.");
                    for(String name : clan.getMembers()) {
                        UDSPlugin.getPlayers().get(name).setClan("");
                    }
                    UDSPlugin.getBases().remove(clan.getName() + "base");
                } else if(args[0].equals("stats") && (clan = hasClan()) != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s stats:");
                    player.sendMessage(Color.ITEM + "Members: " + Color.TEXT + clan.getMembers().size());
                    player.sendMessage(Color.ITEM + "Kills: " + Color.TEXT + clan.getKills());
                    player.sendMessage(Color.ITEM + "Deaths: " + Color.TEXT + clan.getDeaths());
                    player.sendMessage(Color.ITEM + "KDR: " + Color.TEXT + decimalFormat.format(clan.getRatio()));
                }
            } else if(args.length == 2) {
                int page;
                if(args[0].equals("new") && isClanless() && canAfford(Config.CLAN_COST) && censor(args[1]) && noClan(args[1])) {
                    player.debit(Config.CLAN_COST);
                    UDSPlugin.getClans().put(args[1], new Clan(args[1], player.getName()));
                    Bukkit.broadcastMessage(Color.BROADCAST + player.getDisplayName() + " just created " + args[1] + ".");
                } else if(args[0].equals("invite") && (target = matchesPlayer(args[1])) != null && isOnline(target) && (clan = hasClan()) != null && isLeader(clan) && notSelf(target)) {
                    UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.Type.CLAN, clan.getName(), target));
                    player.sendMessage(Message.REQUEST_SENT);
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has invited you to join " + clan.getName() + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                } else if(args[0].equals("kick") && (target = matchesPlayer(args[1])) != null && (clan = hasClan()) != null && isInClan(target, clan)) {
                    clan.delMember(target.getName());
                    if((base = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
                        base.delMember(target.getName());
                    }
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has kicked you out of " + clan.getName() + ".");
                    player.sendMessage(Color.MESSAGE + target.getDisplayName() + " has been kicked out of your clan.");
                    clan.sendMessage(target.getDisplayName() + " has left the clan.");
                } else if(args[0].equals("members") && (clan = matchesClan(args[1])) != null) {
                    String members = "";
                    for(String name : clan.getMembers()) {
                        members = members.concat(name + ", ");
                    }
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s leader is " + clan.getLeader() + ".");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s members are:");
                    player.sendMessage(Color.TEXT + members.substring(0, members.length() - 2));
                } else if(args[0].equals("list") && (page = parseInt(args[1])) != -1) {
                    sendPage(page, player);
                } else if(args[0].equals("stats") && (clan = matchesClan(args[1])) != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player.sendMessage(Color.MESSAGE + clan.getName() + "'s stats:");
                    player.sendMessage(Color.ITEM + "Members: " + Color.TEXT + clan.getMembers().size());
                    player.sendMessage(Color.ITEM + "Kills: " + Color.TEXT + clan.getKills());
                    player.sendMessage(Color.ITEM + "Deaths: " + Color.TEXT + clan.getDeaths());
                    player.sendMessage(Color.ITEM + "KDR: " + Color.TEXT + decimalFormat.format(clan.getRatio()));
                } else if(args[0].equals("rename") && (clan = hasClan()) != null && isLeader(clan) && censor(args[1]) && noClan(args[1]) && canAfford(Config.CLAN_COST)) {
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
                } else if(args[0].equals("owner") && (clan = hasClan()) != null && isLeader(clan) && (target = matchesPlayer(args[1])) != null && isInClan(target, clan)) {
                    clan.changeLeader(target.getName());
                    clan.sendMessage("Your new leader is " + target.getDisplayName());
                    if(target.isOnline()) {
                        target.sendMessage(Color.MESSAGE + "You are the new leader of " + clan.getName());
                    }
                    player.sendMessage(Color.MESSAGE + "You have resigned as leader of " + clan.getName());
                } else if(args[0].equals("base") && (clan = hasClan()) != null && isLeader(clan)) {
                    if(args[1].equals("make") && noBase(clan) && canAfford(Config.BASE_COST)) {
                        Vector min = player.getLocation().add(-25, 0, -25).toVector().setY(20);
                        Vector max = player.getLocation().add(25, 0, 25).toVector().setY(220);
                        base = new Region(clan.getName() + "base", min, max, player.getLocation(), "", "", Region.Type.BASE);
                        if(noOverlaps(base)) {
                            player.debit(Config.BASE_COST);
                            UDSPlugin.getRegions().put(base.getName(), base);
                            UDSPlugin.getBases().put(base.getName(), base);
                            base.placeMoreMarkers();
                            base.placeTowers();
                            clan.sendMessage("Your clan base has just been set up. Use /clan base to teleport to it.");
                            for(String member : clan.getMembers()) {
                                base.addMember(member);
                            }
                        }
                    } else if(args[1].equals("clear") && (base = hasBase(clan)) != null) {
                        UDSPlugin.getRegions().remove(base.getName());
                        UDSPlugin.getBases().remove(base.getName());
                        clan.sendMessage("Your clan base has been removed.");
                    } else if(args[1].equals("set") && (base = hasBase(clan)) != null) {
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
                    player.sendMessage(Color.ITEM + clan.getName() + " - " + Color.TEXT + clan.getLeader() + " KDR: " + decimalFormat.format(clan.getRatio()));
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
        return (int)(clan2.getRatio() - clan1.getRatio() * 1000);
    }
}

