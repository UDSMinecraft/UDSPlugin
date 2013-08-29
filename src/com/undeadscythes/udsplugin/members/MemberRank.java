package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public enum MemberRank {
    NEWBIE(ChatColor.WHITE, 0),
    MEMBER(ChatColor.GREEN, 1),
    VIP(ChatColor.DARK_PURPLE, 1),
    WARDEN(ChatColor.AQUA, 2),
    MOD(ChatColor.DARK_AQUA, 3),
    ADMIN(ChatColor.YELLOW, 4),
    OWNER(ChatColor.GOLD, 5);

    private final ChatColor color;
    protected final int ranking;

    private static final String NAME = "Player Rank";

    MemberRank(final ChatColor color, final int rank) {
        this.color = color;
        this.ranking = rank;
    }

    public ChatColor getColor() {
        return color;
    }

    public static MemberRank getByRanking(final int ranking) {
        for(MemberRank rank : values()) {
            if(rank.ranking == ranking) {
                return rank;
            }
        }
        return null;
    }

    public static MemberRank getAbove(final MemberRank rank) {
        return getByRanking(rank.ranking + 1);
    }

    public static MemberRank getBelow(final MemberRank rank) {
        return getByRanking(rank.ranking - 1);
    }

    public static MemberRank getByName(final String name) throws NoEnumFoundException {
        if(name.equals("default")) return NEWBIE; // For back-compat with < 2.43
        for(MemberRank rank : values()) {
            if(rank.name().equals(name.toUpperCase())) {
                return rank;
            }
        }
        throw new NoEnumFoundException(NAME, name);
    }

    public int getID() {
        return ranking;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
