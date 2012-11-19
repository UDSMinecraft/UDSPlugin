package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.Region.RegionFlag;
import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * Storage of config values to help aid maintenance.
 * @author UndeadScythes
 */
public class Config {
    public static int MINECART_LIFE;
    public static World WORLD;
    public static HashMap<RegionFlag, Boolean> GLOBAL_FLAGS;
    public static HashMap<String, Integer> MOB_REWARDS;
    public static boolean BLOCK_ENDERMAN;
    public static boolean BLOCK_SILVERFISH;
    public static boolean BLOCK_CREEPER;
    public static boolean BLOCK_TNT;
    public static boolean BLOCK_WITHER;
    public static ArrayList<EntityType> HOSTILE_MOBS = new ArrayList<EntityType>(Arrays.asList(EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.ENDER_DRAGON, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.PIG_ZOMBIE, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.WITCH, EntityType.WITHER, EntityType.ZOMBIE));
    public static ArrayList<String> SERVER_RULES;
    public static byte MAP_DATA;
    public static int EXPAND_COST;
    public static int COMPASS_RANGE;
    /**
     * Items that VIP ranks can spawn.
     */
    public static ArrayList<Material> WHITELIST;
    /**
     * Cost to get a map of spawn.
     */
    public static int MAP_COST;
    /**
     * Cost to protect a home area.
     */
    public static int HOME_COST;
    /**
     * Cost to but a city shop.
     */
    public static int SHOP_COST;
    /**
     * Cost to rent VIP rank.
     */
    public static int VIP_COST;
    /**
     * Cost to make a new clan.
     */
    public static int CLAN_COST;
    /**
     * Cost to setup a clan base.
     */
    public static int BASE_COST;
    /**
     * The singular form of the in game money.
     */
    public static String CURRENCY;
    /**
     * The cost to found anew city.
     */
    public static int CITY_COST;
    /**
     * The time between Ender Dragon respawns.
     */
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
    public static ArrayList<Kit> KITS;

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
        CITY_COST = config.getInt("cost.city");
        CURRENCY = config.getString("currency.singular");
        MAP_COST = config.getInt("cost.map");
        HOME_COST = config.getInt("cost.home");
        SHOP_COST = config.getInt("cost.shop");
        VIP_COST = config.getInt("cost.vip");
        CLAN_COST = config.getInt("cost.clan");
        BASE_COST = config.getInt("cost.base");
        WHITELIST = new ArrayList<Material>();
        for(int id : config.getIntegerList("item-whitelist")) {
            WHITELIST.add(Material.getMaterial(id));
        }
        KITS = new ArrayList<Kit>();
        for(String kit : config.getStringList("kits")) {
            String[] kitSplit = kit.split(",");
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            for(Object item : ArrayUtils.subarray(kitSplit, 3, kitSplit.length -1)) {
                items.add(new ItemStack(Material.getMaterial(Integer.parseInt((String)item))));
            }
            KITS.add(new Kit(kitSplit[0], Integer.parseInt(kitSplit[1]), items, PlayerRank.getByName(kitSplit[2])));
        }
        EXPAND_COST = config.getInt("cost.expand");
        MAP_DATA = (byte)config.getInt("map-data");
        SERVER_RULES = new ArrayList<String>(config.getStringList("server-rules"));
        BLOCK_CREEPER = config.getBoolean("block.creeper");
        BLOCK_TNT = config.getBoolean("block.tnt");
        BLOCK_WITHER = config.getBoolean("block.wither");
        BLOCK_ENDERMAN = config.getBoolean("block.enderman");
        BLOCK_SILVERFISH = config.getBoolean("block.silverfish");
        MOB_REWARDS = new HashMap<String, Integer>();
        for(String reward : config.getStringList("mob-rewards")) {
            MOB_REWARDS.put(reward.split(":")[0], Integer.parseInt(reward.split(":")[1]));
        }
        COMPASS_RANGE = config.getInt("range.compass");
        GLOBAL_FLAGS = new HashMap<RegionFlag, Boolean>();
        for(RegionFlag flag : RegionFlag.values()) {
            GLOBAL_FLAGS.put(flag, config.getBoolean("global-flags." + flag.toString()));
        }
        WORLD = Bukkit.getWorld(config.getString("world-name"));
        MINECART_LIFE = config.getInt("minecart.life");
    }
}
