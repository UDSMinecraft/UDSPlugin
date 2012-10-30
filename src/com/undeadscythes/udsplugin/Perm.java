package com.undeadscythes.udsplugin;

/**
 * Permissions to use within UDSPlugin.
 * @author UndeadScythes
 */
public enum Perm {
    A(Rank.MOD, true),
    ACCEPTRULES(Rank.DEFAULT, false),
    BACK(Rank.MEMBER, true),
    BAN(Rank.MOD, true),
    BOUNTY(Rank.MEMBER, true),
    BROADCAST(Rank.MOD, true),
    BUTCHER(Rank.MOD, true),
    C(Rank.MEMBER, true),
    CALL(Rank.DEFAULT, true),
    DAY(Rank.WARDEN, true),
    DELWARP(Rank.WARDEN, true),
    GOD(Rank.MOD, true),
    HEAL(Rank.WARDEN, true),
    NIGHT(Rank.WARDEN, true),
    SETWARP(Rank.WARDEN, true),
    WHO(Rank.DEFAULT, true),
    WHOIS(Rank.DEFAULT, true);

    private Rank rank;
    private boolean inherits;

    private Perm(Rank rank, boolean inherits) {
        this.rank = rank;
        this.inherits = inherits;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean inherits() {
        return inherits;
    }
}
