package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.Region.RegionType;
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
    public final static Vector HALF_BLOCK = new Vector(.5, .5, .5);
    /**
     * Where the issue/suggestion tickets are stored.
     */
    public final static String TICKET_PATH = "tickets.txt";
    public final static String TIMES_PATH = "times.data";

    /**
     * Whether the server is in lockdown mode.
     */
    public static boolean serverInLockdown;
    public static long lastEnderDeath;
    public static long lastDailyEvent;

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
    private static MatchableHashMap<SaveablePlayer> vips;
    private static MatchableHashMap<SaveablePlayer> onlinePlayers;
    private static File DATA_PATH = new File("plugins/UDSPlugin/data");
    private Timer timer;

    /**
     * Used for testing in NetBeans. Woo! NetBeans!
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
        getLogger().info("Config loaded.");
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
        vips = new MatchableHashMap<SaveablePlayer>();
        onlinePlayers = new MatchableHashMap<SaveablePlayer>();
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
        getLogger().info("Timer started.");
        setCommandExecutors();
        getLogger().info("Commands registered.");
        registerEvents();
        getLogger().info("Events registered.");
        addRecipes();
        getLogger().info("Recipes added.");
        Censor.initCensor();
        getLogger().info("Censor online.");
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
            String message = (clans.size() + regions.size() + warps.size() + players.size()) + " clans, regions, warps and players saved.";
            getLogger().info(message);
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
        regions.save(DATA_PATH + File.separator + Region.PATH);
        warps.save(DATA_PATH + File.separator + Warp.PATH);
        players.save(DATA_PATH + File.separator + SaveablePlayer.PATH);
        BufferedWriter file = new BufferedWriter(new FileWriter(DATA_PATH + File.separator + UDSPlugin.TIMES_PATH));
        file.write(Long.toString(lastDailyEvent));
        file.newLine();
        file.write(Long.toString(lastEnderDeath));
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
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + SaveablePlayer.PATH));
            while((nextLine = file.readLine()) != null) {
                SaveablePlayer player = new SaveablePlayer(nextLine);
                players.put(nextLine.split("\t")[0], player);
                if(player.getVIPTime() > 0) {
                    vips.put(player.getName(), player);
                }
            }
            file.close();
            message = players.size() + " players loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info("No player file exists yet.");
        }
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Region.PATH));
            while((nextLine = file.readLine()) != null) {
                Region region = new Region(nextLine);
                regions.put(region.getName(), region);
                if(region.getType().equals(RegionType.BASE)) {
                    bases.put(region.getName(), region);
                } else if(region.getType().equals(RegionType.HOME)) {
                    homes.put(region.getName(), region);
                } else if(region.getType().equals(RegionType.QUARRY)) {
                    quarries.put(region.getName(), region);
                } else if(region.getType().equals(RegionType.SHOP)) {
                    shops.put(region.getName(), region);
                } else if(region.getType().equals(RegionType.CITY)) {
                    cities.put(region.getName(), region);
                } else if(region.getType().equals(RegionType.ARENA)) {
                    arenas.put(region.getName(), region);
                }
            }
            file.close();
            message = regions.size() + " regions loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info("No region file exists yet.");
        }
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Warp.PATH));
            while((nextLine = file.readLine()) != null) {
                warps.put(nextLine.split("\t", 1)[0], new Warp(nextLine));
            }
            file.close();
            message = warps.size() + " warps loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info("No warp file exists yet.");
        }
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + TIMES_PATH));
            lastDailyEvent = Long.parseLong(file.readLine());
            lastEnderDeath = Long.parseLong(file.readLine());
            file.close();
        } catch (FileNotFoundException ex) {
            lastDailyEvent = System.currentTimeMillis();
        }
        try {
            file = new BufferedReader(new FileReader(DATA_PATH + File.separator + Clan.PATH));
            while((nextLine = file.readLine()) != null) {
                Clan clan = new Clan(nextLine);
                clans.put(nextLine.split("\t")[0], clan);
                clan.linkMembers();
            }
            file.close();
            message = clans.size() + " clans loaded.";
            getLogger().info(message);
        } catch (FileNotFoundException ex) {
            getLogger().info("No clan file exists yet.");
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
        getCommand("clan").setExecutor(new ClanCmd());
        getCommand("day").setExecutor(new DayCmd());
        getCommand("delwarp").setExecutor(new DelWarpCmd());
        getCommand("demote").setExecutor(new DemoteCmd());
        getCommand("enchant").setExecutor(new EnchantCmd());
        getCommand("face").setExecutor(new FaceCmd());
        getCommand("gift").setExecutor(new GiftCmd());
        getCommand("god").setExecutor(new GodCmd());
        getCommand("heal").setExecutor(new HealCmd());
        getCommand("help").setExecutor(new HelpCmd());
        getCommand("home").setExecutor(new HomeCmd());
        getCommand("i").setExecutor(new ICmd());
        getCommand("ignore").setExecutor(new IgnoreCmd());
        getCommand("invsee").setExecutor(new InvSeeCmd());
        getCommand("jail").setExecutor(new JailCmd());
        getCommand("kick").setExecutor(new KickCmd());
        getCommand("kit").setExecutor(new KitCmd());
        getCommand("lockdown").setExecutor(new LockdownCmd());
        getCommand("map").setExecutor(new MapCmd());
        getCommand("me").setExecutor(new MeCmd());
        getCommand("money").setExecutor(new MoneyCmd());
        getCommand("n").setExecutor(new NCmd());
        getCommand("nick").setExecutor(new NickCmd());
        getCommand("night").setExecutor(new NightCmd());
        getCommand("p").setExecutor(new PCmd());
        getCommand("paybail").setExecutor(new PayBailCmd());
        getCommand("pet").setExecutor(new PetCmd());
        getCommand("powertool").setExecutor(new PowertoolCmd());
        getCommand("private").setExecutor(new PrivateCmd());
        getCommand("promote").setExecutor(new PromoteCmd());
        getCommand("r").setExecutor(new RCmd());
        getCommand("rain").setExecutor(new RainCmd());
        getCommand("region").setExecutor(new RegionCmd());
        getCommand("rules").setExecutor(new RulesCmd());
        getCommand("scuba").setExecutor(new ScubaCmd());
        getCommand("server").setExecutor(new ServerCmd());
        getCommand("setspawn").setExecutor(new SetSpawnCmd());
        getCommand("setwarp").setExecutor(new SetWarpCmd());
        getCommand("shop").setExecutor(new ShopCmd());
        getCommand("signs").setExecutor(new SignsCmd());
        getCommand("sit").setExecutor(new SitCmd());
        getCommand("spawn").setExecutor(new SpawnCmd());
        getCommand("spawner").setExecutor(new SpawnerCmd());
        getCommand("stack").setExecutor(new StackCmd());
        getCommand("stats").setExecutor(new StatsCmd());
        getCommand("storm").setExecutor(new StormCmd());
        getCommand("sun").setExecutor(new SunCmd());
        getCommand("tp").setExecutor(new TPCmd());
        getCommand("tell").setExecutor(new TellCmd());
        getCommand("ticket").setExecutor(new TicketCmd());
        getCommand("tgm").setExecutor(new TGMCmd());
        getCommand("unban").setExecutor(new UnBanCmd());
        getCommand("unjail").setExecutor(new UnJailCmd());
        getCommand("vip").setExecutor(new VIPCmd());
        getCommand("we").setExecutor(new WECmd());
        getCommand("warp").setExecutor(new WarpCmd());
        getCommand("where").setExecutor(new WhereCmd());
        getCommand("who").setExecutor(new WhoCmd());
        getCommand("whois").setExecutor(new WhoIsCmd());
        getCommand("xp").setExecutor(new XPCmd());
        getCommand("y").setExecutor(new YCmd());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new BlockBurn(), this);
        getServer().getPluginManager().registerEvents(new BlockFade(), this);
        getServer().getPluginManager().registerEvents(new BlockForm(), this);
        getServer().getPluginManager().registerEvents(new BlockFromTo(), this);
        getServer().getPluginManager().registerEvents(new BlockGrow(), this);
        getServer().getPluginManager().registerEvents(new BlockIgnite(), this);
        getServer().getPluginManager().registerEvents(new BlockPhysics(), this);
        getServer().getPluginManager().registerEvents(new BlockPistonExtend(), this);
        getServer().getPluginManager().registerEvents(new BlockPistonRetract(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new BlockRedstone(), this);
        getServer().getPluginManager().registerEvents(new BlockSpread(), this);
        getServer().getPluginManager().registerEvents(new CreatureSpawn(), this);
        getServer().getPluginManager().registerEvents(new EntityBlockForm(), this);
        getServer().getPluginManager().registerEvents(new EntityChangeBlock(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new EntityExplode(), this);
        getServer().getPluginManager().registerEvents(new EntityInteract(), this);
        getServer().getPluginManager().registerEvents(new EntityPortalEnter(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryOpen(), this);
        getServer().getPluginManager().registerEvents(new HangingBreak(), this);
        getServer().getPluginManager().registerEvents(new HangingPlace(), this);
        getServer().getPluginManager().registerEvents(new PlayerBucketEmpty(), this);
        getServer().getPluginManager().registerEvents(new PlayerBucketFill(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerPortal(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new PlayerShearEntity(), this);
        getServer().getPluginManager().registerEvents(new SignChange(), this);
        getServer().getPluginManager().registerEvents(new VehicleEntityCollision(), this);
        getServer().getPluginManager().registerEvents(new VehicleDestroy(), this);
        getServer().getPluginManager().registerEvents(new VehicleExit(), this);
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
    static public MatchableHashMap<SaveablePlayer> getPlayers() {
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
     * Grab the VIPs map.
     * @return VIPs map.
     */
    static public MatchableHashMap<SaveablePlayer> getVIPS() {
        return vips;
    }

    /**
     * Grab the online players map.
     * @return Online players map.
     */
    static public MatchableHashMap<SaveablePlayer> getOnlinePlayers() {
        return onlinePlayers;
    }
}
