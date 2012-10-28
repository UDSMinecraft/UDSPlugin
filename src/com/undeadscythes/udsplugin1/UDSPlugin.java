package com.undeadscythes.udsplugin1;

import com.undeadscythes.udsplugin1.commands.DayCmd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin. The heart of UDSPlugin.
 * @author UndeadScythe
 */
public class UDSPlugin extends JavaPlugin {
    static SaveableHashMap clans;
    static SaveableHashMap players;
    static SaveableHashMap regions;
    static SaveableHashMap warps;
    static MatchableHashMap<ChatRoom> chatRooms;
    static MatchableHashMap<Request> requests;
    static MatchableHashMap<Session> sessions;
    final String PATH = "plugins/UDSPlugin/data";
    Timer timer;

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        new File(PATH).mkdir();
        clans = new SaveableHashMap();
        players = new SaveableHashMap();
        regions = new SaveableHashMap();
        warps = new SaveableHashMap();
        chatRooms = new MatchableHashMap<ChatRoom>();
        requests = new MatchableHashMap<Request>();
        sessions = new MatchableHashMap<Session>();
        try {
            loadFiles();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDSPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer = new Timer(this, 100);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, timer, 100, 100);
        setCommandExecutors();
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
    }

    /**
     * Saves all the listed objects to file.
     * @throws IOException When a file can't be opened.
     */
    public void saveFiles() throws IOException {
        clans.save(new File(PATH + Clan.PATH));
        players.save(new File(PATH + ExtendedPlayer.PATH));
        regions.save(new File(PATH + Region.PATH));
        warps.save(new File(PATH + Warp.PATH));
    }

    /**
     * Loads the listed files from file.
     * @throws FileNotFoundException When a file can't be accessed.
     * @throws IOException When a file can't be read from.
     */
    public void loadFiles() throws FileNotFoundException, IOException {
        BufferedReader file = new BufferedReader(new FileReader(PATH + Clan.PATH));
        String nextLine;
        while((nextLine = file.readLine()) != null) {
            clans.put(nextLine.split("\t", 1)[0], new Clan(nextLine));
        }
        file.close();
        file = new BufferedReader(new FileReader(PATH + ExtendedPlayer.PATH));
        while((nextLine = file.readLine()) != null) {
            players.put(nextLine.split("\t", 1)[0], new ExtendedPlayer(nextLine));
        }
        file.close();
        file = new BufferedReader(new FileReader(PATH + Region.PATH));
        while((nextLine = file.readLine()) != null) {
            regions.put(nextLine.split("\t", 1)[0], new Region(nextLine));
        }
        file.close();
        file = new BufferedReader(new FileReader(PATH + Warp.PATH));
        while((nextLine = file.readLine()) != null) {
            warps.put(nextLine.split("\t", 1)[0], new Warp(nextLine));
        }
        file.close();
    }

    /**
     * Connects the commands with their executors.
     */
    private void setCommandExecutors() {
        getCommand("a").setExecutor(new ACmd(this));
        getCommand("acceptrules").setExecutor(new AcceptRulesCmd(this));
        getCommand("back").setExecutor(new BackCmd(this));
        getCommand("ban").setExecutor(new BanCmd(this));
        getCommand("bounty").setExecutor(new BountyCmd());
        getCommand("broadcast").setExecutor(new BroadcastCmd(this));
        getCommand("butcher").setExecutor(new ButcherCmd(this));
        getCommand("c").setExecutor(new CCmd(this));
        getCommand("call").setExecutor(new CallCmd(this));
        getCommand("challenge").setExecutor(new ChallengeCmd(this));
        getCommand("check").setExecutor(new CheckCmd(this));
        getCommand("ci").setExecutor(new CiCmd(this));
        getCommand("city").setExecutor(new CityCmd(this));
        getCommand("clan").setExecutor(new ClanCmd(this));
        getCommand("day").setExecutor(new DayCmd());
        getCommand("delwarp").setExecutor(new DelWarpCmd(this));
        getCommand("enchant").setExecutor(new EnchantCmd(this));
        getCommand("face").setExecutor(new FaceCmd());
        getCommand("gift").setExecutor(new GiftCmd(this));
        getCommand("god").setExecutor(new GodCmd(this));
        getCommand("heal").setExecutor(new HealCmd(this));
        getCommand("help").setExecutor(new HelpCmd(this));
        getCommand("home").setExecutor(new HomeCmd(this));
        getCommand("i").setExecutor(new ICmd(this));
        getCommand("ignore").setExecutor(new IgnoreCmd(this));
        getCommand("invsee").setExecutor(new InvSeeCmd(this));
        getCommand("jail").setExecutor(new JailCmd(this));
        getCommand("kick").setExecutor(new KickCmd(this));
        getCommand("kit").setExecutor(new KitCmd(this));
        getCommand("lockdown").setExecutor(new LockdownCmd(this));
        getCommand("map").setExecutor(new MapCmd(this));
        getCommand("me").setExecutor(new MeCmd(this));
        getCommand("money").setExecutor(new MoneyCmd());
        getCommand("n").setExecutor(new NCmd(this));
        getCommand("nick").setExecutor(new NickCmd(this));
        getCommand("night").setExecutor(new NightCmd(this));
        getCommand("p").setExecutor(new PCmd(this));
        getCommand("paybail").setExecutor(new PayBailCmd());
        getCommand("pet").setExecutor(new PetCmd(this));
        getCommand("powertool").setExecutor(new PowertoolCmd());
        getCommand("private").setExecutor(new PrivateCmd(this));
        getCommand("r").setExecutor(new RCmd(this));
        getCommand("rain").setExecutor(new RainCmd(this));
        getCommand("region").setExecutor(new RegionCmd(this));
        getCommand("rules").setExecutor(new RulesCmd(this));
        getCommand("scuba").setExecutor(new ScubaCmd(this));
        getCommand("server").setExecutor(new ServerCmd(this));
        getCommand("setspawn").setExecutor(new SetSpawnCmd(this));
        getCommand("setwarp").setExecutor(new SetWarpCmd(this));
        getCommand("shop").setExecutor(new ShopCmd(this));
        getCommand("signs").setExecutor(new SignsCmd(this));
        getCommand("sit").setExecutor(new SitCmd(this));
        getCommand("spawn").setExecutor(new SpawnCmd(this));
        getCommand("spawner").setExecutor(new SpawnerCmd(this));
        getCommand("stack").setExecutor(new StackCmd(this));
        getCommand("stats").setExecutor(new StatsCmd(this));
        getCommand("storm").setExecutor(new StormCmd(this));
        getCommand("sun").setExecutor(new SunCmd(this));
        getCommand("swearjar").setExecutor(new SwearjarCmd(this));
        getCommand("tp").setExecutor(new TPCmd(this));
        getCommand("tell").setExecutor(new TellCmd(this));
        getCommand("tgm").setExecutor(new TGMCmd(this));
        getCommand("unban").setExecutor(new UnBanCmd(this));
        getCommand("unjail").setExecutor(new UnJailCmd(this));
        getCommand("unnick").setExecutor(new UnNickCmd(this));
        getCommand("vip").setExecutor(new VIPCmd(this));
        getCommand("we").setExecutor(new WECmd(this));
        getCommand("warp").setExecutor(new WarpCmd());
        getCommand("where").setExecutor(new WhereCmd(this));
        getCommand("who").setExecutor(new WhoCmd(this));
        getCommand("whois").setExecutor(new WhoIsCmd(this));
        getCommand("xp").setExecutor(new XPCmd(this));
        getCommand("y").setExecutor(new YCmd());
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
}
