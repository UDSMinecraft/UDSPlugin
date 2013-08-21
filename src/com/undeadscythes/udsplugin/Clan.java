package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;

/**
 * A group of players, used for handling PVP.
 *
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    private String name;
    private Member leader;
    private final HashMap<Member, ClanRank> members;

    private int kills = 0;
    private int deaths = 0;

    public Clan(final String name, final Member leader) {
        this.name = name;
        this.leader = leader;
        members = new HashMap<Member, ClanRank>(1);
        members.put(leader, ClanRank.LEADER);
    }

    public Clan(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        leader = PlayerUtils.getPlayer(recordSplit[1]);
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new HashMap<Member, ClanRank>(recordSplit[4].split(",").length);
        for(String member : recordSplit[4].split(",")) {
            members.put(PlayerUtils.getPlayer(member.split(":")[0]), member.split(":").length > 1 ? ClanRank.valueOf(member.split(":")[1]) : ClanRank.RECRUIT);
        }
        members.put(leader, ClanRank.LEADER);
    }

    @Override
    public String getRecord() {
        final List<String> record = new ArrayList<String>(5);
        record.add(name);
        record.add(leader.getName());
        record.add(Integer.toString(kills));
        record.add(Integer.toString(deaths));
        final List<String> memberList = new ArrayList<String>(members.size());
        final Iterator<Map.Entry<Member, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<Member, ClanRank> pair = i.next();
            memberList.add(pair.getKey().getName() + ":" + pair.getValue().name());
        }
        record.add(StringUtils.join(memberList, ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    public void linkMembers() {
        for(Member member : members.keySet()) {
            if(member != null) {
                member.setClan(this);
            }
        }
    }

    public Set<Member> getOnlineMembers() {
        final Set<Member> Members = new HashSet<Member>(members.size());
        for(Member member : members.keySet()) {
            try {
                Members.add(PlayerUtils.getOnlinePlayer(member));
            } catch (PlayerNotOnlineException ex) {}
        }
        return Members;
    }

    public Set<Member> getMembers() {
        return members.keySet();
    }

    public Set<Member> getRankMembers(final ClanRank rank) {
        final Set<Member> ranked = new HashSet<Member>(members.size());
        final Iterator<Map.Entry<Member, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<Member, ClanRank> pair = i.next();
            if(pair.getValue().equals(rank)) {
                ranked.add(pair.getKey());
            }
        }
        return ranked;
    }

    public int getKills() {
        return kills;
    }

    public void newKill() {
        kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void newDeath() {
        deaths++;
    }

    public String getName() {
        return name;
    }

    public void rename(final String name) {
        this.name = name;
    }

    public Member getLeader() {
        return leader;
    }

    public boolean changeLeader(final Member player) {
        if(members.containsKey(player)) {
            leader = player;
            return true;
        } else {
            return false;
        }
    }

    public double getRatio() {
        if(deaths == 0) {
            return kills;
        } else {
            return (double)kills / (double)deaths;
        }
    }

    public void addMember(final Member player) {
        members.put(player, ClanRank.RECRUIT);
    }

    public boolean delMember(final Member player) {
        members.remove(player);
        if(leader.equals(player)) {
            if(members.isEmpty()) {
                return false;
            } else {
                leader = members.keySet().toArray(new Member[members.size()])[0];
                return true;
            }
        } else {
            return true;
        }
    }

    public void sendMessage(final String message) {
        for(Member member : getMembers()) {
            member.sendClan("[" + name + "] " + message);
        }
    }

    public boolean promote(final Member member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.next());
        return !members.get(member).equals(prev);
    }

    public boolean demote(final Member member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.prev());
        return !members.get(member).equals(prev);
    }

    public boolean hasClanRank(final Member player) {
        return members.get(player).compareTo(ClanRank.RECRUIT) > 0;
    }

    public String getRankOf(final Member player) {
        return members.get(player).toString();
    }
}
