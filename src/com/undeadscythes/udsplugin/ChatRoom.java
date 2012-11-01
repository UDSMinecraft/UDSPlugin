package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A private chat room on the server.
 * @author UndeadScythes
 */
public class ChatRoom {
    private String name;
    private ArrayList<ExtendedPlayer> members = new ArrayList<ExtendedPlayer>();

    /**
     * Create a brand new private chat room.
     * @param player Player who opened the chat room.
     * @param name Name of the chat room.
     */
    public ChatRoom (ExtendedPlayer player, String name) {
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
    public ArrayList<ExtendedPlayer> getOnlineMembers() {
        ArrayList<ExtendedPlayer> onlineMembers = new ArrayList<ExtendedPlayer>();
        for(ExtendedPlayer member : members) {
            if(member != null) {
                onlineMembers.add(member);
            }
        }
        return onlineMembers;

    }
}
