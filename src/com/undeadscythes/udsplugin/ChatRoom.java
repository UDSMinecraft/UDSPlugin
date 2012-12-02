package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A private chat room on the server.
 * @author UndeadScythes
 */
public class ChatRoom {
    private final transient String name;
    private final transient Set<SaveablePlayer> members;

    /**
     * Create a brand new private chat room.
     * @param player Player who opened the chat room.
     * @param name Name of the chat room.
     */
    public ChatRoom (final SaveablePlayer player, final String name) {
        this.name = name;
        members = new HashSet<SaveablePlayer>(Arrays.asList(player));
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
    public Set<SaveablePlayer> getOnlineMembers() {
        final HashSet<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>();
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

    /**
     * Add a new chat room member.
     * @param player Player to add.
     */
    public void addMember(final SaveablePlayer player) {
        members.add(player);
    }

    /**
     * Check if a player is in this chat room.
     * @param player Player to check.
     * @return <code>true</code> if the player is in the chat room, <code>false</code> otherwise.
     */
    public boolean isMember(final SaveablePlayer player) {
        return members.contains(player);
    }

    /**
     * Remove a player from the chat room.
     * @param player Player to remove.
     */
    public void delMember(final SaveablePlayer player) {
        members.remove(player);
    }

    /**
     * Send a message to players in the chat room.
     * @param message Message to send.
     */
    public void sendMessage(final String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendMessage(Color.PRIVATE + "[" + name + "] " + message);
        }
    }
}
