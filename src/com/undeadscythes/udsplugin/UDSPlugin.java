package com.undeadscythes.udsplugin;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.timers.*;
import com.undeadscythes.udsplugin.eventhandlers.*;
import com.undeadscythes.udsplugin.commands.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythe
 */
public class UDSPlugin extends JavaPlugin {
    public static UDSPlugin PLUGIN;
    public static Server SERVER;
    public static final int BUILD_LIMIT = 255;
    public static final String INT_REGEX = "[0-9][0-9]*";
    public static final File DATA_PATH = new File("plugins/UDSPlugin/data");
    public static final HashSet<Byte> TRANSPARENT_BLOCKS = new HashSet<Byte>(0);
    private static final List<Material> WATER = new ArrayList<Material>(Arrays.asList(Material.WATER, Material.STATIONARY_WATER));
    private static final List<Material> RAILS = new ArrayList<Material>(Arrays.asList(Material.RAILS, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL));
    private static final Vector HALF_BLOCK = new Vector(.5, .5, .5);
    private static final File BLOCKS_PATH = new File("plugins/UDSPlugin/blocks");
    private static final List<EntityType> HOSTILE_MOBS = new ArrayList<EntityType>(Arrays.asList(EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.ENDER_DRAGON, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.WITCH, EntityType.WITHER, EntityType.ZOMBIE));
    private static final List<EntityType> PASSIVE_MOBS = new ArrayList<EntityType>(Arrays.asList(EntityType.BAT, EntityType.CHICKEN, EntityType.COW, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PIG, EntityType.SHEEP, EntityType.SQUID, EntityType.VILLAGER));
    private static final HashMap<String, ChatRoom> CHAT_ROOMS = new HashMap<String, ChatRoom>(0);
    private static final HashMap<String, Request> REQUESTS = new HashMap<String, Request>(0);
    private static final HashMap<String, EditSession> SESSIONS = new HashMap<String, EditSession>(0);
    private static AfkCheck afkCheck = new AfkCheck();
    private static AutoSave autoSave = new AutoSave();
    private static DragonRespawn dragonRespawn = new DragonRespawn();
    private static MinecartCheck minecartChecks = new MinecartCheck();
    private static PlayerChecks playerChecks = new PlayerChecks();
    private static QuarryRefill quarryRefill = new QuarryRefill();
    private static RequestTimeOut requestTimeOut = new RequestTimeOut();
    private static VipSpawns vipSpawns = new VipSpawns();
    private static Data data;
    private static boolean serverLockedDown = false;
    private static final YamlConfig worldFlags = new YamlConfig(DATA_PATH + "/worlds.yml");

    public static void main(final String[] args) {}

    public static int saveFiles() throws IOException {
        data.saveData();
        worldFlags.save();
        PlayerUtils.savePlayers();
        WarpUtils.saveWarps(DATA_PATH);
        ClanUtils.saveClans(DATA_PATH);
        RegionUtils.saveRegions(DATA_PATH);
        PortalUtils.savePortals(DATA_PATH);
        return ClanUtils.numClans() + RegionUtils.numRegions() + WarpUtils.numWarps() + PortalUtils.numPortals() + PlayerUtils.numPlayers();
    }

    public static Request getRequest(final Member player) {
        return REQUESTS.get(player.getName());
    }

    public static EditSession getSession(final String name) {
        return SESSIONS.get(name);
    }

    public static void addSession(final String name, final EditSession session) {
        SESSIONS.put(name, session);
    }

    public static Iterator<Map.Entry<String, Request>> getRequestIterator() {
        return REQUESTS.entrySet().iterator();
    }

    public static Data getData() {
        return data;
    }

    public static void toggleLockdown() {
        serverLockedDown ^= true;
    }

    public static boolean isLockedDown() {
        return serverLockedDown;
    }

    public static File getBlocksPath() {
        return BLOCKS_PATH;
    }

    public static Vector getHalfBlock() {
        return HALF_BLOCK;
    }

    public static boolean isRail(final Material type) {
        return RAILS.contains(type);
    }

    public static String getVersion() {
        return PLUGIN.getDescription().getVersion();
    }

