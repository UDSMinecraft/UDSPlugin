package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsmeta.*;

/**
 * @author UndeadScythes
 */
public enum MemberKey implements MetadataKey {
    GODMODE,
    LOCKDOWN_PASS,
    AFK,
    SHOPPING,
    BUYING,
    HIDDEN,
    WAGER,
    POWERTOOL_ID,
    BOUNTY(true),
    MONEY(true),
    VIP_SPAWNS(true),
    BAIL(true),
    VIP_TIME(true),
    JAIL_TIME(true),
    JAIL_SENTENCE(true),
    TIME_LOGGED(true),
    LAST_ATTACKED,
    LAST_PRIZE_CLAIMED,
    POWERTOOL_COMMAND,
    LOADITEMS(true),
    BACK_POINT,
    CHECK_POINT,
    CHALLENGER,
    WHISPERER,
    CHATROOM,
    CHAT_CHANNEL,
    PET,
    CLAN,
    RANK(true),
    INVENTORY,
    ARMOR,
    LAST_CHATS,
    IGNORES,
    PVP,
    KILLS(true),
    PVP_TIME(true),
    LAST_VECTOR,
    SHOP,
    LAST_LOGINS,
    VIP_FOR_LIFE(true),
    NICK(true);

    private boolean persistent;

    MemberKey() {
        persistent = false;
    }

    MemberKey(boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public String getString() {
        return name();
    }
}
