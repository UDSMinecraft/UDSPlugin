package com.undeadscythes.udsplugin;

import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * A group of players, used for handling PVP.
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    /**
     * File name of clan file.
     */
    public static final String PATH = "clans.csv";

    private transient String name;
    private transient SaveablePlayer leader;
    private transient final Set<SaveablePlayer> members;

    private transient int kills = 0;
    private transient int deaths = 0;

    /**
     * Initialise a brand new clan.
     * @param name Clan name.
     * @param leader Leader name.
     */
    public Clan(final String name, final SaveablePlayer leader) {
        this.name = name;
        this.leader = leader;
        members = new HashSet<SaveablePlayer>(Arrays.asList(leader));
    }

    /**
     * Initialise a clan from a string record.
     * @param record A line from a save file.
     */
    public Clan(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        leader = UDSPlugin.getPlayers().get(recordSplit[1]);
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new HashSet<SaveablePlayer>();
        for(String member : recordSplit[4].split(",")) {
            members.add(UDSPlugin.getPlayers().get(member));
        }
    }

    @Override
    public String getRecord() {
        final List<String> record = new ArrayList<String>();
        record.add(name);
        record.add(leader.getName());
        record.add(Integer.toString(kills));
        record.add(Integer.toString(deaths));
        final List<String> memberList = new ArrayList<String>();
        for(SaveablePlayer member : members) {
            memberList.add(member.getName());
        }
        record.add(StringUtils.join(memberList, ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    /**
     *
     */
    public void linkMembers() {
        for(SaveablePlayer member : members) {
            member.setClan(this);
        }
    }

    /**
     * Get members of the clan that are currently online.
     * @return Online members.
     */
    public Set<SaveablePlayer> getOnlineMembers() {
        final Set<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>();
        for(SaveablePlayer member : members) {
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
        return members;
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
        Bukkit.getLogger().info("Implicit Clan.toString()."); // Implicit .toString()
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
        if(members.contains(player)) {
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
        members.add(player);
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
                leader = members.toArray(new SaveablePlayer[members.size()])[0];
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
            member.sendMessage(Color.CLAN + "[" + name + "] " + message);
        }
    }
}
