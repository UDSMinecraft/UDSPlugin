
package com.undeadscythes.udsplugin;

/**
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

    public ClanRank next() {
        if(this.equals(ClanRank.CAPTAIN) || this.equals(ClanRank.LEADER)) {
            return this;
        }
        return getByRank(this.rank + 1);
    }

    public ClanRank prev() {
        if(this.equals(ClanRank.RECRUIT)) {
            return this;
        }
        return getByRank(this.rank - 1);
    }

    private static ClanRank getByRank(final int rank) {
        for(final ClanRank test : ClanRank.values()) {
            if(test.rank == rank) {
                return test;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
