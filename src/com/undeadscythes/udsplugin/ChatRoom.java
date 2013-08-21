package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class ChatRoom {
    private final String name;
    private final HashSet<Member> members;

    public ChatRoom (final Member player, final String name) {
        this.name = name;
        members = new HashSet<Member>(Arrays.asList(player));
    }

    public String getName() {
        return name;
    }

    public Set<Member> getMembers() {
        final HashSet<Member> Members = new HashSet<Member>(members.size());
        for(Member member : members) {
            try {
                Members.add(PlayerUtils.getOnlinePlayer(member));
            } catch (PlayerNotOnlineException ex) {}
        }
        return Members;

    }

    @Override
    public String toString() {
        return name;
    }

    public void addMember(final Member player) {
        members.add(player);
    }

    public boolean isMember(final Member player) {
        return members.contains(player);
    }

    public void delMember(final Member player) {
        members.remove(player);
    }

    public void sendMessage(final String message) {
        for(Member member : getMembers()) {
            member.sendPrivate("[" + name + "] " + message);
        }
    }
}
