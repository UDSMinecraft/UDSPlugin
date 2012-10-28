package com.undeadscythes.udsplugin1;

/**
 * Storage of config values to help aid maintenance.
 * @author UndeadScythes
 */
public enum Config {
    /**
     * The gift to give players when a new player joins.
     */
    WELCOME_GIFT("welcome-gift"),
    /**
     * Player join welcome message.
     */
    WELCOME("message.welcome"),
    /**
     * A requests TTL.
     */
    REQUEST_TIME("request.time"),
    /**
     * The radius from center of the explorable world.
     */
    WORLD_BORDER("range.world"),
    /**
     * The number of free item spawns a VIP gets per day.
     */
    VIP_SPAWNS("vip.spawns"),
    /**
     * The time a player gets in VIP when rented.
     */
    VIP_TIME("vip.time"),
    /**
     * External storage of the last time a daily task was completed.
     */
    DAY("day"),
    /**
     * Time between auto save and other slow events.
     */
    SLOW_TIME("auto-save"),
    /**
     * Cost to get build rights.
     */
    BUILD_COST("cost.build");

    String link;

    Config(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return this.link;
    }
}
