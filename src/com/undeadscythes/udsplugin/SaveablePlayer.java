package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

/**
 * An extension of Minecraft players adding various fields and methods.
 *
 * @author UndeadScythes
 */
public class SaveablePlayer implements Saveable {
    private String name;
    private Player player;
    private String nick;

    private boolean hasGodMode = false;
    private boolean hasLockdownPass = false;
    private boolean isAfk = false;
    private boolean mustLoadItems = false;
    private boolean isShopping = false;
    private boolean isBuying = false;
    private boolean isHidden = false;
    private int wager = 0;
    private int powertoolId = 0;
    private int bounty = 0;
    private int money = 0;
    private int vipSpawns = 0;
    private int bail = 0;
    private long vipTime = 0;
    private long timeJailed = 0;
    private long jailSentence = 0;
    private long timeLogged = 0;
    private long timeAttacked = 0;
    private long timePrizeClaimed = 0;
    private String powertoolCmd = "";
    private Location backPoint = null;
    private Location checkPoint = null;
    private SaveablePlayer challenger = null;
    private SaveablePlayer whisperer = null;
    private ChatRoom chatRoom = null;
    private ChatChannel chatChannel = ChatChannel.PUBLIC;
    private UUID selectedPet = null;
    private Clan clan = null;
    private PlayerRank rank = PlayerRank.NEWBIE;
    private ItemStack[] inventoryCopy = null;
    private ItemStack[] armorCopy = null;
    private final LinkedList<Long> lastChatTimes = new LinkedList<Long>();
    private final Set<SaveablePlayer> ignoredPlayers = new HashSet<SaveablePlayer>(0);
    private boolean pvp = false;
    private int kills = 0;
    private long pvpTime = 0;
    private Vector lastVector;
    private Location shop;
    private final LinkedList<Long> lastLogins = new LinkedList<Long>();

    public SaveablePlayer(final Player player) {
        this.player = player;
        nick = player.getName();
        name = player.getName();
    }

    public SaveablePlayer(final String record) {
        final String[] recordSplit = record.split("\t");
        name = recordSplit[0];
        bounty = Integer.parseInt(recordSplit[1]);
        money = Integer.parseInt(recordSplit[2]);
        rank = PlayerRank.getByName(recordSplit[3]);
        vipTime = Long.parseLong(recordSplit[4]);
        vipSpawns = Integer.parseInt(recordSplit[5]);
        timeJailed = Long.parseLong(recordSplit[6]);
        jailSentence = Long.parseLong(recordSplit[7]);
        bail = Integer.parseInt(recordSplit[8]);
        nick = recordSplit[9];
        timeLogged = Long.parseLong(recordSplit[10]);
        if(recordSplit.length == 13) {
            pvpTime = Long.parseLong(recordSplit[11]);
            kills = Integer.parseInt(recordSplit[12]);
        }
    }

    @Override
    public final String getRecord() {
        final List<String> record = new ArrayList<String>(13);
        record.add(name);
        record.add(Integer.toString(bounty));
        record.add(Integer.toString(money));
        record.add(rank.toString());
        record.add(Long.toString(vipTime));
        record.add(Integer.toString(vipSpawns));
        record.add(Long.toString(timeJailed));
        record.add(Long.toString(jailSentence));
        record.add(Integer.toString(bail));
        record.add(nick);
        record.add(Long.toString(timeLogged));
        record.add(Long.toString(pvpTime));
        record.add(Integer.toString(kills));
        return StringUtils.join(record.toArray(), "\t");
    }

    public final void wrapPlayer(final Player player) {
        this.player = player;
        player.setDisplayName(nick);
        player.setPlayerListName(nick);
        lastVector = player.getLocation().toVector();
    }

    public final void addTime(final long time) {
        timeLogged += time;
    }

    public final Vector getLastVector() {
        return lastVector;
    }

    public final void setLastVector(final Vector vector) {
        lastVector = vector;
    }

    public final void addKill() {
        kills++;
        player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        pvpTime = System.currentTimeMillis();
    }

    public final void removeKill() {
        kills--;
        player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        pvpTime = System.currentTimeMillis();
    }

    public final int getTotalExp() {
        return player.getTotalExperience();
    }

    public final void toggleLockdownPass() {
        hasLockdownPass ^= true;
    }

    public final UUID getSelectedPet() {
        return selectedPet;
    }

    public final void selectPet(final UUID id) {
        selectedPet = id;
    }

    public final boolean isShopping() {
        return isShopping;
    }

    public final void setShopping(final boolean isShopping) {
        this.isShopping = isShopping;
    }

