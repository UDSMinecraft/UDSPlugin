package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class ChatRoom {
    private final String name;
    private final HashSet<OfflineMember> members;

    public ChatRoom (final OfflineMember player, final String name) {
        this.name = name;
        members = new HashSet<OfflineMember>(Arrays.asList(player));
    }

    public String getName() {
        return name;
    }

    public Set<Member> getMembers() {
        final HashSet<Member> onlineMembers = new HashSet<Member>(members.size());
        for(OfflineMember member : members) {
            try {
                onlineMembers.add(MemberUtils.getOnlineMember(member));
            } catch(PlayerNotOnlineException ex) {}
        }
        return onlineMembers;

    }

    @Override
    public String toString() {
        return name;
    }

    public void addMember(final OfflineMember player) {
        members.add(player);
    }

    public boolean isMember(final OfflineMember player) {
        return members.contains(player);
    }

    public void delMember(final OfflineMember player) {
        members.remove(player);
    }

    public void sendMessage(final String message) {
        for(Member member : getMembers()) {
            member.sendPrivate("[" + name + "] " + message);
        }
    }
}
