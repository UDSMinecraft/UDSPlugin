package com.undeadscythes.udsplugin;

import java.util.*;
import org.apache.commons.lang.*;

/**
 * A group of players, used for handling PVP.
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    /**
     * File name of clan file.
     */
    public static final String PATH = "clans.csv";

    private String name;
    private SaveablePlayer leader;
    private int kills = 0;
    private int deaths = 0;
    private HashSet<SaveablePlayer> members = new HashSet<SaveablePlayer>();

    /**
     * Initialise a brand new clan.
     * @param name Clan name.
     * @param leader Leader name.
     */
    public Clan(String name, SaveablePlayer leader) {
        this.name = name;
        this.leader = leader;
        members.add(leader);
    }

    /**
     * Initialise a clan from a string record.
     * @param record A line from a save file.
     */
    public Clan(String record) {
        String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        leader = UDSPlugin.getPlayers().get(recordSplit[1]);
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new HashSet<SaveablePlayer>();
        for(String member : recordSplit[4].split(",")) {
            members.add(UDSPlugin.getPlayers().get(member));

        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(leader.getName());
        record.add(kills + "");
        record.add(deaths + "");
        ArrayList<String> memberList = new ArrayList<String>();
        for(SaveablePlayer member : members) {
            memberList.add(member.getName());
        }
        record.add(StringUtils.join(memberList, ","));
        return StringUtils.join(record.toArray(), "\t");
    }

    public void linkMembers() {
        for(SaveablePlayer member : members) {
            member.setClan(this);
        }
    }

    /**
     * Get members of the clan that are currently online.
     * @return Online members.
     */
    public HashSet<SaveablePlayer> getOnlineMembers() {
        HashSet<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>();
        for(SaveablePlayer member : members) {
            if(member != null) {
                onlineMembers.add(member);
            }
        }
        return onlineMembers;
    }

    public HashSet<SaveablePlayer> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void rename(String name) {
        this.name = name;
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
    public void changeName(String name) {
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
    public boolean changeLeader(SaveablePlayer player) {
        if(members.contains(player)) {
            leader = player;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Increment the clan's kills stat.
     */
    public void newKill() {
        kills++;
    }

    /**
     * Increment the clan's deaths stat.
     */
    public void newDeath() {
        deaths++;
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
     * Checks if a player is a member of the clan.
     * @param player Name of the player.
     * @return <code>true</code> if player is a clan member, <code>false</code> otherwise.
     */
    public boolean hasMember(SaveablePlayer player) {
        return members.contains(player);
    }

    /**
     * Add a new clan member.
     * @param player Player name.
     */
    public void addMember(SaveablePlayer player) {
        members.add(player);
    }

    /**
     * Remove a member from the clan.
     * @param player Player name.
     */
    public boolean delMember(SaveablePlayer player) {
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

    public void sendMessage(String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendMessage(Color.CLAN + "[" + name + "] " + message);
        }
    }
}
