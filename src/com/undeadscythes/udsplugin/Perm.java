package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import org.bukkit.*;

/**
 * Permissions to use within UDSPlugin.
 *
 * @author UndeadScythes
 */
public enum Perm {
    A(MemberRank.MOD),
    ACCEPTRULES(MemberRank.NEWBIE, false),
    ADMIN(MemberRank.ADMIN),
    ADMINCHAT(MemberRank.MOD),
    ADMINMSG(MemberRank.MOD),
    AFK(MemberRank.NEWBIE),
    ANYMODE(MemberRank.MOD),
    BACK(MemberRank.MEMBER),
    BACK_ON_DEATH(MemberRank.VIP),
    BAN(MemberRank.MOD),
    BOUNTY(MemberRank.MEMBER),
    BROADCAST(MemberRank.MOD),
    BUTCHER(MemberRank.MOD),
    BYPASS(MemberRank.MOD),
    C(MemberRank.MEMBER),
    CALL(MemberRank.NEWBIE),
    CHALLENGE(MemberRank.MEMBER),
    CHAT_HELP(MemberRank.NEWBIE),
    CHECK(MemberRank.NEWBIE),
    CHUNK(MemberRank.ADMIN),
    CI(MemberRank.MEMBER),
    CI_OTHER(MemberRank.MOD),
    CITY(MemberRank.MEMBER, GameMode.SURVIVAL),
    CLAN(MemberRank.MEMBER),
    CLAN_BASE_BUILD(MemberRank.MEMBER, GameMode.SURVIVAL),
    COMPASS(MemberRank.WARDEN),
    DAY(MemberRank.WARDEN),
    DEBUG(MemberRank.ADMIN),
    DELWARP(MemberRank.WARDEN),
    DEMOTE(MemberRank.MOD),
    ENCHANT(MemberRank.MOD),
    FACE(MemberRank.NEWBIE),
    GIFT(MemberRank.MEMBER, GameMode.SURVIVAL),
    GOD(MemberRank.MOD),
    HEADDROP(MemberRank.MOD),
    HEAL(MemberRank.WARDEN),
    HELP(MemberRank.NEWBIE),
    HOME(MemberRank.MEMBER),
    HOME_EXPAND(MemberRank.VIP),
    HOME_MAKE(MemberRank.MEMBER, GameMode.SURVIVAL),
    HOME_OTHER(MemberRank.MOD),
    I(MemberRank.VIP),
    IGNORE(MemberRank.NEWBIE),
    INVSEE(MemberRank.WARDEN),
    ITEM(MemberRank.NEWBIE),
    I_ADMIN(MemberRank.MOD),
    JAIL(MemberRank.WARDEN),
    KICK(MemberRank.MOD),
    KIT(MemberRank.NEWBIE, GameMode.SURVIVAL),
    LOCKDOWN(MemberRank.ADMIN),
    MAP(MemberRank.NEWBIE, GameMode.SURVIVAL),
    ME(MemberRank.NEWBIE),
    MINECART(MemberRank.NEWBIE),
    MOD(MemberRank.MOD),
    MOD_HELP(MemberRank.MOD, false),
    MONEY(MemberRank.NEWBIE),
    MONEY_ADMIN(MemberRank.MOD),
    MONEY_OTHER(MemberRank.WARDEN),
    MIDAS(MemberRank.MOD),
    N(MemberRank.NEWBIE),
    NEWBIEMSG(MemberRank.NEWBIE, false),
    NICK(MemberRank.MEMBER),
    NICK_OTHER(MemberRank.MOD),
    NIGHT(MemberRank.WARDEN),
    P(MemberRank.NEWBIE),
    PASSWORD(MemberRank.MEMBER),
    PAPER_COMPLEX(MemberRank.MOD),
    PAPER_SIMPLE(MemberRank.NEWBIE),
    PAYBAIL(MemberRank.NEWBIE),
    PET(MemberRank.MEMBER),
    PLOT(MemberRank.MEMBER, GameMode.CREATIVE),
    PLOT_MANY(MemberRank.ADMIN, GameMode.CREATIVE),
    PLOT_REMOVE(MemberRank.ADMIN, GameMode.CREATIVE),
    PORTAL(MemberRank.ADMIN),
    PORTAL_USE(MemberRank.NEWBIE),
    POWERTOOL(MemberRank.MOD),
    PRIVATE(MemberRank.NEWBIE),
    PRIZE(MemberRank.NEWBIE),
    PROMOTE(MemberRank.MOD),
    PVP(MemberRank.MEMBER),
    R(MemberRank.NEWBIE),
    RAIN(MemberRank.WARDEN),
    REGION(MemberRank.MOD),
    RULES(MemberRank.NEWBIE),
    SCUBA(MemberRank.NEWBIE, GameMode.SURVIVAL),
    SERVER(MemberRank.ADMIN),
    SETSPAWN(MemberRank.ADMIN),
    SETWARP(MemberRank.WARDEN),
    SHAREDINV(MemberRank.MOD),
    SHOP(MemberRank.MEMBER, GameMode.SURVIVAL),
    SHOP_ADMIN(MemberRank.ADMIN, GameMode.SURVIVAL),
    SHOP_ANYWHERE(MemberRank.ADMIN, GameMode.SURVIVAL),
    SHOP_SERVER(MemberRank.MOD, GameMode.SURVIVAL),
    SHOP_SIGN(MemberRank.MEMBER, GameMode.SURVIVAL),
    SIGNS(MemberRank.MOD),
    SIGN_CHECKPOINT(MemberRank.MOD),
    SIGN_ITEM(MemberRank.ADMIN),
    SIGN_MINECART(MemberRank.MOD),
    SIGN_PRIZE(MemberRank.ADMIN),
    SIGN_SPLEEF(MemberRank.MOD),
    SIGN_WARP(MemberRank.MOD),
    SIT(MemberRank.NEWBIE),
    SPAWN(MemberRank.NEWBIE),
    SPAWNER(MemberRank.MOD),
    SPLEEF(MemberRank.NEWBIE),
    STACK(MemberRank.NEWBIE),
    STATS(MemberRank.NEWBIE),
    STORM(MemberRank.WARDEN),
    SUN(MemberRank.WARDEN),
    TELL(MemberRank.NEWBIE),
    TGM(MemberRank.MOD),
    TICKET(MemberRank.NEWBIE),
    TP(MemberRank.MOD),
    UNAVOIDABLE(MemberRank.WARDEN),
    UNBAN(MemberRank.MOD),
    UNJAIL(MemberRank.WARDEN),
    UNKICKABLE(MemberRank.MOD),
    VANISH(MemberRank.MOD),
    VIP(MemberRank.MEMBER),
    VIP_FOR_LIFE(MemberRank.ADMIN),
    VIP_RANK(MemberRank.VIP, false),
    VIP_BUY(MemberRank.MEMBER, false),
    WAND(MemberRank.MEMBER),
    WARDEN(MemberRank.WARDEN),
    WARDEN_HELP(MemberRank.WARDEN, false),
    WARP(MemberRank.NEWBIE),
    WE(MemberRank.WARDEN),
    WE_COPY(MemberRank.MOD),
    WE_DRAIN(MemberRank.MOD),
    WE_EXT(MemberRank.WARDEN),
    WE_LOAD(MemberRank.OWNER),
    WE_MOVE(MemberRank.MOD),
    WE_PASTE(MemberRank.MOD),
    WE_REGEN(MemberRank.MOD),
    WE_REPLACE(MemberRank.MOD),
    WE_SAVE(MemberRank.OWNER),
    WE_SET(MemberRank.MOD),
    WE_UNDO(MemberRank.MOD),
    WE_VIP(MemberRank.VIP, false),
    WHERE(MemberRank.NEWBIE),
    WHO(MemberRank.NEWBIE),
    WHOIS(MemberRank.NEWBIE),
    WORLD(MemberRank.ADMIN),
    XP(MemberRank.MOD),
    Y(MemberRank.NEWBIE);

    public final MemberRank rank;
    private final boolean hereditary;
    private final GameMode mode;

    private Perm(final MemberRank rank, final boolean isHereditary, final GameMode mode) {
        this.rank = rank;
        this.hereditary = isHereditary;
        this.mode = mode;
    }

    private Perm(final MemberRank rank) {
        this(rank, true, null);
    }

    private Perm(final MemberRank rank, final boolean hereditary) {
        this(rank, hereditary, null);
    }

    private Perm(final MemberRank rank, final GameMode mode) {
        this(rank, true, mode);
    }

    public MemberRank getRank() {
        return rank;
    }

    public boolean isHereditary() {
        return hereditary;
    }

    public GameMode getMode() {
        return mode;
    }
}
