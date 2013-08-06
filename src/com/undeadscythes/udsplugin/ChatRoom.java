package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A private chat room on the server.
 * 
 * @author UndeadScythes
 */
public class ChatRoom {
    private final String name;
    private final HashSet<SaveablePlayer> members;

    public ChatRoom (final SaveablePlayer player, final String name) {
        this.name = name;
        members = new HashSet<SaveablePlayer>(Arrays.asList(player));
    }

    public final String getName() {
        return name;
    }

    public final Set<SaveablePlayer> getOnlineMembers() {
        final HashSet<SaveablePlayer> onlineMembers = new HashSet<SaveablePlayer>(members.size());
        for(SaveablePlayer member : members) {
            if(member.isOnline()) {
                onlineMembers.add(member);
            }
        }
        return onlineMembers;

    }

    @Override
    public final String toString() {
        return name;
    }

    public final void addMember(final SaveablePlayer player) {
        members.add(player);
    }

    public final boolean isMember(final SaveablePlayer player) {
        return members.contains(player);
    }

    public final void delMember(final SaveablePlayer player) {
        members.remove(player);
    }

    public final void sendMessage(final String message) {
        for(SaveablePlayer member : getOnlineMembers()) {
            member.sendPrivate("[" + name + "] " + message);
        }
    }
}
