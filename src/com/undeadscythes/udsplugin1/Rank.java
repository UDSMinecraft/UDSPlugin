package com.undeadscythes.udsplugin1;

/**
 * A player rank granting permission.
 * @author UndeadScythes
 */
public enum Rank {
    /**
     * Basic player rank.
     */
    DEFAULT,
    /**
     * Player with build rights.
     */
    MEMBER,
    /**
     * Donating or long term player.
     */
    VIP,
    /**
     * Trustee.
     */
    WARDEN,
    /**
     * Player moderator.
     */
    MOD,
    /**
     * Server administrator.
     */
    ADMIN,
    /**
     * Server owner.
     */
    OWNER;
}
