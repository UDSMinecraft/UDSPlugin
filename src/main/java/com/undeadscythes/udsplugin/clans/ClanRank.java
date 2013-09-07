package com.undeadscythes.udsplugin.clans;

/**
 * Ranks for players within clans.
 * 
 * @author UndeadScythes
 */
public enum ClanRank {
    RECRUIT(0),
    CAPTAIN(1),
    LEADER(2);

    private int rank;

    private ClanRank(final int rank) {
        this.rank = rank;
    }

    public final ClanRank next() {
        if(this.equals(ClanRank.CAPTAIN) || this.equals(ClanRank.LEADER)) {
            return this;
        }
        return getByRank(this.rank + 1);
    }

    public final ClanRank prev() {
        if(this.equals(ClanRank.RECRUIT)) {
            return this;
        }
        return getByRank(this.rank - 1);
    }

    private static ClanRank getByRank(final int rank) {
        for(final ClanRank test : ClanRank.values()) {
            if(test.getRank() == rank) {
                return test;
            }
        }
        return null;
    }
    
    private int getRank() {
        return rank;
    }
    
    @Override
    public final String toString() {
        return name().toLowerCase();
    }
}
