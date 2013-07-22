package com.undeadscythes.udsplugin;

/**
 * A reference that points to a particular value of the configuration YAML and
 * any multiplier required to bring it into the correct numerical range.
 * @author UndeadScythes
 */
public enum ConfigRef {
    BLOCK_CREEPERS("block.creeper"),
    BLOCK_ENDERMEN("block.endermen"),
    BLOCK_SILVERFISH("block.silverfish"),
    BLOCK_TNT("block.tnt"),
    BLOCK_WITHER("block.wither"),
    MAP_DATA("map-data"),
    BASE_COST("cost.base"),
    BUILD_COST("cost.build"),
    CITY_COST("cost.city"),
    CLAN_COST("cost.clan"),
    EXPAND_COST("cost.expand"),
    HOME_COST("cost.home"),
    MAP_COST("cost.map"),
    SHOP_COST("cost.shop"),
    VIP_COST("cost.vip"),
    UNDO_COUNT("range.undo"),
    DRAIN_RANGE("range.drain"),
    MOVE_RANGE("range.move"),
    EDIT_RANGE("range.edit"),
    COMPASS_RANGE("range.compass"),
    BUTCHER_RANGE("range.butcher"),
    VIP_SPAWNS("vip.spawns"),
    WORLD_BORDER("range.world"),
    SPAWNER_EXP("exp.spawner"),
    REQUEST_TTL("request-timeout", Timer.SECOND),
    MINECART_TTL("minecart.life", Timer.SECOND),
    PVP_TIME("pvp-time", Timer.SECOND),
    SLOW_TIME("auto-save", Timer.MINUTE),
    DRAGON_RESPAWN("respawn-dragon", Timer.MINUTE),
    VIP_TIME("vip.time", Timer.DAY),
    CURRENCIES("currency.plural"),
    WELCOME_MSG("welcome.message"),
    WELCOME_ADMIN("welcome.admin"),
    SERVER_OWNER("server-owner"),
    CURRENCY("currency.singular"),
    MAIN_WORLD("world-name"),
    WELCOME_GIFT("welcome.gift"),
    SERVER_RULES("server-rules"),
    VIP_WHITELIST("item-whitelist"),
    KITS("kits"),
    MOB_REWARDS("mob-rewards"),
    PISTON_POWER("piston-power"),
    GLOBAL_FLAGS("global-flags"),
    SHARES("inventory-shares"),
    GMAIL_ADDRESS("gmail.email"),
    SKULL("head-drop-chance"),
    GMAIL_PASSWORD("gmail.password");

    private String reference;
    private long multiplier;

    private ConfigRef(final String reference) {
        this.reference = reference;
        this.multiplier = 1;
    }

    private ConfigRef(final String reference, final long multiplier) {
        this.reference = reference;
        this.multiplier = multiplier;
    }

    /**
     * Get the YAML reference point of this configuration value.
     * @return YAML reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Get the multiplier required to bring this value into the correct range.
     * @return Multiplier.
     */
    public long getMultiplier() {
        return multiplier;
    }
}
