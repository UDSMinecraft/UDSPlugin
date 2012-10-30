package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.configuration.file.*;

/**
 * Storage of config values to help aid maintenance.
 * @author UndeadScythes
 */
public class Config {
    public static long DRAGON_RESPAWN;
    /**
     * The name of the server owner.
     */
    public static String SERVER_OWNER;
    /**
     * The amount of experience to drop when a mob spawner is destroyed.
     */
    public static int SPAWNER_EXP;
    /**
     * The amount of time you are pinned after you have attacked someone.
     */
    public static long PVP_TIME;
    /**
     * The range of the butcher command.
     */
    public static int BUTCHER_RANGE;
    /**
     * The plural form of the in game money.
     */
    public static String CURRENCIES;
    /**
     * The gift to give players when a new player joins.
     */
    public static Material WELCOME_GIFT;
    /**
     * Player join welcome message.
     */
    public static String WELCOME;
    /**
     * A requests TTL.
     */
    public static long REQUEST_TIME;
    /**
     * The radius from center of the explorable world.
     */
    public static int WORLD_BORDER;
    /**
     * The number of free item spawns a VIP gets per day.
     */
    public static int VIP_SPAWNS;
    /**
     * The time a player gets in VIP when rented.
     */
    public static long VIP_TIME;
    /**
     * External storage of the last time a daily task was completed.
     */
    public static long SLOW_TIME;
    /**
     * Cost to get build rights.
     */
    public static int BUILD_COST;
    /**
     * Message to send to mods+ on log on.
     */
    public static String WELCOME_ADMIN;

    /**
     * Load the online 'easy-access' config class with values from the file on disk.
     * @param config
     */
    public static void loadConfig(FileConfiguration config) {
        BUILD_COST = config.getInt("cost.build");
        CURRENCIES = config.getString("currency.plural");
        REQUEST_TIME = config.getLong("request-timeout") * Timer.SECOND;
        SLOW_TIME = config.getLong("auto-save") * Timer.MINUTE;
        VIP_SPAWNS = config.getInt("vip.spawns");
        VIP_TIME = config.getLong("vip.time") * Timer.DAY;
        WELCOME = config.getString("welcome.message");
        WELCOME_GIFT = Material.getMaterial(config.getString("welcome.gift").toUpperCase());
        WORLD_BORDER = config.getInt("range.world");
        WELCOME_ADMIN = config.getString("welcome.admin");
        BUTCHER_RANGE = (int)Math.pow(config.getInt("range.butcher"), 2);
        PVP_TIME = config.getLong("pvp-time") * Timer.SECOND;
        SPAWNER_EXP = config.getInt("exp.spawner");
        SERVER_OWNER = config.getString("server-owner");
        DRAGON_RESPAWN = config.getLong("respawn-dragon") * Timer.MINUTE;
    }
}
