package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * A group of players, used for handling PVP.
 * 
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    private String name;
    private SaveablePlayer leader;
    private final HashMap<SaveablePlayer, ClanRank> members;

    private int kills = 0;
    private int deaths = 0;

    public Clan(final String name, final SaveablePlayer leader) {
        this.name = name;
        this.leader = leader;
        members = new HashMap<SaveablePlayer, ClanRank>(1);
        members.put(leader, ClanRank.LEADER);
    }

    public Clan(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        leader = PlayerUtils.getPlayer(recordSplit[1]);
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new HashMap<SaveablePlayer, ClanRank>(recordSplit[4].split(",").length);
        for(String member : recordSplit[4].split(",")) {
            members.put(PlayerUtils.getPlayer(member.split(":")[0]), member.split(":").length > 1 ? ClanRank.valueOf(member.split(":")[1]) : ClanRank.RECRUIT);
        }
        members.put(leader, ClanRank.LEADER);
    }

    @Override
    public final String getRecord() {
        final List<String> record = new ArrayList<String>(5);
        record.add(name);
        record.add(leader.getName());
        record.add(Integer.toString(kills));
        record.add(Integer.toString(deaths));
        final List<String> memberList = new ArrayList<String>(members.size());
        final Iterator<Map.Entry<SaveablePlayer, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<SaveablePlayer, ClanRank> pair = i.next();
            memberList.add(pair.getKey().getName() + ":" + pair.getValue().name());
        }
        record.add(StringUtils.join(memberList, ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    public final void linkMembers() {
        for(SaveablePlayer member : members.keySet()) {
            member.setClan(this);
        }
    }

    public final Set<SaveablePlayer> getOnlineMembers() {
        final Set<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>(members.size());
        for(SaveablePlayer member : members.keySet()) {
            if(member.isOnline()) {
                onlineMembers.add(member);
            }
        }
        return onlineMembers;
    }

    public final Set<SaveablePlayer> getMembers() {
        return members.keySet();
    }
    
    public final Set<SaveablePlayer> getRankMembers(final ClanRank rank) {
        final Set<SaveablePlayer> ranked = new HashSet<SaveablePlayer>(members.size());
        final Iterator<Map.Entry<SaveablePlayer, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<SaveablePlayer, ClanRank> pair = i.next();
            if(pair.getValue().equals(rank)) {
                ranked.add(pair.getKey());
            }
        }
        return ranked;
    }
    
    public final int getKills() {
        return kills;
    }

    public final void newKill() {
        kills++;
    }

    public final int getDeaths() {
        return deaths;
    }

    public final void newDeath() {
        deaths++;
    }

    public final String getName() {
        return name;
    }

    public final void rename(final String name) {
        this.name = name;
    }

    public final SaveablePlayer getLeader() {
        return leader;
    }

    public final boolean changeLeader(final SaveablePlayer player) {
        if(members.containsKey(player)) {
            leader = player;
            return true;
        } else {
            return false;
        }
    }

    public final double getRatio() {
        if(deaths == 0) {
            return kills;
        } else {
            return (double)kills / (double)deaths;
        }
    }

    public final void addMember(final SaveablePlayer player) {
        members.put(player, ClanRank.RECRUIT);
    }

    public final boolean delMember(final SaveablePlayer player) {
        members.remove(player);
        if(leader.equals(player)) {
            if(members.isEmpty()) {
                return false;
            } else {
                leader = members.keySet().toArray(new SaveablePlayer[members.size()])[0];
                return true;
            }
        } else {
            return true;
        }
    }

    public final void sendMessage(final String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendClan("[" + name + "] " + message);
        }
    }
    
    public final boolean promote(final SaveablePlayer member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.next());
        return !members.get(member).equals(prev);
    }
    
    public final boolean demote(final SaveablePlayer member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.prev());
        return !members.get(member).equals(prev);
    }
    
    public final boolean hasClanRank(final SaveablePlayer player) {
        return members.get(player).compareTo(ClanRank.RECRUIT) > 0;
    }
    
    public final String getRankOf(final SaveablePlayer player) {
        return members.get(player).toString();
    }
}
