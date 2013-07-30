package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * A group of players, used for handling PVP.
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    private String name;
    private SaveablePlayer leader;
    private final HashMap<SaveablePlayer, ClanRank> members;

    private int kills = 0;
    private int deaths = 0;

    /**
     * Initialise a brand new clan.
     * @param name Clan name.
     * @param leader Leader name.
     */
    public Clan(final String name, final SaveablePlayer leader) {
        this.name = name;
        this.leader = leader;
        members = new HashMap<SaveablePlayer, ClanRank>(1);
        members.put(leader, ClanRank.LEADER);
    }

    /**
     * Initialise a clan from a string record.
     * @param record A line from a save file.
     */
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
    public String getRecord() {
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

    /**
     *
     */
    public void linkMembers() {
        for(SaveablePlayer member : members.keySet()) {
            member.setClan(this);
        }
    }

    /**
     * Get members of the clan that are currently online.
     * @return Online members.
     */
    public Set<SaveablePlayer> getOnlineMembers() {
        final Set<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>(members.size());
        for(SaveablePlayer member : members.keySet()) {
            if(member != null) {
                onlineMembers.add(member);
            }
        }
        return onlineMembers;
    }

    /**
     *
     * @return
     */
    public Set<SaveablePlayer> getMembers() {
        return members.keySet();
    }
    
    public Set<SaveablePlayer> getRankMembers(final ClanRank rank) {
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
    
    /**
     *
     * @return
     */
    public int getKills() {
        return kills;
    }

     /**
     * Increment the clan's kills stat.
     */
    public void newKill() {
        kills++;
    }

   /**
     *
     * @return
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Increment the clan's deaths stat.
     */
    public void newDeath() {
        deaths++;
    }

    @Override
    public String toString() {
        Bukkit.getLogger().info("Implicit Clan.toString(). (" + Thread.currentThread().getStackTrace() + ")"); // Implicit .toString()
        return name;
    }

    /**
     * Gets the name of the clan.
     * @return Name of the clan.
     */
    public String getName() {
        return name;
    }

    /**
     * Change the name of the clan.
     * @param name New name.
     */
    public void rename(final String name) {
        this.name = name;
    }

    /**
     * Get the name of the clan's leader.
     * @return Clan leader's name.
     */
    public SaveablePlayer getLeader() {
        return leader;
    }

    /**
     * Change the clan leader to another member of the clan.
     * @param player Name of the new leader.
     * @return <code>true</code> if successful, <code>false</code> otherwise.
     */
    public boolean changeLeader(final SaveablePlayer player) {
        if(members.containsKey(player)) {
            leader = player;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the clan's kill/death ratio.
     * @return Kill/death ratio.
     */
    public double getRatio() {
        if(deaths == 0) {
            return kills;
        } else {
            return (double)kills / (double)deaths;
        }
    }

    /**
     * Add a new clan member.
     * @param player Player name.
     */
    public void addMember(final SaveablePlayer player) {
        members.put(player, ClanRank.RECRUIT);
    }

    /**
     * Remove a member from the clan.
     * @param player Player name.
     * @return
     */
    public boolean delMember(final SaveablePlayer player) {
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

    /**
     *
     * @param message
     */
    public void sendMessage(final String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendClan("[" + name + "] " + message);
        }
    }
    
    public boolean promote(final SaveablePlayer member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.next());
        return !members.get(member).equals(prev);
    }
    
    public boolean demote(final SaveablePlayer member) {
        ClanRank prev = members.get(member);
        members.put(member, prev.prev());
        return !members.get(member).equals(prev);
    }
    
    public boolean hasClanRank(final SaveablePlayer player) {
        return members.get(player).compareTo(ClanRank.RECRUIT) > 0;
    }
}
