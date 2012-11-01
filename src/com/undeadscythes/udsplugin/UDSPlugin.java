package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.commands.*;
import com.undeadscythes.udsplugin.eventhandlers.*;
import java.io.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.*;

/**
 * The main plugin. The heart of UDSPlugin.
 * @author UndeadScythe
 */
public class UDSPlugin extends JavaPlugin {
    /**
     * A vector that defines the center of a block.
     */
    public static Vector HALF_BLOCK = new Vector(.5, .5, .5);
    /**
     * Where the issue/suggestion tickets are stored.
     */
    public static String TICKET_PATH = "tickets.txt";
    public static String TIMES_PATH = "times.data";

    /**
     * Whether the server is in lockdown mode.
     */
    public static boolean serverInLockdown;

    public static long LAST_ENDER_DEATH;
    public static long LAST_DAILY_EVENT;

    private static SaveableHashMap clans;
    private static SaveableHashMap players;
    private static SaveableHashMap regions;
    private static SaveableHashMap warps;
    private static MatchableHashMap<ChatRoom> chatRooms;
    private static MatchableHashMap<Request> requests;
    private static MatchableHashMap<Session> sessions;
    private static MatchableHashMap<Region> quarries;
    private static MatchableHashMap<Region> homes;
    private static MatchableHashMap<Region> shops;
    private static MatchableHashMap<Region> bases;
    private static MatchableHashMap<Region> cities;
    private static MatchableHashMap<Region> arenas;
    private static MatchableHashMap<ExtendedPlayer> vips;
    private static MatchableHashMap<ExtendedPlayer> onlinePlayers;
    private static File DATA_PATH = new File("plugins/UDSPlugin1/data");
    private Timer timer;

