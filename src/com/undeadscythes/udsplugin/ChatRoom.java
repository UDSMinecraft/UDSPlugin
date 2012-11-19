package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A private chat room on the server.
 * @author UndeadScythes
 */
public class ChatRoom {
    private String name;
    private HashSet<SaveablePlayer> members = new HashSet<SaveablePlayer>();

    /**
     * Create a brand new private chat room.
     * @param player Player who opened the chat room.
     * @param name Name of the chat room.
     */
    public ChatRoom (SaveablePlayer player, String name) {
        this.name = name;
        members.add(player);
    }

    /**
     * Get the name of the chat room.
     * @return The name of the chat room.
     */
    public String getName() {
        return name;
    }

    /**
     * Get members of the chat room that are currently online.
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

    @Override
    public String toString() {
        return name;
    }

    public void addMember(SaveablePlayer player) {
        members.add(player);
    }

    public boolean isMember(SaveablePlayer player) {
        return members.contains(player);
    }

    public void delMember(SaveablePlayer player) {
        members.remove(player);
    }

    public void sendMessage(String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendMessage(Color.PRIVATE + "[" + name + "] " + message);
        }
    }
}
