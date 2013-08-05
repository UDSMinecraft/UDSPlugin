package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.comparators.SortByKDR;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.text.*;
import java.util.*;
import org.bukkit.util.Vector;

/**
 * Various clan related commands.
 * @author UndeadScythes
 */
public class ClanCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Clan clan;
        Region base;
        SaveablePlayer target;
        if(argsLength() == 1) {
            if(subCmdEquals("base")) {
                if((clan = isInClan()) != null && (base = hasClanBase(clan)) != null && notJailed() && notPinned()) {
                    player().teleport(base.getWarp());
                }
            } else if(subCmdEquals("leave")) {
                if((clan = isInClan()) != null) {
                    if(clan.delMember(player())) {
                        if((base = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base")) != null) {
                            base.delMember(player());
                        }
                        clan.sendMessage(player().getNick() + " has left the clan.");
                    } else {
                        ClanUtils.removeClan(clan);
                        RegionUtils.removeRegion(RegionType.BASE, clan.getName() + "base");
                        UDSPlugin.sendBroadcast(clan.getName() + " no longer exists as all members have left.");
                    }
                    player().setClan(null);
                    player().sendNormal("You have left " + clan.getName());
                }
            } else if(subCmdEquals("members")) {
                if((clan = isInClan()) != null) {
                    if(!clan.getLeader().equals(player())) {
                        player().sendNormal("Your clan leader is " + clan.getLeader() + ".");
                        player().sendNormal("Your rank is " + clan.getRankOf(player()));
                    }
                    boolean empty = true;
                    for(final ClanRank rank : ClanRank.values()) {
                        String members = "";
                        for(SaveablePlayer member : clan.getRankMembers(rank)) {
                            if(!member.equals(player())) {
                                members = members.concat(member.getNick() + ", ");
                            }
                        }
                        if(!members.isEmpty()) {
                            empty = false;
                            player().sendNormal(clan.getName() + "s " + rank.toString() + "s are:");
                            player().sendText(members.substring(0, members.length() - 2));
                        }
                    }
                    if(empty) {
                        player().sendNormal("Your clan has no other members.");
                    }
                }
            } else if(subCmdEquals("list")) {
                sendPage(1, player());
            } else if(subCmdEquals("disband")) {
                if((clan = isInClan()) != null && isClanLeader(clan)) {
                    UDSPlugin.sendBroadcast(clan.getName() + " has been disbanded.");
                    ClanUtils.removeClan(clan);
                    clan.sendMessage("Your clan has been disbanded.");
                    for(SaveablePlayer member : clan.getMembers()) {
                        member.setClan(null);
                    }
                    RegionUtils.removeRegion(RegionType.BASE, clan.getName() + "base");
                }
            } else if(subCmdEquals("stats")) {
                if((clan = isInClan()) != null) {
                    final DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player().sendNormal(clan.getName() + "'s stats:");
                    player().sendListItem("Members: ", "" + clan.getMembers().size());
                    player().sendListItem("Kills: ", "" + clan.getKills());
                    player().sendListItem("Deaths: ", "" + clan.getDeaths());
                    player().sendListItem("KDR: ", decimalFormat.format(clan.getRatio()));
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(2)) {
            int page;
            if(subCmdEquals("new")) {
                if(noClan() && canAfford(Config.CLAN_COST) && noBadLang(arg(1)) && notClan(arg(1))) {
                    player().debit(Config.CLAN_COST);
                    clan = new Clan(arg(1), player());
                    player().setClan(clan);
                    ClanUtils.addClan(clan);
                    UDSPlugin.sendBroadcast(player().getNick() + " just created " + arg(1) + ".");
                }
            } else if(subCmdEquals("invite")) {
                if((target = matchesPlayer(arg(1))) != null && isOnline(target) && canRequest(target) && (clan = isInClan()) != null && hasClanRank(clan, player()) && notSelf(target)) {
                    UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.CLAN, clan.getName(), target));
                    player().sendMessage(Message.REQUEST_SENT);
                    target.sendNormal(player().getNick() + " has invited you to join " + clan.getName() + ".");
                    target.sendMessage(Message.REQUEST_Y_N);
                }
            } else if(subCmdEquals("kick")) {
                if((target = matchesPlayer(arg(1))) != null && (clan = isInClan()) != null && isInClan(target, clan) && hasClanRank(clan, player()) && notSelf(target)) {
                    clan.delMember(target);
                    target.setClan(null);
                    if((base = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base")) != null) {
                        base.delMember(target);
                    }
                    target.sendNormal(player().getNick() + " has kicked you out of " + clan.getName() + ".");
                    player().sendNormal(target.getNick() + " has been kicked out of your clan.");
                    clan.sendMessage(target.getNick() + " has left the clan.");
                }
            } else if(subCmdEquals("promote")) {
                if((target = matchesPlayer(arg(1))) != null && (clan = isInClan()) != null && isInClan(target, clan) && hasClanRank(clan, player()) && notSelf(target)) {
                    if(clan.promote(target)) {
                        target.sendNormal("You have been promoted in clan " + clan.getName() + ".");
                        player().sendNormal(target.getNick() + " has been promoted.");
                        clan.sendMessage(target.getNick() + " has been promoted.");
                    } else {
                        player().sendError("That player cannot be promoted any further.");
                    }
                }
            } else if(subCmdEquals("demote")) {
                if((target = matchesPlayer(arg(1))) != null && (clan = isInClan()) != null && isInClan(target, clan) && hasClanRank(clan, player()) && notSelf(target)) {
                    if(clan.demote(target)) {
                        target.sendNormal("You have been demoted in clan " + clan.getName() + ".");
                        player().sendNormal(target.getNick() + " has been promoted.");
                        clan.sendMessage(target.getNick() + " has been demoted.");
                    } else {
                        player().sendError("That player cannot be demoted any further.");
                    }
                }
            } else if(subCmdEquals("members")) {
                if((clan = clanExists(arg(1))) != null) {
                    String members = "";
                    for(SaveablePlayer member : clan.getMembers()) {
                        members = members.concat(member.getNick() + ", ");
                    }
                    player().sendNormal(clan.getName() + "'s leader is " + clan.getLeader().getNick() + ".");
                    player().sendNormal(clan.getName() + "'s members are:");
                    player().sendText(members.substring(0, members.length() - 2));
                }
            } else if(subCmdEquals("list")) {
                if((page = isInteger(arg(1))) != -1) {
                    sendPage(page, player());
                }
            } else if(subCmdEquals("stats")) {
                if((clan = clanExists(arg(1))) != null) {
                    final DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    player().sendNormal(clan.getName() + "'s stats:");
                    player().sendListItem("Members: ", "" + clan.getMembers().size());
                    player().sendListItem("Kills: ", "" +  clan.getKills());
                    player().sendListItem("Deaths: ", "" + clan.getDeaths());
                    player().sendListItem("KDR: ", "" + decimalFormat.format(clan.getRatio()));
                }
            } else if(subCmdEquals("rename")) {
                if((clan = isInClan()) != null && isClanLeader(clan) && noBadLang(arg(1)) && notClan(arg(1)) && canAfford(Config.CLAN_COST)) {
                    player().debit(Config.CLAN_COST);
                    ClanUtils.removeClan(clan);
                    if((base = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base")) != null) {
                        RegionUtils.renameRegion(base, arg(1) + "base");
                    }
                    clan.rename(arg(1));
                    ClanUtils.addClan(clan);
                    clan.sendMessage("Your clan has been renamed " + arg(1) + ".");
                    UDSPlugin.sendBroadcast(player().getNick() + " has rebranded their clan as " + arg(1) + ".");
                }
            } else if(subCmdEquals("owner")) {
                if((clan = isInClan()) != null && isClanLeader(clan) && (target = matchesPlayer(arg(1))) != null && isInClan(target, clan)) {
                    clan.changeLeader(target);
                    clan.sendMessage("Your new leader is " + target.getNick());
                    if(target.isOnline()) {
                        target.sendNormal("You are the new leader of " + clan.getName());
                    }
                    player().sendNormal("You have resigned as leader of " + clan.getName());
                }
            } else if(subCmdEquals("base")) {
                if((clan = isInClan()) != null && isClanLeader(clan)) {
                    if(arg(1).equals("make")) {
                        if(noBase(clan) && canAfford(Config.BASE_COST)) {
                            final Vector min = player().getLocation().add(-25, 0, -25).toVector().setY(20);
                            final Vector max = player().getLocation().add(25, 0, 25).toVector().setY(220);
                            base = new Region(clan.getName() + "base", min, max, player().getLocation(), null, "", RegionType.BASE);
                            if(noOverlaps(base)) {
                                player().debit(Config.BASE_COST);
                                RegionUtils.addRegion(base);
                                base.placeMoreMarkers();
                                base.placeTowers();
                                clan.sendMessage("Your clan base has just been set up. Use /clan base to teleport to it.");
                                for(SaveablePlayer member : clan.getMembers()) {
                                    base.addMember(member);
                                }
                            }
                        }
                    } else if(arg(1).equals("clear")) {
                        if((base = hasClanBase(clan)) != null) {
                            RegionUtils.removeRegion(base);
                            clan.sendMessage("Your clan base has been removed.");
                        }
                    } else if(arg(1).equals("set")) {
                        if((base = hasClanBase(clan)) != null) {
                            base.setWarp(player().getLocation());
                            player().sendNormal("Clan base warp point set.");
                        }
                    } else {
                        subCmdHelp();
                    }
                }
            } else {
                subCmdHelp();
            }
        }
    }

    private void sendPage(int page, SaveablePlayer player) {
        final List<Clan> clans = ClanUtils.getSortedClans(new SortByKDR());
        final int pages = (clans.size() + 8) / 9;
        if(pages == 0) {
            player.sendNormal("There are no clans on the server.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- Current Clans " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            final DecimalFormat decimalFormat = new DecimalFormat("#.##");
            for(Clan clan : clans) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendListItem(clan.getName() + " - ", clan.getLeader().getNick() + " KDR: " + decimalFormat.format(clan.getRatio()));
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
    
    private boolean hasClanRank(final Clan clan, final SaveablePlayer player) {
        if(!clan.hasClanRank(player)) {
            player.sendError("You are not high enough rank in the clan to do this.");
            return false;
        }
        return true;
    }
}