    /**
     * Used for testing in NetBeans.
     * @param args
     */
    public static void main(final String[] args) {}

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        DATA_PATH.mkdirs();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        Config.loadConfig(getConfig());
        getLogger().info(Message.CONFIG_LOADED.toString());
        clans = new SaveableHashMap();
        players = new SaveableHashMap();
        regions = new SaveableHashMap();
        warps = new SaveableHashMap();
        chatRooms = new MatchableHashMap<ChatRoom>();
        requests = new MatchableHashMap<Request>();
        sessions = new MatchableHashMap<Session>();
        quarries = new MatchableHashMap<Region>();
        homes = new MatchableHashMap<Region>();
        shops = new MatchableHashMap<Region>();
        bases = new MatchableHashMap<Region>();
        cities = new MatchableHashMap<Region>();
        arenas = new MatchableHashMap<Region>();
        vips = new MatchableHashMap<ExtendedPlayer>();
        onlinePlayers = new MatchableHashMap<ExtendedPlayer>();
        try {
            loadFiles();
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            timer = new Timer();
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 100, 100);
        getLogger().info(Message.TIMER_STARTED.toString());
        setCommandExecutors();
        getLogger().info(Message.COMMANDS_REGISTERED.toString());
        registerEvents();
        getLogger().info(Message.EVENTS_REGISTERED.toString());
        addRecipes();
        getLogger().info(Message.RECIPES_ADDED.toString());
        String message = getName() + " version " + this.getDescription().getVersion() + " enabled.";
        getLogger().info(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        try {
            saveFiles();
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        String message = getName() + " disabled.";
        getLogger().info(message);
    }

    /**
     * Saves all the listed objects to file.
     * @throws IOException When a file can't be opened.
     */
    public static void saveFiles() throws IOException {
        clans.save(DATA_PATH + File.separator + Clan.PATH);
        players.save(DATA_PATH + File.separator + ExtendedPlayer.PATH);
        regions.save(DATA_PATH + File.separator + Region.PATH);
        warps.save(DATA_PATH + File.separator + Warp.PATH);
        BufferedWriter file = new BufferedWriter(new FileWriter(DATA_PATH + File.separator + UDSPlugin.TIMES_PATH));
        file.write(Long.toString(LAST_DAILY_EVENT));
        file.newLine();
        file.write(Long.toString(LAST_ENDER_DEATH));
        file.close();
    }

    /**
     * Loads the listed files from file.
     * @throws FileNotFoundException When a file can't be accessed.
     * @throws IOException When a file can't be read from.
     */
    private void loadFiles() throws IOException {
        BufferedReader file;
        String nextLine;
        String message;
        int count = 0;
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Clan.PATH));
            while((nextLine = file.readLine()) != null) {
                clans.put(nextLine.split("\t", 1)[0], new Clan(nextLine));
                count++;
            }
            file.close();
            message = count + " clans loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info(Message.NO_CLAN_FILE.toString());
        }
        try {
            count = 0;
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + ExtendedPlayer.PATH));
            while((nextLine = file.readLine()) != null) {
                ExtendedPlayer player = new ExtendedPlayer(nextLine);
                players.put(nextLine.split("\t")[0], player);
                if(player.getVIPTime() > 0) {
                    vips.put(player.getName(), player);
                }
            }
            file.close();
            message = count + " players loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info(Message.NO_PLAYER_FILE.toString());
        }
        try {
            count = 0;
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Region.PATH));
            while((nextLine = file.readLine()) != null) {
                Region region = new Region(nextLine);
                regions.put(region.getName(), region);
                if(region.getType().equals(Region.Type.BASE)) {
                    bases.put(region.getName(), region);
                } else if(region.getType().equals(Region.Type.HOME)) {
                    homes.put(region.getName(), region);
                } else if(region.getType().equals(Region.Type.QUARRY)) {
                    quarries.put(region.getName(), region);
                } else if(region.getType().equals(Region.Type.SHOP)) {
                    shops.put(region.getName(), region);
                } else if(region.getType().equals(Region.Type.CITY)) {
                    cities.put(region.getName(), region);
                } else if(region.getType().equals(Region.Type.ARENA)) {
                    arenas.put(region.getName(), region);
                }
            }
            file.close();
            message = count + " regions loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info(Message.NO_REGION_FILE.toString());
        }
        try {
            count = 0;
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Warp.PATH));
            while((nextLine = file.readLine()) != null) {
                warps.put(nextLine.split("\t", 1)[0], new Warp(nextLine));
            }
            file.close();
            message = count + " warps loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info(Message.NO_WARP_FILE.toString());
        }
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + TIMES_PATH));
            LAST_DAILY_EVENT = Long.parseLong(file.readLine());
            LAST_ENDER_DEATH = Long.parseLong(file.readLine());
            file.close();
        } catch (FileNotFoundException ex) {
            LAST_DAILY_EVENT = System.currentTimeMillis();
        }
    }

    /**
     * Connects the commands with their executors.
     */
    private void setCommandExecutors() {
        getCommand("a").setExecutor(new ACmd());
        getCommand("acceptrules").setExecutor(new AcceptRulesCmd());
        getCommand("back").setExecutor(new BackCmd());
        getCommand("ban").setExecutor(new BanCmd());
        getCommand("bounty").setExecutor(new BountyCmd());
        getCommand("broadcast").setExecutor(new BroadcastCmd());
        getCommand("butcher").setExecutor(new ButcherCmd());
        getCommand("c").setExecutor(new CCmd());
        getCommand("call").setExecutor(new CallCmd());
        getCommand("challenge").setExecutor(new ChallengeCmd());
        getCommand("check").setExecutor(new CheckCmd());
        getCommand("ci").setExecutor(new CiCmd());
        getCommand("city").setExecutor(new CityCmd());
//        getCommand("clan").setExecutor(new ClanCmd(this));
        getCommand("day").setExecutor(new DayCmd());
        getCommand("delwarp").setExecutor(new DelWarpCmd());
        getCommand("demote").setExecutor(new DemoteCmd());
//        getCommand("enchant").setExecutor(new Enchant(this));
//        getCommand("face").setExecutor(new Face());
        getCommand("gift").setExecutor(new GiftCmd());
        getCommand("god").setExecutor(new GodCmd());
        getCommand("heal").setExecutor(new HealCmd());
//        getCommand("help").setExecutor(new Help(this));
//        getCommand("home").setExecutor(new Home(this));
//        getCommand("i").setExecutor(new I(this));
//        getCommand("ignore").setExecutor(new Ignore(this));
//        getCommand("invsee").setExecutor(new InvSee(this));
//        getCommand("jail").setExecutor(new Jail(this));
//        getCommand("kick").setExecutor(new Kick(this));
//        getCommand("kit").setExecutor(new Kit(this));
//        getCommand("lockdown").setExecutor(new Lockdown(this));
//        getCommand("map").setExecutor(new Map(this));
//        getCommand("me").setExecutor(new Me(this));
        getCommand("money").setExecutor(new MoneyCmd());
//        getCommand("n").setExecutor(new N(this));
//        getCommand("nick").setExecutor(new Nick(this));
        getCommand("night").setExecutor(new NightCmd());
//        getCommand("p").setExecutor(new P(this));
//        getCommand("paybail").setExecutor(new PayBail());
//        getCommand("pet").setExecutor(new Pet(this));
//        getCommand("powertool").setExecutor(new Powertool());
//        getCommand("private").setExecutor(new Private(this));
        getCommand("promote").setExecutor(new PromoteCmd());
//        getCommand("r").setExecutor(new R(this));
//        getCommand("rain").setExecutor(new Rain(this));
//        getCommand("region").setExecutor(new RegionCmd(this));
//        getCommand("rules").setExecutor(new Rules(this));
//        getCommand("scuba").setExecutor(new Scuba(this));
//        getCommand("server").setExecutor(new Server(this));
//        getCommand("setspawn").setExecutor(new SetSpawn(this));
        getCommand("setwarp").setExecutor(new SetWarpCmd());
//        getCommand("shop").setExecutor(new Shop(this));
//        getCommand("signs").setExecutor(new Signs(this));
//        getCommand("sit").setExecutor(new Sit(this));
//        getCommand("spawn").setExecutor(new Spawn(this));
//        getCommand("spawner").setExecutor(new Spawner(this));
//        getCommand("stack").setExecutor(new Stack(this));
//        getCommand("stats").setExecutor(new Stats(this));
//        getCommand("storm").setExecutor(new Storm(this));
//        getCommand("sun").setExecutor(new Sun(this));
//        getCommand("swearjar").setExecutor(new Swearjar(this));
        getCommand("tp").setExecutor(new TPCmd());
//        getCommand("tell").setExecutor(new Tell(this));
        getCommand("tgm").setExecutor(new TGMCmd());
//        getCommand("unban").setExecutor(new UnBan(this));
//        getCommand("unjail").setExecutor(new UnJail(this));
//        getCommand("unnick").setExecutor(new UnNick(this));
//        getCommand("vip").setExecutor(new VIP(this));
//        getCommand("we").setExecutor(new WE(this));
        getCommand("warp").setExecutor(new WarpCmd());
//        getCommand("where").setExecutor(new Where(this));
        getCommand("who").setExecutor(new WhoCmd());
        getCommand("whois").setExecutor(new WhoIsCmd());
//        getCommand("xp").setExecutor(new XP(this));
//        getCommand("y").setExecutor(new Y());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
//        getServer().getPluginManager().registerEvents(new BlockBurn(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockFade(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockForm(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockFromTo(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockIgnite(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockPhysics(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new BlockPistonExtend(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockPistonRetract(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockPlace(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockRedstone(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockSpread(), plugin);
//        getServer().getPluginManager().registerEvents(new CreatureSpawn(), plugin);
//        getServer().getPluginManager().registerEvents(new EntityBlockForm(), plugin);
//        getServer().getPluginManager().registerEvents(new EntityChangeBlock(), plugin);
//        getServer().getPluginManager().registerEvents(new EntityDamage(), plugin);
//        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new EntityDeath(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new EntityExplode(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new EntityInteract(), plugin);
//        getServer().getPluginManager().registerEvents(new InventoryClick(), plugin);
//        getServer().getPluginManager().registerEvents(new InventoryOpen(), plugin);
//        getServer().getPluginManager().registerEvents(new PlayerBucketEmpty(), plugin);
//        getServer().getPluginManager().registerEvents(new PlayerDeath(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new PlayerInteract(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
//        getServer().getPluginManager().registerEvents(new PlayerPortal(), plugin);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
//        getServer().getPluginManager().registerEvents(new PlayerRespawn(plugin), plugin);
//        getServer().getPluginManager().registerEvents(new SignChange(), plugin);
//        getServer().getPluginManager().registerEvents(new VehicleDestroy(), plugin);
//        getServer().getPluginManager().registerEvents(new VehicleEntityCollision(), plugin);
//        getServer().getPluginManager().registerEvents(new VehicleExit(), plugin);
    }

    private void addRecipes() {
        ShapedRecipe mossyStoneBrick = new ShapedRecipe(new ItemStack(98, 1, (short) 0, (byte) 1)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.VINE).setIngredient('B', new MaterialData(98, (byte) 0));
        this.getServer().addRecipe(mossyStoneBrick);
        ShapelessRecipe crackedStoneBrick = new ShapelessRecipe(new ItemStack(98, 1, (short) 0, (byte) 2)).addIngredient(Material.WOOD_PICKAXE).addIngredient(new MaterialData(98, (byte) 0));
        this.getServer().addRecipe(crackedStoneBrick);
        ShapedRecipe circleStoneBrick = new ShapedRecipe(new ItemStack(98, 8, (short) 0, (byte) 3)).shape("AAA", "A A", "AAA").setIngredient('A', new MaterialData(98, (byte) 0));
        this.getServer().addRecipe(circleStoneBrick);
        ShapedRecipe mossyCobbleStone = new ShapedRecipe(new ItemStack(48, 1)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.VINE).setIngredient('B', Material.COBBLESTONE);
        this.getServer().addRecipe(mossyCobbleStone);
        ShapedRecipe iceBlock = new ShapedRecipe(new ItemStack(79, 1)).shape(" A ", "ABA", " A ").setIngredient('A', Material.SNOW_BALL).setIngredient('B', Material.WATER_BUCKET);
        this.getServer().addRecipe(iceBlock);
        ShapedRecipe chainHelmet = new ShapedRecipe(new ItemStack(302, 1)).shape("AAA", "A A").setIngredient('A', Material.FLINT);
        this.getServer().addRecipe(chainHelmet);
        ShapedRecipe chainChest = new ShapedRecipe(new ItemStack(303, 1)).shape("A A", "AAA" ,"AAA").setIngredient('A', Material.FLINT);
        this.getServer().addRecipe(chainChest);
        ShapedRecipe chainLegs = new ShapedRecipe(new ItemStack(304, 1)).shape("AAA", "A A", "A A").setIngredient('A', Material.FLINT);
        this.getServer().addRecipe(chainLegs);
        ShapedRecipe chainBoots = new ShapedRecipe(new ItemStack(305, 1)).shape("A A", "A A").setIngredient('A', Material.FLINT);
        this.getServer().addRecipe(chainBoots);
        ShapedRecipe grassBlock = new ShapedRecipe(new ItemStack(Material.GRASS, 1)).shape("A", "B").setIngredient('A', new MaterialData(31, (byte) 1)).setIngredient('B', Material.DIRT);
        this.getServer().addRecipe(grassBlock);
        ShapedRecipe snowLayer = new ShapedRecipe(new ItemStack(Material.SNOW, 1)).shape("AAA").setIngredient('A', Material.SNOW_BALL);
        this.getServer().addRecipe(snowLayer);
        ShapedRecipe creeperEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 50)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.SULPHUR);
        this.getServer().addRecipe(creeperEgg);
        ShapedRecipe skeletonEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 51)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.BONE);
        this.getServer().addRecipe(skeletonEgg);
        ShapedRecipe spiderEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 52)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.SPIDER_EYE);
        this.getServer().addRecipe(spiderEgg);
        ShapedRecipe zombieEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 54)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.ROTTEN_FLESH);
        this.getServer().addRecipe(zombieEgg);
        ShapedRecipe slimeEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 55)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.SLIME_BALL);
        this.getServer().addRecipe(slimeEgg);
        ShapedRecipe ghastEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 56)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.GHAST_TEAR);
        this.getServer().addRecipe(ghastEgg);
        ShapedRecipe pigZombieEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 57)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.GOLD_NUGGET);
        this.getServer().addRecipe(pigZombieEgg);
        ShapedRecipe endermanEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 58)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.ENDER_PEARL);
        this.getServer().addRecipe(endermanEgg);
        ShapedRecipe caveSpiderEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 59)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.STRING);
        this.getServer().addRecipe(caveSpiderEgg);
        ShapedRecipe silverFishEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 60)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.STONE);
        this.getServer().addRecipe(silverFishEgg);
        ShapedRecipe blazeEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 61)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.BLAZE_ROD);
        this.getServer().addRecipe(blazeEgg);
        ShapedRecipe magmaCubeEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 62)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.MAGMA_CREAM);
        this.getServer().addRecipe(magmaCubeEgg);
        ShapedRecipe pigEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 90)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.PORK);
        this.getServer().addRecipe(pigEgg);
        ShapedRecipe sheepEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 91)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.WOOL);
        this.getServer().addRecipe(sheepEgg);
        ShapedRecipe cowEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 92)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.LEATHER);
        this.getServer().addRecipe(cowEgg);
        ShapedRecipe chickenEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 93)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.FEATHER);
        this.getServer().addRecipe(chickenEgg);
        ShapedRecipe squidEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 94)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.INK_SACK);
        this.getServer().addRecipe(squidEgg);
        ShapedRecipe wolfEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 95)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.WHEAT);
        this.getServer().addRecipe(wolfEgg);
        ShapedRecipe mooshroomEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 96)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.RED_MUSHROOM);
        this.getServer().addRecipe(mooshroomEgg);
        ShapedRecipe villagerEgg = new ShapedRecipe(new ItemStack(383, 1, (short) 0, (byte) 120)).shape("AAA", "ABA", "AAA").setIngredient('A', Material.GOLD_BLOCK).setIngredient('B', Material.RED_ROSE);
        this.getServer().addRecipe(villagerEgg);
        ShapedRecipe webBlock = new ShapedRecipe(new ItemStack(Material.WEB)).shape("AAA", "A A", "AAA").setIngredient('A', Material.STRING);
        this.getServer().addRecipe(webBlock);
    }

    /**
     * Grab the chat rooms map.
     * @return Chat rooms map.
     */
    static public MatchableHashMap<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    /**
     * Grab and cast the clans map.
     * @return Clans map.
     */
    static public MatchableHashMap<Clan> getClans() {
        return (MatchableHashMap)clans;
    }

    /**
     * Grab and cast the players map.
     * @return Players map.
     */
    static public MatchableHashMap<ExtendedPlayer> getPlayers() {
        return (MatchableHashMap)players;
    }

    /**
     * Grab and cast the regions map.
     * @return Regions map.
     */
    static public MatchableHashMap<Region> getRegions() {
        return (MatchableHashMap)regions;
    }

    /**
     * Grab the requests map.
     * @return Requests map.
     */
    static public MatchableHashMap<Request> getRequests() {
        return requests;
    }

    /**
     * Grab and cast the sessions map.
     * @return Sessions map.
     */
    static public MatchableHashMap<Session> getSessions() {
        return sessions;
    }

    /**
     * Grab and cast the warps map.
     * @return Warps map.
     */
    static public MatchableHashMap<Warp> getWarps() {
        return (MatchableHashMap)warps;
    }

    /**
     * Grab the quarries map.
     * @return Quarries map.
     */
    static public MatchableHashMap<Region> getQuarries() {
        return quarries;
    }

    /**
     * Grab the homes map.
     * @return Homes map.
     */
    static public MatchableHashMap<Region> getHomes() {
        return homes;
    }

    /**
     * Grab the shops map.
     * @return Shops map.
     */
    static public MatchableHashMap<Region> getShops() {
        return shops;
    }

    /**
     * Grab the bases map.
     * @return Bases map.
     */
    static public MatchableHashMap<Region> getBases() {
        return bases;
    }

    /**
     * Grab the cities map.
     * @return Cities map.
     */
    static public MatchableHashMap<Region> getCities() {
        return cities;
    }

    /**
     * Grab the cities map.
     * @return Cities map.
     */
    static public MatchableHashMap<Region> getArenas() {
        return arenas;
    }

    /**
     * Grab the vips map.
     * @return Vips map.
     */
    static public MatchableHashMap<ExtendedPlayer> getVIPS() {
        return vips;
    }

    /**
     * Grab the online players map.
     * @return Online players map.
     */
    static public MatchableHashMap<ExtendedPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }
}