    public static boolean checkWorldFlag(final World world, final Flag flag) {
        FileConfiguration flags = worldFlags.getConfig();
        final String path = world.getName() + "." + flag.name();
        if(flags.contains(path)) {
            return flags.getBoolean(path);
        }
        final boolean def = flag.isDefaulted();
        flags.set(path, def);
        return def;
    }

    public static boolean toggleWorldFlag(final World world, final Flag flag) {
        final boolean after = !checkWorldFlag(world, flag);
        worldFlags.getConfig().set(world.getName() + "." + flag.name(), after);
        return after;
    }

    public static void changeWorldMode(final World world, final GameMode mode) {
        worldFlags.getConfig().set(world.getName() + ".mode", mode.getValue());
    }

    public static void setWorldSpawn(final Location location) {
        worldFlags.getConfig().set(location.getWorld().getName() + ".spawn.x", location.getX());
        worldFlags.getConfig().set(location.getWorld().getName() + ".spawn.y", location.getY());
        worldFlags.getConfig().set(location.getWorld().getName() + ".spawn.z", location.getZ());
        worldFlags.getConfig().set(location.getWorld().getName() + ".spawn.pitch", location.getPitch());
        worldFlags.getConfig().set(location.getWorld().getName() + ".spawn.yaw", location.getYaw());
    }

    public static Location getWorldSpawn(final World world) {
        Location spawn;
        if(worldFlags.getConfig().contains(world.getName() + ".spawn")) {
            spawn = new Location(world, worldFlags.getConfig().getDouble(world.getName() + ".spawn.x"), worldFlags.getConfig().getDouble(world.getName() + ".spawn.y"), worldFlags.getConfig().getDouble(world.getName() + ".spawn.z"), (float)worldFlags.getConfig().getDouble(world.getName() + ".spawn.yaw"), (float)worldFlags.getConfig().getDouble(world.getName() + ".spawn.pitch"));
        } else {
            spawn = world.getSpawnLocation();
        }
        return spawn;
    }

    public static GameMode getWorldMode(final World world) {
        final String path = world.getName() + ".mode";
        if(worldFlags.getConfig().contains(path)) {
            return GameMode.getByValue(worldFlags.getConfig().getInt(path));
        }
        changeWorldMode(world, GameMode.SURVIVAL);
        return GameMode.SURVIVAL;
    }

    public static void sendBroadcast(final String message) {
        Bukkit.broadcastMessage(Color.BROADCAST + message);
    }

    public static boolean isPassiveMob(final EntityType mob) {
        return PASSIVE_MOBS.contains(mob);
    }

    public static boolean isHostileMob(final EntityType mob) {
        return HOSTILE_MOBS.contains(mob);
    }

    public static boolean isWater(final Material type) {
        return WATER.contains(type);
    }

    public static void addRequest(final String name, final Request request) {
        REQUESTS.put(name, request);
    }

    public static ChatRoom getChatRoom(final String name) {
        return CHAT_ROOMS.get(name);
    }

    public static void addChatRoom(final String name, final ChatRoom chatRoom) {
        CHAT_ROOMS.put(name, chatRoom);
    }

    public static void removeRequest(final String name) {
        REQUESTS.remove(name);
    }

    public static UDSPlugin getPlugin() {
        return PLUGIN;
    }