    public final void setWhisperer(final SaveablePlayer player) {
        whisperer = player;
    }

    public final boolean toggleHidden() {
        isHidden ^= true;
        return isHidden;
    }

    public final boolean isHidden() {
        return isHidden;
    }

    public final int getWager() {
        return wager;
    }

    public final boolean isBuying() {
        return isBuying;
    }

    public final boolean hasPvp() {
        return pvp;
    }

    public final void setBuying(final boolean isBuying) {
        this.isBuying = isBuying;
    }

    public final boolean hasScuba() {
        if(player != null) {
            ItemStack helmet = player.getInventory().getHelmet();
            if(helmet != null && helmet.getType().equals(Material.GLASS)) {
                return true;
            }
        }
        return false;
    }

    public final Location getShop() {
        return shop;
    }

    public final void setShop(final Location shop) {
        this.shop = shop;
    }

    public final SaveablePlayer getWhisperer() {
        return whisperer;
    }

    public final int countItems(final ItemStack search) {
        if(player == null) {
            return 0;
        } else {
            final ItemStack[] inventory = player.getInventory().getContents();
            int count = 0;
            for(int i = 0; i < inventory.length; i++) {
                final ItemStack item = inventory[i];
                if(item != null && item.getType() == search.getType() && item.getData().getData() == search.getData().getData()) {
                    count += item.getAmount();
                }
            }
            return count;
        }
    }

    public final int getPowertoolID() {
        return powertoolId;
    }

    public final String getPowertool() {
        return powertoolCmd;
    }

    public final void claimPrize() {
        timePrizeClaimed = System.currentTimeMillis();
    }

    public final void setVehicle(final Entity vehicle) {
        vehicle.setPassenger(player);
    }

    public final boolean hasClaimedPrize() {
        return (timePrizeClaimed + TimeUtils.DAY > System.currentTimeMillis());
    }

    public final EditSession forceSession() {
        EditSession session = UDSPlugin.getSession(getName());
        if(session == null) {
            session = new EditSession();
            UDSPlugin.addSession(getName(), session);
        }
        return session;
    }

    public final void setPowertoolID(final int ID) {
        powertoolId = ID;
    }

    public final void setPowertool(final String cmd) {
        powertoolCmd = cmd;
    }

    private Player getBase() {
        return player;
    }

    public final void nullBase() {
        player = null;
    }

    public final ItemStack[] getInventoryCopy() {
        return inventoryCopy == null ? null : inventoryCopy.clone();
    }

