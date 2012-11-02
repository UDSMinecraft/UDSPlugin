package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.ExtendedPlayer.Rank;

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
    CHALLENGE(Rank.MEMBER, true),
    CHECK(Rank.DEFAULT, true),
    CI(Rank.MEMBER, true),
    CITY(Rank.MEMBER, true),
    DAY(Rank.WARDEN, true),
    DELWARP(Rank.WARDEN, true),
    DEMOTE(Rank.MOD, true),
    ENCHANT(Rank.MOD, true),
    FACE(Rank.DEFAULT, true),
    GIFT(Rank.MEMBER, true),
    GOD(Rank.MOD, true),
    HEAL(Rank.WARDEN, true),
    I(Rank.VIP, true),
    I_ADMIN(Rank.MOD, true),
    IGNORE(Rank.DEFAULT, true),
    JAIL(Rank.WARDEN, true),
    MONEY(Rank.DEFAULT, true),
    MONEY_OTHER(Rank.WARDEN, true),
    MONEY_ADMIN(Rank.MOD, true),
    NIGHT(Rank.WARDEN, true),
    PROMOTE(Rank.MOD, true),
    SETWARP(Rank.WARDEN, true),
    TGM(Rank.MOD, true),
    VIP(Rank.MEMBER, true),
    VIP_BUY(Rank.MEMBER, false),
    TP(Rank.MOD, true),
    WARP(Rank.DEFAULT, true),
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
