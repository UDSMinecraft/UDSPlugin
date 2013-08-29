package com.undeadscythes.udsplugin.clans;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;
import org.apache.commons.lang.*;

/**
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    private String name;
    private OfflineMember leader;
    private final HashMap<OfflineMember, ClanRank> members;

    private int kills = 0;
    private int deaths = 0;

    public Clan(final String name, final OfflineMember leader) {
        this.name = name;
        this.leader = leader;
        members = new HashMap<OfflineMember, ClanRank>(1);
        members.put(leader, ClanRank.LEADER);
    }

    public Clan(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        try {
            leader = MemberUtils.getMember(recordSplit[1]);
        } catch(NoPlayerFoundException ex) {
            throw new UnexpectedException("bad leader on clan load:" + recordSplit[1] + "," + name);
        }
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new HashMap<OfflineMember, ClanRank>(recordSplit[4].split(",").length);
        for(String member : recordSplit[4].split(",")) {
            try {
                members.put(MemberUtils.getMember(member.split(":")[0]), member.split(":").length > 1 ? ClanRank.valueOf(member.split(":")[1]) : ClanRank.RECRUIT);
            } catch(NoPlayerFoundException ex) {
                throw new UnexpectedException("bad member on clan load:" + member + "," + name);
            }
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
        final Iterator<Map.Entry<OfflineMember, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<OfflineMember, ClanRank> pair = i.next();
            memberList.add(pair.getKey().getName() + ":" + pair.getValue().name());
        }
        record.add(StringUtils.join(memberList, ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    public void linkMembers() {
        for(OfflineMember member : members.keySet()) {
            if(member != null) {
                member.setClan(this);
            }
        }
    }

    public Set<Member> getOnlineMembers() {
        final Set<Member> Members = new HashSet<Member>(members.size());
        for(OfflineMember member : members.keySet()) {
            try {
                Members.add(MemberUtils.getOnlineMember(member));
            } catch(PlayerNotOnlineException ex) {}
        }
        return Members;
    }

    public Set<OfflineMember> getMembers() {
        return members.keySet();
    }

    public Set<OfflineMember> getRankMembers(final ClanRank rank) {
        final Set<OfflineMember> ranked = new HashSet<OfflineMember>(members.size());
        final Iterator<Map.Entry<OfflineMember, ClanRank>> i = members.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry<OfflineMember, ClanRank> pair = i.next();
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

    public OfflineMember getLeader() {
        return leader;
    }

    public boolean changeLeader(final OfflineMember player) {
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

    public void addMember(final OfflineMember player) {
        members.put(player, ClanRank.RECRUIT);
    }

    public boolean delMember(final OfflineMember player) {
        members.remove(player);
        if(leader.equals(player)) {
            if(members.isEmpty()) {
                return false;
            } else {
                leader = members.keySet().toArray(new OfflineMember[members.size()])[0];
                return true;
            }
        } else {
            return true;
        }
    }

    public void sendMessage(final String message) {
        for(Member member : getOnlineMembers()) {
            member.sendClan("[" + name + "] " + message);
        }
    }

    public boolean promote(final OfflineMember member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.next());
        return !members.get(member).equals(prev);
    }

    public boolean demote(final OfflineMember member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.prev());
        return !members.get(member).equals(prev);
    }

    public boolean hasClanRank(final OfflineMember player) {
        return members.get(player).compareTo(ClanRank.RECRUIT) > 0;
    }

    public String getRankOf(final OfflineMember player) {
        return members.get(player).toString();
    }
}