    public final boolean isInShop(final Location location) {
        for(Region region : RegionUtils.getRegions(RegionType.SHOP)) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                return true;
            }
        }
        return false;
    }

    public final void setClan(final Clan clan) {
        this.clan = clan;
    }

    public final void saveInventory() {
        if(player != null) {
            inventoryCopy = player.getInventory().getContents();
        }
    }

    public final void saveItems() {
        saveInventory();
        saveArmor();
    }

    public final void loadArmor() {
        if(player != null) {
            player.getInventory().setArmorContents(armorCopy);
            armorCopy = new ItemStack[0];
        }
    }

    public final void endChallenge() {
        wager = 0;
        challenger = null;
    }

    public final void loadInventory() {
        if(player != null) {
            player.getInventory().setContents(inventoryCopy);
            inventoryCopy = new ItemStack[0];
        }
    }

    public final void saveArmor() {
        if(player != null) {
            armorCopy = player.getInventory().getArmorContents();
        }
    }

    public final int getBail() {
        return bail;
    }

    public final void loadItems() {
        loadInventory();
        loadArmor();
    }

    public final boolean newChat() {
        if(lastChatTimes.size() > 5) {
            lastChatTimes.removeFirst();
        }
        lastChatTimes.offerLast(System.currentTimeMillis());
        if(lastChatTimes.size() == 5 && lastChatTimes.getLast() - lastChatTimes.getFirst() < 3000) {
            return false;
        }
        return true;
    }

    public final String getTimeLogged() {
        return TimeUtils.timeToString(timeLogged);
    }

    public final String getLastSeen() {
        if(player == null) {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
            if(offlinePlayer == null) {
                return "Unknown";
            } else {
                return TimeUtils.timeToString(System.currentTimeMillis() - Bukkit.getOfflinePlayer(name).getLastPlayed());
            }
        } else {
            return TimeUtils.timeToString(System.currentTimeMillis() - player.getLastPlayed());
        }
    }

    public final String getVIPTimeString() {
        return TimeUtils.timeToString(Config.VIP_TIME - System.currentTimeMillis() + vipTime);
    }

    public final int useVIPSpawns(final int amount) {
        vipSpawns -= amount;
        return vipSpawns;
    }

    public final void setBackPoint() {
        if(player != null) {
            backPoint = player.getLocation();
        }
    }

    public final void setBackPoint(final Location location) {
        backPoint = location;
    }

    public final boolean toggleGameMode() {
        if(player == null) {
            return false;
        } else {
            if(player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.setGameMode(GameMode.CREATIVE);
                return true;
            } else {
                player.setGameMode(GameMode.SURVIVAL);
                return false;
            }
        }
    }

    public final int getMoney() {
        return money;
    }

    public final ChatRoom getChatRoom() {
        return chatRoom;
    }

    public final void release() {
        timeJailed = 0;
        jailSentence = 0;
        if(!quietTeleport(WarpUtils.getWarp("jailout")) && !quietTeleport(getWorld().getSpawnLocation())) {
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }

    public final void jail(final long sentence, final int bail) {
        timeJailed = System.currentTimeMillis();
        jailSentence = sentence * TimeUtils.MINUTE;
        this.bail = bail;
    }

    public final Region getCurrentRegion(final RegionType type) {
        if(player != null) {
            if(type == RegionType.CITY) {
                for(Region region : RegionUtils.getRegions(RegionType.CITY)) {
                    if(player.getLocation().toVector().isInAABB(region.getV1(), region.getV2())) {
                        return region;
                    }
                }
            } else if(type == RegionType.SHOP) {
                for(Region region : RegionUtils.getRegions(RegionType.SHOP)) {
                    if(player.getLocation().toVector().isInAABB(region.getV1(), region.getV2())) {
                        return region;
                    }
                }
            }
        }
        return null;
    }

    public final PlayerRank demote() {
        final PlayerRank newRank = PlayerRank.getBelow(rank);
        if(newRank != null) {
            rank = newRank;
        }
        return newRank;
    }

    public final PlayerRank promote() {
        final PlayerRank newRank = PlayerRank.getAbove(rank);
        if(newRank == PlayerRank.OWNER) {
            return null;
        } else if(newRank != null) {
            rank = newRank;
        }
        return newRank;
    }

    public final ChatChannel getChannel() {
        return chatChannel;
    }

    public final Location getCheckPoint() {
        return checkPoint;
    }

    public final void setCheckPoint(final Location location) {
        checkPoint = location;
    }

    public final boolean isDuelling() {
        return challenger != null;
    }

    public final void setChallenger(final SaveablePlayer challenger) {
        this.challenger = challenger;
    }

    public final void hideFrom(final Player player, final boolean hide) {
        if(hide) {
            player.hidePlayer(this.player);
        } else {
            player.showPlayer(this.player);
        }
    }

    public final SaveablePlayer getChallenger() {
        return challenger;
    }

    public final boolean canBuildHere(final Location location) {
        boolean contained = false;
        for(Region region : RegionUtils.getRegions()) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                if(((region.getRank() != null && rank.compareTo(region.getRank()) >= 0)) || region.isOwnedBy(this) || region.hasMember(this)) {
                    return true;
                }
                contained = true;
            }
        }
        return contained ? false : UDSPlugin.checkWorldFlag(location.getWorld(), WorldFlag.BUILD);
    }

    public final void giveAndDrop(final ItemStack item) {
        if(player != null) {
            final Map<Integer, ItemStack> drops = player.getInventory().addItem(item);
            for(ItemStack drop : drops.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), drop);
            }
        }
    }

    public final boolean ignorePlayer(final SaveablePlayer player) {
        return ignoredPlayers.add(player);
    }

    public final boolean unignorePlayer(final SaveablePlayer player) {
        return ignoredPlayers.remove(player);
    }

    public final boolean isIgnoringPlayer(final SaveablePlayer player) {
        return ignoredPlayers.contains(player);
    }

    public final long getLastDamageCaused() {
        return timeAttacked;
    }

    public final int getBounty() {
        return bounty;
    }

    public final void setBounty(final int bounty) {
        this.bounty = bounty;
    }

    public final void addBounty(final int bounty) {
        this.bounty += bounty;
    }

    public final Location getBack() {
        return backPoint;
    }

    public final boolean hasRank(final PlayerRank rank) {
        return this.rank.compareTo(rank) >= 0;
    }

    public final boolean isRank(final PlayerRank rank) {
        return this.rank.equals(rank);
    }

    public final void setRank(final PlayerRank rank) {
        this.rank = rank;
    }

    public final boolean isInClan() {
        return clan != null;
    }

    public final Clan getClan() {
        return clan;
    }

    public final String getNick() {
        return nick;
    }

    public final boolean toggleChannel(final ChatChannel channel) {
        if(this.chatChannel.equals(channel)) {
            this.chatChannel = ChatChannel.PUBLIC;
            return false;
        } else {
            this.chatChannel = channel;
            return true;
        }
    }

    public final boolean hasLockdownPass() {
        return hasLockdownPass;
    }

    public final void setLoadItems(final boolean loadItems) {
        this.mustLoadItems = loadItems;
    }

    public final boolean hasLoadItems() {
        return mustLoadItems;
    }

    public final long getVIPTime() {
        return vipTime;
    }

    public final void setVIPTime(final long time) {
        vipTime = time;
    }

    public final int getVIPSpawns() {
        return vipSpawns;
    }

    public final void setVIPSpawns(final int spawns) {
        vipSpawns = spawns;
    }

    public final long getJailTime() {
        return timeJailed;
    }

    public final void setJailTime(final long time) {
        timeJailed = time;
    }

    public final long getJailSentence() {
        return jailSentence;
    }

    public final void setJailSentence(final long sentence) {
        jailSentence = sentence;
    }

    public final boolean isJailed() {
        return (timeJailed > 0);
    }

    public final boolean hasGodMode() {
        return hasGodMode;
    }

    public final boolean toggleGodMode() {
        hasGodMode ^= true;
        return hasGodMode;
    }

    public final void setGodMode(final boolean mode) {
        hasGodMode = mode;
    }

    public final void chat(final ChatChannel channel, final String message) {
        if(player != null) {
            final ChatChannel temp = this.chatChannel;
            this.chatChannel = channel;
            player.chat(message);
            this.chatChannel = temp;
        }
    }

    public final boolean canAfford(final int price) {
        return money >= price || hasPerm(Perm.MIDAS);
    }

    public final void setWager(final int wager) {
        this.wager = wager;
    }

    public final void debit(final int amount) {
        money -= amount;
    }

    public final void credit(final int amount) {
        money += amount;
    }

    public final void setMoney(final int amount) {
        money = amount;
    }

    public final boolean isAfk() {
        return isAfk;
    }

    public final boolean toggleAfk() {
        isAfk ^= true;
        return isAfk;
    }

    public final void sendWhisper(final String message) {
        sendMessage(Color.WHISPER + message);
    }

    public final void move(final Location location) {
        if(player != null) {
            final Location destination = location;
            destination.setPitch(player.getLocation().getPitch());
            destination.setYaw(player.getLocation().getYaw());
            quietTeleport(destination);
        }
    }

    public final boolean quietTeleport(final Location location) {
        if(location == null) {
            return false;
        } else {
            if(player != null) {
                player.teleport(location);
            }
            return true;
        }
    }

    public final boolean quietTeleport(final Warp warp) {
        if(warp == null) {
            return false;
        } else {
            if(player == null) {
                return warp.getLocation() != null;
            } else {
                return player.teleport(warp.getLocation());
            }
        }
    }

    public final boolean hasPerm(final Perm perm) {
        if(perm.isHereditary()) {
            return perm.getRank().compareTo(rank) <= 0;
        } else {
            return perm.getRank().equals(rank);
        }
    }

    public final void setDisplayName(final String name) {
        nick = name;
    }

    public final String getName() {
        return player == null ? name : player.getName();
    }

    public final void sendMessage(final String message) {
        if(player != null) {
            player.sendMessage(message);
        }
    }

    public final void sendPrivate(final String message) {
        sendMessage(Color.PRIVATE + message);
    }

    public final void sendNormal(final String message) {
        sendMessage(Color.MESSAGE + message);
    }

    public final void sendError(final String message) {
        sendMessage(Color.ERROR + message);
    }

    public final void sendClan(final String message) {
        sendMessage(Color.CLAN + message);
    }

    public final void sendBroadcast(final String message) {
        sendMessage(Color.BROADCAST + message);
    }

    public final void sendListItem(final String item, final String message) {
        sendMessage(Color.ITEM + item + Color.TEXT + message);
    }

    public final void sendText(final String message) {
        sendMessage(Color.TEXT + message);
    }

    public final boolean isOnline() {
        return player == null ? false : player.isOnline();
    }

    public final void setFoodLevel(final int level) {
        if(player != null) {
            player.setFoodLevel(level);
        }
    }

    public final Location getLocation() {
        return player == null ? null : player.getLocation();
    }

    public final World getWorld() {
        return player == null ? null : player.getWorld();
    }

    public final long getLastPlayed() {
        return player == null ? Bukkit.getOfflinePlayer(name).getLastPlayed() : player.getLastPlayed();
    }

    public final void kickPlayer(final String message) {
        if(player != null) {
            player.kickPlayer(message);
        }
    }

    public final void setBanned(final boolean banned) {
        if(player == null) {
            Bukkit.getOfflinePlayer(name).setBanned(banned);
        } else {
            player.setBanned(banned);
        }
    }

    public final boolean isBanned() {
        return player == null ? Bukkit.getOfflinePlayer(name).isBanned() : player.isBanned();
    }

    public final PlayerInventory getInventory() {
        return player == null ? null : player.getInventory();
    }

    public final boolean teleport(final Location location) {
        return player == null? false : player.teleport(location);
    }

    public final ItemStack getItemInHand() {
        return player == null ? null : player.getItemInHand();
    }

    public final boolean performCommand(final String command) {
        return player == null ? false : player.performCommand(command);
    }

    public final boolean isOp() {
        return player == null ? false : player.isOp();
    }

    public final boolean isSneaking() {
        return player == null ? false : player.isSneaking();
    }

    public final void setItemInHand(final ItemStack item) {
        if(player != null) {
            player.setItemInHand(item);
        }
    }

    public final Block getTargetBlock(final HashSet<Byte> transparent, final int range) {
        return player == null ? null : player.getTargetBlock(transparent, range);
    }

    public final List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent, final int range) {
        return player == null ? null : player.getLastTwoTargetBlocks(transparent, range);
    }

    public final Player getKiller() {
        return player == null ? null : player.getKiller();
    }

    public final GameMode getGameMode() {
        return player == null ? null : player.getGameMode();
    }

    @SuppressWarnings("deprecation")
    public final void updateInventory() {
        if(player != null) {
            player.updateInventory();
        }
    }

    public final void teleport(final SaveablePlayer target) {
        if(target != null) {
            player.teleport(target.getBase());
        }
    }

    public final void teleportHere(final Entity entity) {
        if(player != null) {
            entity.teleport(player);
        }
    }

    public final void setPet(final Tameable pet) {
        if(player != null) {
            pet.setOwner(player);
        }
    }

    public final void giveExpLevels(final int levels) {
        if(player != null) {
            player.giveExpLevels(levels);
        }
    }

    public final void setExp(final int exp) {
        if(player != null) {
            player.setExp(exp);
        }
    }

    public final void setLevel(final int level) {
        if(player != null) {
            player.setLevel(level);
        }
    }

    public final int getLevel() {
        return player == null ? 0 : player.getLevel();
    }

    public final double getMaxHealth() {
        return player == null ? 0 : player.getMaxHealth();
    }

    public final void setHealth(final double health) {
        if(player != null) {
            player.setHealth(health);
        }
    }

    public final Location getBedSpawnLocation() {
        return player == null ? null : player.getBedSpawnLocation();
    }

    public final List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return player == null ? null : player.getNearbyEntities(x, y, z);
    }

    public final boolean isInsideVehicle() {
        return player == null ? false : player.isInsideVehicle();
    }

    public final Entity getVehicle() {
        return player == null ? null : player.getVehicle();
    }

    public final ChatColor getRankColor() {
        return rank.getColor();
    }

    public final boolean outRanks(final SaveablePlayer player) {
        return !player.hasRank(rank);
    }

    public final String getRankName() {
        return rank.name();
    }

    public final boolean sameRank(final SaveablePlayer player) {
        return player.isRank(rank);
    }

    public final PlayerRank getRank() {
        return rank;
    }

    public final void togglePvp() {
        pvp ^= true;
        if(pvp) {
            pvpTime = System.currentTimeMillis();
            kills = 0;
            final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            final Objective objective = board.registerNewObjective("PvP", "dummy");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("PvP");
            objective.getScore(player).setScore(kills);
            player.setScoreboard(board);
        } else {
            pvpTime = 0;
            kills = 0;
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public final int getKills() {
        return kills;
    }

    public final long getPvpTime() {
        return pvpTime;
    }
    public final void newLogin(final long time) {
        lastLogins.add(time);
        while(lastLogins.size() > 2) {
            lastLogins.remove();
        }
    }
    public final boolean isActive() {
        return lastLogins.size() == 2 && System.currentTimeMillis() - TimeUtils.WEEK < lastLogins.get(1);
    }
}
