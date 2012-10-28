package com.undeadscythes.udsplugin1;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;

/**
 * A group of players, used for handling PVP.
 * @author UndeadScythes
 */
public class Clan implements Saveable {
    /**
     * File name of clan file.
     */
    public static String PATH = "clans.csv";

    private String name;
    private String leader;
    private int kills;
    private int deaths;
    private ArrayList<String> members;

    /**
     * Initialise a brand new clan.
     * @param name Clan name.
     * @param leader Leader name.
     */
    public Clan(String name, String leader) {
        this.name = name;
        this.leader = leader;
        members = new ArrayList<String>();
        members.add(leader);
    }

    /**
     * Initialise a clan from a string record.
     * @param record A line from a save file.
     */
    public Clan(String record) {
        String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        leader = recordSplit[1];
        kills = Integer.parseInt(recordSplit[2]);
        deaths = Integer.parseInt(recordSplit[3]);
        members = new ArrayList<String>(Arrays.asList(recordSplit[4].split(",")));
    }

    /**
     * @inheritDoc
     */
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(name);
        record.add(leader);
        record.add(kills + "");
        record.add(deaths + "");
        record.add(StringUtils.join(members.toArray(), ","));
        return StringUtils.join(record.toArray(), "\t");
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
    public String getLeader() {
        return leader;
    }

    /**
     * Change the clan leader to another member of the clan.
     * @param name Name of the new leader.
     * @return <code>true</code> if successful, <code>false</code> otherwise.
     */
    public boolean changeLeader(String name) {
        if(members.contains(name)) {
            leader = name;
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
     * @param name Name of the player.
     * @return <code>true</code> if player is a clan member, <code>false</code> otherwise.
     */
    public boolean hasMember(String name) {
        return members.contains(name);
    }

    /**
     * Add a new clan member.
     * @param name Player name.
     */
    public void addMember(String name) {
        members.add(name);
    }

    /**
     * Remove a member from the clan.
     * @param name Player name.
     */
    public void delMember(String name) {
        members.remove(name);
    }
}
