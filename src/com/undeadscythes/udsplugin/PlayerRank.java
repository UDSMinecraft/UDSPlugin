package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;
import org.bukkit.*;

/**
 * A player rank granting permission.
 * @author UndeadScythes
 */
public enum PlayerRank {
    NEWBIE(ChatColor.WHITE, 0),
    MEMBER(ChatColor.GREEN, 1),
    VIP(ChatColor.DARK_PURPLE, 1),
    WARDEN(ChatColor.AQUA, 2),
    MOD(ChatColor.DARK_AQUA, 3),
    ADMIN(ChatColor.YELLOW, 4),
    OWNER(ChatColor.GOLD, 5),
    NONE(null, 5);

    private ChatColor color;
    private int ranking;

    PlayerRank(final ChatColor color, final int rank) {
        this.color = color;
        this.ranking = rank;
    }

    /**
        * Get the chat color associated to this rank.
        * @return The rank's chat color.
        */
    public ChatColor getColor() {
        return color;
    }

    /**
        * Get player rank by integer ranking.
        * @param ranking Integer ranking.
        * @return Player rank or <code>null</code> if there is no match.
        */
    public static PlayerRank getByRanking(final int ranking) {
        for(PlayerRank rank : values()) {
            if(rank.ranking == ranking) {
                return rank;
            }
        }
        return null;
    }

    /**
        * Get the rank above this one.
        * @param rank Player rank.
        * @return The rank above.
        */
    public static PlayerRank getAbove(final PlayerRank rank) {
        return getByRanking(rank.ranking + 1);
    }

    /**
        * Get the rank below this one.
        * @param rank Player rank.
        * @return The rank below.
        */
    public static PlayerRank getBelow(final PlayerRank rank) {
        return getByRanking(rank.ranking - 1);
    }

    /**
        * Get player rank by name.
        * @param string Rank name.
        * @return Player rank, <code>null</code> if there is no match.
        */
    public static PlayerRank getByName(final String string) {
        for(PlayerRank rank : values()) {
            if(rank.name().equals(string.toUpperCase())) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