    @Override
    public void onEnable() {
        PLUGIN = this;
        SERVER = getServer();
        DATA_PATH.mkdirs();
        BLOCKS_PATH.mkdirs();
        try {
            MetaCore.loadMeta(DATA_PATH + "/players.yml", MetaType.values(), PlayerKey.values());
        } catch (IOException ex) {
            getLogger().severe("Could not load players. Shutting down.");
            getServer().shutdown();
        }
        Config.init();
        UDSPlugin.TRANSPARENT_BLOCKS.clear();
        for(Material material : Material.values()) {
            if(material.isBlock() && !material.isSolid()) {
                TRANSPARENT_BLOCKS.add((byte)material.getId());
            }
        }
        data = new Data(this);
        data.reloadData();
        data.saveDefaultData();
        data.saveData();
        loadWorlds();
        data.reloadData();
        worldFlags.load();
        try {
            loadFiles();
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        final BukkitScheduler sched = Bukkit.getScheduler();
        sched.scheduleSyncRepeatingTask(this, afkCheck, TimeUtils.MINUTE * 5 / TimeUtils.TICK, TimeUtils.MINUTE * 5 / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, autoSave, TimeUtils.MINUTE * 15 / TimeUtils.TICK, TimeUtils.MINUTE * 15 / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, dragonRespawn, TimeUtils.MINUTE / TimeUtils.TICK, TimeUtils.HOUR / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, minecartChecks, TimeUtils.MINUTE / TimeUtils.TICK, TimeUtils.SECOND * 5 / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, playerChecks, TimeUtils.MINUTE / TimeUtils.TICK, TimeUtils.SECOND * 5 / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, quarryRefill, TimeUtils.HOUR / TimeUtils.TICK, TimeUtils.DAY / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, requestTimeOut, TimeUtils.MINUTE / TimeUtils.TICK, TimeUtils.SECOND * 15 / TimeUtils.TICK);
        sched.scheduleSyncRepeatingTask(this, vipSpawns, TimeUtils.HOUR / TimeUtils.TICK, TimeUtils.DAY / TimeUtils.TICK);
        setCommandExecutors();
        registerEvents();
        addRecipes();
        Censor.initCensor();
        MinecartCheck.findMinecarts();
        final String message = getName() + " version " + this.getDescription().getVersion() + " enabled.";
        getLogger().info(message);
    }

    @Override
    public void onDisable() {
        try {
            getLogger().info(saveFiles() + " server objects saved.");
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        final String message = getName() + " disabled.";
        getLogger().info(message);
    }

    public void loadWorlds() {
        for(String world : data.getWorlds()) {
            final WorldCreator creator = new WorldCreator(world);
            creator.createWorld();
        }
    }

    private void loadFiles() throws IOException {
        PlayerUtils.loadPlayers(DATA_PATH);
        RegionUtils.loadRegions(DATA_PATH);
        WarpUtils.loadWarps(DATA_PATH);
        ClanUtils.loadClans(DATA_PATH);
        PortalUtils.loadPortals(DATA_PATH);
    }

    private void setCommandExecutors() {
        getCommand("a").setExecutor(new ACmd());
        getCommand("acceptrules").setExecutor(new AcceptRulesCmd());
        getCommand("admin").setExecutor(new AdminCmd());
        getCommand("afk").setExecutor(new AfkCmd());
        getCommand("back").setExecutor(new BackCmd());
        getCommand("ban").setExecutor(new BanCmd());
        getCommand("bounty").setExecutor(new BountyCmd());
        getCommand("broadcast").setExecutor(new BroadcastCmd());
        getCommand("butcher").setExecutor(new ButcherCmd());
        getCommand("c").setExecutor(new CCmd());
        getCommand("call").setExecutor(new CallCmd());
        getCommand("challenge").setExecutor(new ChallengeCmd());
        getCommand("chat").setExecutor(new ChatCmd());
        getCommand("check").setExecutor(new CheckCmd());
        getCommand("chunk").setExecutor(new ChunkCmd());
        getCommand("ci").setExecutor(new CiCmd());
        getCommand("city").setExecutor(new CityCmd());
        getCommand("clan").setExecutor(new ClanCmd());
        getCommand("day").setExecutor(new DayCmd());
        getCommand("debug").setExecutor(new DebugCmd());
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
        getCommand("mod").setExecutor(new ModCmd());
        getCommand("money").setExecutor(new MoneyCmd());
        getCommand("n").setExecutor(new NCmd());
        getCommand("nick").setExecutor(new NickCmd());
        getCommand("night").setExecutor(new NightCmd());
        getCommand("p").setExecutor(new PCmd());
        getCommand("paybail").setExecutor(new PayBailCmd());
        getCommand("pet").setExecutor(new PetCmd());
        getCommand("plot").setExecutor(new PlotCmd());
        getCommand("portal").setExecutor(new PortalCmd());
        getCommand("powertool").setExecutor(new PowertoolCmd());
        getCommand("private").setExecutor(new PrivateCmd());
        getCommand("promote").setExecutor(new PromoteCmd());
        getCommand("pvp").setExecutor(new PvpCmd());
        getCommand("r").setExecutor(new RCmd());
        getCommand("rain").setExecutor(new RainCmd());
        getCommand("region").setExecutor(new RegionCmd());
        getCommand("rules").setExecutor(new RulesCmd());
        getCommand("scuba").setExecutor(new ScubaCmd());
        getCommand("server").setExecutor(new ServerCmd());
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
        getCommand("vanish").setExecutor(new VanishCmd());
        getCommand("vip").setExecutor(new VIPCmd());
        getCommand("we").setExecutor(new WECmd());
        getCommand("warden").setExecutor(new WardenCmd());
        getCommand("warp").setExecutor(new WarpCmd());
        getCommand("where").setExecutor(new WhereCmd());
        getCommand("who").setExecutor(new WhoCmd());
        getCommand("whois").setExecutor(new WhoIsCmd());
        getCommand("world").setExecutor(new WorldCmd());
        getCommand("xp").setExecutor(new XPCmd());
        getCommand("y").setExecutor(new YCmd());
    }

    private void registerEvents() {
        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new AsyncPlayerChat(), this);
        manager.registerEvents(new BlockBreak(), this);
        manager.registerEvents(new BlockBurn(), this);
        manager.registerEvents(new BlockDispense(), this);
        manager.registerEvents(new BlockFade(), this);
        manager.registerEvents(new BlockForm(), this);
        manager.registerEvents(new BlockFromTo(), this);
        manager.registerEvents(new BlockGrow(), this);
        manager.registerEvents(new BlockIgnite(), this);
        manager.registerEvents(new BlockPhysics(), this);
        manager.registerEvents(new BlockPistonExtend(), this);
        manager.registerEvents(new BlockPistonRetract(), this);
        manager.registerEvents(new BlockPlace(), this);
        manager.registerEvents(new BlockRedstone(), this);
        manager.registerEvents(new BlockSpread(), this);
        manager.registerEvents(new CreatureSpawn(), this);
        manager.registerEvents(new EntityBlockForm(), this);
        manager.registerEvents(new EntityChangeBlock(), this);
        manager.registerEvents(new EntityCreatePortal(), this);
        manager.registerEvents(new EntityDamage(), this);
        manager.registerEvents(new EntityDamageByEntity(), this);
        manager.registerEvents(new EntityDeath(), this);
        manager.registerEvents(new EntityExplode(), this);
        manager.registerEvents(new EntityInteract(), this);
        manager.registerEvents(new EntityPortal(), this);
        manager.registerEvents(new InventoryClick(), this);
        manager.registerEvents(new InventoryClose(), this);
        manager.registerEvents(new InventoryOpen(), this);
        manager.registerEvents(new ItemDespawn(), this);
        manager.registerEvents(new HangingBreak(), this);
        manager.registerEvents(new HangingPlace(), this);
        manager.registerEvents(new PlayerBucketEmpty(), this);
        manager.registerEvents(new PlayerBucketFill(), this);
        manager.registerEvents(new PlayerDeath(), this);
        manager.registerEvents(new PlayerInteract(), this);
        manager.registerEvents(new PlayerInteractEntity(), this);
        manager.registerEvents(new PlayerJoin(), this);
        manager.registerEvents(new PlayerLogin(), this);
        manager.registerEvents(new PlayerMove(), this);
        manager.registerEvents(new PlayerPickupItem(), this);
        manager.registerEvents(new PlayerPortal(), this);
        manager.registerEvents(new PlayerQuit(), this);
        manager.registerEvents(new PlayerRespawn(), this);
        manager.registerEvents(new PlayerShearEntity(), this);
        manager.registerEvents(new PlayerTeleport(), this);
        manager.registerEvents(new SignChange(), this);
        manager.registerEvents(new VehicleEntityCollision(), this);
        manager.registerEvents(new VehicleCreate(), this);
        manager.registerEvents(new VehicleDestroy(), this);
        manager.registerEvents(new VehicleExit(), this);
        manager.registerEvents(new WeatherChange(), this);
    }

    private void addRecipes() {
        for(final CustomRecipe recipe : CustomRecipe.values()) {
            Bukkit.getServer().addRecipe(recipe.getRecipe());
        }
    }
}
