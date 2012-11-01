package com.undeadscythes.udsplugin;

/**
 * Permissions to use within UDSPlugin.
 * @author UndeadScythes
 */
public enum Perm {
    A(ExtendedPlayer.Rank.MOD, true),
    ACCEPTRULES(ExtendedPlayer.Rank.DEFAULT, false),
    BACK(ExtendedPlayer.Rank.MEMBER, true),
    BAN(ExtendedPlayer.Rank.MOD, true),
    BOUNTY(ExtendedPlayer.Rank.MEMBER, true),
    BROADCAST(ExtendedPlayer.Rank.MOD, true),
    BUTCHER(ExtendedPlayer.Rank.MOD, true),
    C(ExtendedPlayer.Rank.MEMBER, true),
    CALL(ExtendedPlayer.Rank.DEFAULT, true),
    CHALLENGE(ExtendedPlayer.Rank.MEMBER, true),
    CHECK(ExtendedPlayer.Rank.DEFAULT, true),
    CI(ExtendedPlayer.Rank.MEMBER, true),
    CITY(ExtendedPlayer.Rank.MEMBER, true),
    DAY(ExtendedPlayer.Rank.WARDEN, true),
    DELWARP(ExtendedPlayer.Rank.WARDEN, true),
    DEMOTE(ExtendedPlayer.Rank.MOD, true),
    GIFT(ExtendedPlayer.Rank.MEMBER, true),
    GOD(ExtendedPlayer.Rank.MOD, true),
    HEAL(ExtendedPlayer.Rank.WARDEN, true),
    MONEY(ExtendedPlayer.Rank.DEFAULT, true),
    MONEY_OTHER(ExtendedPlayer.Rank.WARDEN, true),
    MONEY_ADMIN(ExtendedPlayer.Rank.MOD, true),
    NIGHT(ExtendedPlayer.Rank.WARDEN, true),
    PROMOTE(ExtendedPlayer.Rank.MOD, true),
    SETWARP(ExtendedPlayer.Rank.WARDEN, true),
    TGM(ExtendedPlayer.Rank.MOD, true),
    TP(ExtendedPlayer.Rank.MOD, true),
    WARP(ExtendedPlayer.Rank.DEFAULT, true),
    WHO(ExtendedPlayer.Rank.DEFAULT, true),
    WHOIS(ExtendedPlayer.Rank.DEFAULT, true);

    private ExtendedPlayer.Rank rank;
    private boolean inherits;

    private Perm(ExtendedPlayer.Rank rank, boolean inherits) {
        this.rank = rank;
        this.inherits = inherits;
    }

    public ExtendedPlayer.Rank getRank() {
        return rank;
    }

    public boolean inherits() {
        return inherits;
    }
}
