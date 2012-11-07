package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;

/**
 * Permissions to use within UDSPlugin.
 * @author UndeadScythes
 */
public enum Perm {
    SHOP_ADMIN(PlayerRank.ADMIN, true),
    WE_VIP(PlayerRank.VIP, false),
    CHAT_HELP(PlayerRank.DEFAULT, true),
    ADMIN_HELP(PlayerRank.ADMIN, true),
    MOD_HELP(PlayerRank.MOD, true),
    WARDEN_HELP(PlayerRank.WARDEN, true),
    VIP_HELP(PlayerRank.VIP, true),
    A(PlayerRank.MOD, true),
    ACCEPTRULES(PlayerRank.DEFAULT, false),
    BACK(PlayerRank.MEMBER, true),
    BAN(PlayerRank.MOD, true),
    BOUNTY(PlayerRank.MEMBER, true),
    BROADCAST(PlayerRank.MOD, true),
    BUTCHER(PlayerRank.MOD, true),
    C(PlayerRank.MEMBER, true),
    CALL(PlayerRank.DEFAULT, true),
    CHALLENGE(PlayerRank.MEMBER, true),
    CHECK(PlayerRank.DEFAULT, true),
    CI(PlayerRank.MEMBER, true),
    CITY(PlayerRank.MEMBER, true),
    CLAN(PlayerRank.MEMBER, true),
    DAY(PlayerRank.WARDEN, true),
    DELWARP(PlayerRank.WARDEN, true),
    DEMOTE(PlayerRank.MOD, true),
    ENCHANT(PlayerRank.MOD, true),
    FACE(PlayerRank.DEFAULT, true),
    GIFT(PlayerRank.MEMBER, true),
    GOD(PlayerRank.MOD, true),
    HEAL(PlayerRank.WARDEN, true),
    HELP(PlayerRank.DEFAULT, true),
    HOME(PlayerRank.MEMBER, true),
    HOME_OTHER(PlayerRank.MOD, true),
    I(PlayerRank.VIP, true),
    I_ADMIN(PlayerRank.MOD, true),
    IGNORE(PlayerRank.DEFAULT, true),
    INVSEE(PlayerRank.WARDEN, true),
    JAIL(PlayerRank.WARDEN, true),
    KICK(PlayerRank.MOD, true),
    KIT(PlayerRank.DEFAULT, true),
    LOCKDOWN(PlayerRank.ADMIN, true),
    MAP(PlayerRank.DEFAULT, true),
    ME(PlayerRank.DEFAULT, true),
    MONEY(PlayerRank.DEFAULT, true),
    MONEY_OTHER(PlayerRank.WARDEN, true),
    MONEY_ADMIN(PlayerRank.MOD, true),
    N(PlayerRank.DEFAULT, true),
    NICK(PlayerRank.MEMBER, true),
    NICK_OTHER(PlayerRank.MOD, true),
    NIGHT(PlayerRank.WARDEN, true),
    P(PlayerRank.DEFAULT, true),
    PAYBAIL(PlayerRank.DEFAULT, true),
    PET(PlayerRank.MEMBER, true),
    POWERTOOL(PlayerRank.MOD, true),
    PRIVATE(PlayerRank.DEFAULT, true),
    PROMOTE(PlayerRank.MOD, true),
    R(PlayerRank.DEFAULT, true),
    RAIN(PlayerRank.WARDEN, true),
    REGION(PlayerRank.MOD, true),
    RULES(PlayerRank.DEFAULT, true),
    SCUBA(PlayerRank.DEFAULT, true),
    SERVER(PlayerRank.ADMIN, true),
    SETSPAWN(PlayerRank.ADMIN, true),
    SETWARP(PlayerRank.WARDEN, true),
    SHOP(PlayerRank.MEMBER, true),
    SIGNS(PlayerRank.MOD, true),
    SIT(PlayerRank.DEFAULT, true),
    SPAWN(PlayerRank.DEFAULT, true),
    SPAWNER(PlayerRank.MOD, true),
    STACK(PlayerRank.DEFAULT, true),
    STATS(PlayerRank.DEFAULT, true),
    STORM(PlayerRank.WARDEN, true),
    SUN(PlayerRank.WARDEN, true),
    TP(PlayerRank.MOD, true),
    TELL(PlayerRank.DEFAULT, true),
    TGM(PlayerRank.MOD, true),
    UNBAN(PlayerRank.MOD, true),
    UNJAIL(PlayerRank.WARDEN, true),
    VIP(PlayerRank.MEMBER, true),
    VIP_BUY(PlayerRank.MEMBER, false),
    WE(PlayerRank.MOD, true),
    WARP(PlayerRank.DEFAULT, true),
    WHERE(PlayerRank.DEFAULT, true),
    WHO(PlayerRank.DEFAULT, true),
    WHOIS(PlayerRank.DEFAULT, true),
    XP(PlayerRank.MOD, true),
    Y(PlayerRank.DEFAULT, true);

    private PlayerRank rank;
    private boolean hereditary;

    private Perm(PlayerRank rank, boolean hereditary) {
        this.rank = rank;
        this.hereditary = hereditary;
    }

    public PlayerRank getRank() {
        return rank;
    }

    public boolean hereditary() {
        return hereditary;
    }
}
