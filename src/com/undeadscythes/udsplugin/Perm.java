package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Permissions to use within UDSPlugin.
 * @author UndeadScythes
 */
public enum Perm {
    A(PlayerRank.MOD),
    ACCEPTRULES(PlayerRank.DEFAULT, false),
    ADMIN(PlayerRank.ADMIN),
    AFK(PlayerRank.DEFAULT),
    BACK(PlayerRank.MEMBER),
    BACK_ON_DEATH(PlayerRank.VIP),
    BAN(PlayerRank.MOD),
    BOUNTY(PlayerRank.MEMBER),
    BROADCAST(PlayerRank.MOD),
    BUTCHER(PlayerRank.MOD),
    BYPASS(PlayerRank.MOD),
    C(PlayerRank.MEMBER),
    CALL(PlayerRank.DEFAULT),
    CHALLENGE(PlayerRank.MEMBER),
    CHAT_HELP(PlayerRank.DEFAULT),
    CHECK(PlayerRank.DEFAULT),
    CHUNK(PlayerRank.ADMIN),
    CI(PlayerRank.MEMBER),
    CITY(PlayerRank.MEMBER),
    CLAN(PlayerRank.MEMBER),
    COMPASS(PlayerRank.WARDEN),
    DAY(PlayerRank.WARDEN),
    DEBUG(PlayerRank.ADMIN),
    DELWARP(PlayerRank.WARDEN),
    DEMOTE(PlayerRank.MOD),
    ENCHANT(PlayerRank.MOD),
    FACE(PlayerRank.DEFAULT),
    GIFT(PlayerRank.MEMBER),
    GOD(PlayerRank.MOD),
    HEAL(PlayerRank.WARDEN),
    HELP(PlayerRank.DEFAULT),
    HOME(PlayerRank.MEMBER),
    HOME_OTHER(PlayerRank.MOD),
    I(PlayerRank.VIP),
    IGNORE(PlayerRank.DEFAULT),
    INVSEE(PlayerRank.WARDEN),
    ITEM(PlayerRank.DEFAULT),
    I_ADMIN(PlayerRank.MOD),
    JAIL(PlayerRank.WARDEN),
    KICK(PlayerRank.MOD),
    KIT(PlayerRank.DEFAULT),
    LOCKDOWN(PlayerRank.ADMIN),
    MAP(PlayerRank.DEFAULT),
    ME(PlayerRank.DEFAULT),
    MINECART(PlayerRank.DEFAULT),
    MOD(PlayerRank.MOD),
    MOD_HELP(PlayerRank.MOD, false),
    MONEY(PlayerRank.DEFAULT),
    MONEY_ADMIN(PlayerRank.MOD),
    MONEY_OTHER(PlayerRank.WARDEN),
    MIDAS(PlayerRank.MOD),
    N(PlayerRank.DEFAULT),
    NICK(PlayerRank.MEMBER),
    NICK_OTHER(PlayerRank.MOD),
    NIGHT(PlayerRank.WARDEN),
    P(PlayerRank.DEFAULT),
    PAPER_COMPLEX(PlayerRank.MOD),
    PAPER_SIMPLE(PlayerRank.DEFAULT),
    PAYBAIL(PlayerRank.DEFAULT),
    PET(PlayerRank.MEMBER),
    PORTAL(PlayerRank.ADMIN),
    PORTAL_USE(PlayerRank.DEFAULT),
    POWERTOOL(PlayerRank.MOD),
    PRIVATE(PlayerRank.DEFAULT),
    PRIZE(PlayerRank.DEFAULT),
    PROMOTE(PlayerRank.MOD),
    R(PlayerRank.DEFAULT),
    RAIN(PlayerRank.WARDEN),
    REGION(PlayerRank.MOD),
    RULES(PlayerRank.DEFAULT),
    SCUBA(PlayerRank.DEFAULT),
    SERVER(PlayerRank.ADMIN),
    SETSPAWN(PlayerRank.ADMIN),
    SETWARP(PlayerRank.WARDEN),
    SHOP(PlayerRank.MEMBER),
    SHOP_ADMIN(PlayerRank.ADMIN),
    SHOP_SERVER(PlayerRank.MOD),
    SHOP_SIGN(PlayerRank.MEMBER),
    SIGNS(PlayerRank.MOD),
    SIGN_CHECKPOINT(PlayerRank.MOD),
    SIGN_ITEM(PlayerRank.ADMIN),
    SIGN_MINECART(PlayerRank.MOD),
    SIGN_PRIZE(PlayerRank.ADMIN),
    SIGN_SPLEEF(PlayerRank.MOD),
    SIGN_WARP(PlayerRank.MOD),
    SIT(PlayerRank.DEFAULT),
    SPAWN(PlayerRank.DEFAULT),
    SPAWNER(PlayerRank.MOD),
    SPLEEF(PlayerRank.DEFAULT),
    STACK(PlayerRank.DEFAULT),
    STATS(PlayerRank.DEFAULT),
    STORM(PlayerRank.WARDEN),
    SUN(PlayerRank.WARDEN),
    TELL(PlayerRank.DEFAULT),
    TGM(PlayerRank.MOD),
    TICKET(PlayerRank.DEFAULT),
    TP(PlayerRank.MOD),
    UNBAN(PlayerRank.MOD),
    UNJAIL(PlayerRank.WARDEN),
    VANISH(PlayerRank.MOD),
    VIP(PlayerRank.VIP, false),
    VIP_BUY(PlayerRank.MEMBER, false),
    VIP_HELP(PlayerRank.VIP, false),
    WAND(PlayerRank.MEMBER),
    WARDEN(PlayerRank.WARDEN),
    WARDEN_HELP(PlayerRank.WARDEN, false),
    WARP(PlayerRank.DEFAULT),
    WE(PlayerRank.WARDEN),
    WE_COPY(PlayerRank.MOD),
    WE_DRAIN(PlayerRank.MOD),
    WE_EXT(PlayerRank.WARDEN),
    WE_LOAD(PlayerRank.OWNER),
    WE_MOVE(PlayerRank.MOD),
    WE_PASTE(PlayerRank.MOD),
    WE_REGEN(PlayerRank.MOD),
    WE_REPLACE(PlayerRank.MOD),
    WE_SAVE(PlayerRank.OWNER),
    WE_SET(PlayerRank.MOD),
    WE_UNDO(PlayerRank.MOD),
    WE_VIP(PlayerRank.VIP, false),
    WHERE(PlayerRank.DEFAULT),
    WHO(PlayerRank.DEFAULT),
    WHOIS(PlayerRank.DEFAULT),
    WORLD(PlayerRank.ADMIN),
    XP(PlayerRank.MOD),
    Y(PlayerRank.DEFAULT);

    private PlayerRank rank;
    private boolean hereditary;

    private Perm(final PlayerRank rank, final boolean hereditary) {
        this.rank = rank;
        this.hereditary = hereditary;
    }

    private Perm(final PlayerRank rank) {
        this(rank, true);
    }

    /**
     * Get the rank assigned to the permission.
     * @return
     */
    public PlayerRank getRank() {
        return rank;
    }

    /**
     * Check if a permission is hereditary.
     * @return
     */
    public boolean isHereditary() {
        return hereditary;
    }
}
