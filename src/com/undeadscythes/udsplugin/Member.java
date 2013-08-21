package com.undeadscythes.udsplugin;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class Member {
    protected OfflinePlayer player;
    private PlayerMetaUtils meta;

    public Member(final OfflinePlayer player) {
        this.player = player;
        try {
            meta = new PlayerMetaUtils(player);
            MetaCore.attach(player);
        } catch (NotMetadatableTypeException ex) {} catch (IOException ex) {}
    }

    public Member(final String record, final OfflinePlayer player) {
        this.player = player;
        try {
            meta = new PlayerMetaUtils(player);
        } catch (NotMetadatableTypeException ex) {}
        String[] recordSplit = record.split("\t");
        meta.set(PlayerKey.BOUNTY, Integer.parseInt(recordSplit[1]));
        meta.set(PlayerKey.MONEY, Integer.parseInt(recordSplit[2]));
        meta.set(PlayerKey.RANK, PlayerRank.getByName(recordSplit[3]).name());
        meta.set(PlayerKey.VIP_TIME, Long.parseLong(recordSplit[4]));
        meta.set(PlayerKey.VIP_SPAWNS, Integer.parseInt(recordSplit[5]));
        meta.set(PlayerKey.JAIL_TIME, Long.parseLong(recordSplit[6]));
        meta.set(PlayerKey.JAIL_SENTENCE, Long.parseLong(recordSplit[7]));
        meta.set(PlayerKey.BAIL, Integer.parseInt(recordSplit[8]));
        meta.set(PlayerKey.NICK, recordSplit[9]);
        meta.set(PlayerKey.TIME_LOGGED, Long.parseLong(recordSplit[10]));
        if(recordSplit.length == 13) {
            meta.set(PlayerKey.PVP_TIME, Long.parseLong(recordSplit[11]));
            meta.set(PlayerKey.KILLS, Integer.parseInt(recordSplit[12]));
        }
    }

    public void setupPlayer() {
        player.getPlayer().setDisplayName(getNick());
        player.getPlayer().setPlayerListName(getRank().getColor() + getNick());
        meta.set(PlayerKey.LAST_VECTOR, player.getPlayer().getLocation().toVector());
    }

//    protected OfflinePlayer getPlayer() {
//        return player;
//    }
//
    public void setVipForLife(boolean setting) {
        meta.set(PlayerKey.VIP_FOR_LIFE, setting);
    }

    public boolean isVipForLife() {
        return meta.getBool(PlayerKey.VIP_FOR_LIFE);
    }

    public void addTime(final long time) {
        meta.set(PlayerKey.TIME_LOGGED, getTimeLogged() + time);
    }

    public Vector getLastVector() throws NoMetadataSetException {
        return meta.getVector(PlayerKey.LAST_VECTOR);
    }

    public void setLastVector(final Vector vector) {
        meta.set(PlayerKey.LAST_VECTOR, vector);
    }

    public int addKill() {
        int kills = 1;
        try {
            kills += meta.getInt(PlayerKey.KILLS);
        } catch (NoMetadataSetException ex) {}
        meta.set(PlayerKey.PVP_TIME, System.currentTimeMillis());
        meta.set(PlayerKey.KILLS, kills);
        player.getPlayer().getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        return kills;
    }

    public int removeKill() {
        int kills = 0;
        try {
            kills = meta.getInt(PlayerKey.KILLS) - 1;
        } catch (NoMetadataSetException ex) {}
        meta.set(PlayerKey.PVP_TIME, System.currentTimeMillis());
        meta.set(PlayerKey.KILLS, kills);
        player.getPlayer().getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        return kills;
    }

    public void toggleLockdownPass() {
        boolean pass = meta.getBool(PlayerKey.LOCKDOWN_PASS);
        meta.set(PlayerKey.LOCKDOWN_PASS, !pass);
    }

    public UUID getSelectedPet() throws NoMetadataSetException {
        return meta.getUUID(PlayerKey.PET);
    }

    public void selectPet(final UUID id) {
        meta.set(PlayerKey.PET, id);
    }

    public boolean isShopping() {
        return meta.getBool(PlayerKey.SHOPPING);
    }

    public void setShopping(final boolean isShopping) {
        meta.set(PlayerKey.SHOPPING, isShopping);
    }

    public void setWhisperer(final Member player) {
        meta.set(PlayerKey.WHISPERER, player);
    }

    public boolean toggleHidden() {
        boolean hidden = !meta.getBool(PlayerKey.HIDDEN);
        meta.set(PlayerKey.HIDDEN, hidden);
        return hidden;
    }

    public boolean isHidden() {
        return meta.getBool(PlayerKey.HIDDEN);
    }

    public int getWager() {
        try {
            return meta.getInt(PlayerKey.WAGER);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean isBuying() {
        return meta.getBool(PlayerKey.BUYING);
    }

    public boolean hasPvp() {
        return meta.getBool(PlayerKey.PVP);
    }

    public void setBuying(final boolean isBuying) {
        meta.set(PlayerKey.BUYING, isBuying);
    }

    public Location getShop() throws NoMetadataSetException {
        return meta.getLocation(PlayerKey.SHOP);
    }

    public void setShop(final Location shop) {
        meta.set(PlayerKey.SHOP, shop);
    }

    public Member getWhisperer() throws NoMetadataSetException {
        return meta.getSaveablePlayer(PlayerKey.WHISPERER);
    }

    public int getPowertoolID() throws NoMetadataSetException {
        return meta.getInt(PlayerKey.POWERTOOL_ID);
    }

    public String getPowertool() {
        try {
            return meta.getString(PlayerKey.POWERTOOL_COMMAND);
        } catch (NoMetadataSetException ex) {
            return "";
        }
    }

    public void claimPrize() {
        meta.set(PlayerKey.LAST_PRIZE_CLAIMED, System.currentTimeMillis());
    }

    public boolean hasClaimedPrize() {
        try {
            return (meta.getLong(PlayerKey.LAST_PRIZE_CLAIMED) + TimeUtils.DAY > System.currentTimeMillis());
        } catch (NoMetadataSetException ex) {
            return false;
        }
    }

    public EditSession forceSession() {
        EditSession session = UDSPlugin.getSession(getName());
        if(session == null) {
            session = new EditSession();
            UDSPlugin.addSession(getName(), session);
        }
        return session;
    }

    public void setPowertoolID(final int ID) {
        meta.set(PlayerKey.POWERTOOL_ID, ID);
    }

    public void setPowertool(final String cmd) {
        meta.set(PlayerKey.POWERTOOL_COMMAND, cmd);
    }

    public ItemStack[] getInventoryCopy() {
        try {
            return meta.getArray(PlayerKey.INVENTORY, new ItemStack[36]);
        } catch (NoMetadataSetException ex) {
            return new ItemStack[36];
        }
    }

    public boolean isInShop(final Location location) {
        for(Region region : RegionUtils.getRegions(RegionType.SHOP)) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                return true;
            }
        }
        return false;
    }

    public void setClan(final Clan clan) {
        meta.set(PlayerKey.CLAN, clan);
    }

    public void endChallenge() {
        meta.remove(PlayerKey.WAGER);
        meta.remove(PlayerKey.CHALLENGER);
    }

    public int getBail() {
        try {
            return meta.getInt(PlayerKey.BAIL);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean newChat() {
        List<Long> lastChats;
        try {
            lastChats = meta.getList(PlayerKey.LAST_CHATS, new LinkedList<Long>());
        } catch (NoMetadataSetException ex) {
            lastChats = new ArrayList<Long>(5);
        }
        LinkedList<Long> lastChatTimes = new LinkedList<Long>(lastChats);
        if(lastChatTimes.size() > 5) {
            lastChatTimes.removeFirst();
        }
        lastChatTimes.offerLast(System.currentTimeMillis());
        if(lastChatTimes.size() == 5 && lastChatTimes.getLast() - lastChatTimes.getFirst() < 3000) {
            return false;
        }
        return true;
    }

    public String getTimeLogged() {
        try {
            return TimeUtils.timeToString(meta.getLong(PlayerKey.TIME_LOGGED));
        } catch (NoMetadataSetException ex) {
            return "no time";
        }
    }

    public String getLastSeen() {
        return TimeUtils.timeToString(System.currentTimeMillis() - player.getLastPlayed());
    }

    public String getVIPTimeString() {
        try {
            return TimeUtils.timeToString(Config.VIP_TIME - System.currentTimeMillis() + meta.getLong(PlayerKey.VIP_TIME));
        } catch (NoMetadataSetException ex) {
            return "no time";
        }
    }

    public int useVIPSpawns(final int amount) throws NoMetadataSetException {
        int vipSpawns = meta.getInt(PlayerKey.VIP_SPAWNS) - amount;
        meta.set(PlayerKey.VIP_SPAWNS, vipSpawns);
        return vipSpawns;
    }

    public void setBackPoint(final Location location) {
        meta.set(PlayerKey.BACK_POINT, location);
    }

    public int getMoney() {
        try {
            return meta.getInt(PlayerKey.MONEY);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public ChatRoom getChatRoom() throws NoMetadataSetException {
        return meta.getChatRoom(PlayerKey.CHATROOM);
    }

    public void jail(final long sentence, final int bail) {
        meta.set(PlayerKey.JAIL_TIME, System.currentTimeMillis());
        meta.set(PlayerKey.JAIL_SENTENCE, sentence * TimeUtils.MINUTE);
        meta.set(PlayerKey.BAIL, bail);
    }

    public PlayerRank demote() {
        final PlayerRank newRank = PlayerRank.getBelow(getRank());
        if(newRank != null) {
            meta.set(PlayerKey.RANK, newRank.name());
        }
        return newRank;
    }

    public PlayerRank promote() {
        final PlayerRank newRank = PlayerRank.getAbove(getRank());
        if(newRank == PlayerRank.OWNER) {
            return null;
        } else if(newRank != null) {
            meta.set(PlayerKey.RANK, newRank.name());
        }
        return newRank;
    }

    public ChatChannel getChannel() {
        try {
            return meta.getChatChannel(PlayerKey.CHAT_CHANNEL);
        } catch (NoMetadataSetException ex) {
            return ChatChannel.PUBLIC;
        }
    }

    public Location getCheckPoint() throws NoMetadataSetException {
        return meta.getLocation(PlayerKey.CHECK_POINT);
    }

    public void setCheckPoint(final Location location) {
        meta.set(PlayerKey.CHECK_POINT, location);
    }

    public boolean isDuelling() {
        return meta.exists(PlayerKey.CHALLENGER);
    }

    public void setChallenger(final Member challenger) {
        meta.set(PlayerKey.CHALLENGER, challenger);
    }

    public Member getChallenger() throws NoMetadataSetException {
        return meta.getSaveablePlayer(PlayerKey.CHALLENGER);
    }

    public boolean canBuildHere(final Location location) {
        boolean contained = false;
        for(Region region : RegionUtils.getRegions()) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                Bukkit.getLogger().info(region.hasMember(this) + "Cant build here");
                if(((region.getRank() != null && getRank().compareTo(region.getRank()) >= 0)) || region.isOwnedBy(this) || region.hasMember(this)) {
                    return true;
                }
                contained = true;
            }
        }
        return contained ? false : UDSPlugin.checkWorldFlag(location.getWorld(), WorldFlag.BUILD);
    }

    public boolean ignorePlayer(final Member player) {
        try {
            return meta.getSet(PlayerKey.IGNORES, new HashSet<Member>(0)).add(player);
        } catch (NoMetadataSetException ex) {
            HashSet<Member> ignores = new HashSet<Member>(1);
            ignores.add(player);
            meta.set(PlayerKey.IGNORES, ignores);
            return false;
        }
    }

    public boolean unignorePlayer(final Member player) {
        try {
            return meta.getSet(PlayerKey.IGNORES, new HashSet<Member>(0)).remove(player);
        } catch (NoMetadataSetException ex) {
            return false;
        }
    }

    public boolean isIgnoringPlayer(final Member player) {
        try {
            return meta.getSet(PlayerKey.IGNORES, new HashSet<Member>(0)).contains(player);
        } catch (NoMetadataSetException ex) {
            return false;
        }
    }

    public long getLastDamageCaused() throws NoMetadataSetException {
        return meta.getLong(PlayerKey.LAST_ATTACKED);
    }

    public int getBounty() {
        try {
            return meta.getInt(PlayerKey.BOUNTY);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setBounty(final int bounty) {
        meta.set(PlayerKey.BOUNTY, bounty);
    }

    public void addBounty(final int bounty) {
        setBounty(getBounty() + bounty);
    }

    public Location getBack() throws NoMetadataSetException {
        return meta.getLocation(PlayerKey.BACK_POINT);
    }

    public boolean hasRank(final PlayerRank rank) {
        return getRank().compareTo(rank) >= 0;
    }

    public boolean isRank(final PlayerRank rank) {
        return getRank().equals(rank);
    }

    public void setRank(final PlayerRank rank) {
        meta.set(PlayerKey.RANK, rank.name());
    }

    public boolean isInClan() {
        return meta.exists(PlayerKey.CLAN);
    }

    public Clan getClan() throws NoMetadataSetException {
        return meta.getClan(PlayerKey.CLAN);
    }

    public String getNick() {
        try {
            return meta.getString(PlayerKey.NICK);
        } catch (NoMetadataSetException ex) {
            return player.getName();
        }
    }

    public boolean toggleChannel(final ChatChannel channel) {
        if(!getChannel().equals(channel)) {
            meta.set(PlayerKey.CHAT_CHANNEL, channel);
            return true;
        }
        meta.set(PlayerKey.CHAT_CHANNEL, ChatChannel.PUBLIC);
        return false;
    }

    public boolean hasLockdownPass() {
        return meta.getBool(PlayerKey.LOCKDOWN_PASS);
    }

    public void setLoadItems(final boolean loadItems) {
        meta.set(PlayerKey.LOADITEMS, loadItems);
    }

    public boolean hasLoadItems() {
        return meta.getBool(PlayerKey.LOADITEMS);
    }

    public long getVIPTime() throws NoMetadataSetException {
        return meta.getLong(PlayerKey.VIP_TIME);
    }

    public void setVIPTime(final long time) {
        meta.set(PlayerKey.VIP_TIME, time);
    }

    public int getVIPSpawns() {
        try {
            return meta.getInt(PlayerKey.VIP_SPAWNS);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setVIPSpawns(final int spawns) {
        meta.set(PlayerKey.VIP_SPAWNS, spawns);
    }

    public long getJailTime() throws NoMetadataSetException {
        return meta.getLong(PlayerKey.JAIL_TIME);
    }

    public void setJailTime(final long time) {
        meta.set(PlayerKey.JAIL_TIME, time);
    }

    public long getJailSentence() {
        try {
            return meta.getLong(PlayerKey.JAIL_SENTENCE);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setJailSentence(final long sentence) {
        meta.set(PlayerKey.JAIL_SENTENCE, sentence);
    }

    public boolean isJailed() {
        return meta.exists(PlayerKey.JAIL_TIME);
    }

    public boolean hasGodMode() {
        return meta.getBool(PlayerKey.GODMODE);
    }

    public boolean toggleGodMode() {
        boolean hasGodMode = !meta.getBool(PlayerKey.GODMODE);
        meta.set(PlayerKey.GODMODE, hasGodMode);
        return hasGodMode;
    }

    public void setGodMode(final boolean mode) {
        meta.set(PlayerKey.GODMODE, mode);
    }

    public boolean canAfford(final int price) {
        return getMoney() >= price || hasPerm(Perm.MIDAS);
    }

    public void setWager(final int wager) {
        meta.set(PlayerKey.WAGER, wager);
    }

    public void debit(final int amount) {
        meta.set(PlayerKey.MONEY, getMoney() - amount);
    }

    public void credit(final int amount) {
        meta.set(PlayerKey.MONEY, getMoney() + amount);
    }

    public void setMoney(final int amount) {
        meta.set(PlayerKey.MONEY, amount);
    }

    public boolean isAfk() {
        return meta.getBool(PlayerKey.AFK);
    }

    public boolean toggleAfk() {
        boolean isAfk = !meta.getBool(PlayerKey.AFK);
        meta.set(PlayerKey.AFK, isAfk);
        return isAfk;
    }

    public boolean hasPerm(final Perm perm) {
        if(perm.isHereditary()) {
            return perm.getRank().compareTo(getRank()) <= 0;
        } else {
            return perm.getRank().equals(getRank());
        }
    }

    public void setDisplayName(final String name) {
        meta.set(PlayerKey.NICK, name);
    }

    public String getName() {
        return player.getName();
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    public void setBanned(final boolean banned) {
        player.setBanned(banned);
    }

    public boolean isBanned() {
        return player.isBanned();
    }

    public boolean isOp() {
        return player.isOp();
    }

    public void setPet(final Tameable pet) {
        pet.setOwner(player);
    }

    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    public ChatColor getRankColor() {
        return getRank().getColor();
    }

    public boolean outRanks(final Member player) {
        return !player.hasRank(getRank());
    }

    public String getRankName() {
        return getRank().name();
    }

    public boolean sameRank(final Member player) {
        return player.isRank(getRank());
    }

    public PlayerRank getRank() {
        try {
            return meta.getPlayerRank(PlayerKey.RANK);
        } catch (NoMetadataSetException ex) {
            return PlayerRank.NEWBIE;
        }
    }

    public Scoreboard togglePvp() {
        boolean pvp = !meta.getBool(PlayerKey.PVP);
        if(pvp) {
            meta.set(PlayerKey.PVP_TIME, System.currentTimeMillis());
            meta.set(PlayerKey.KILLS, 0);
            final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            final Objective objective = board.registerNewObjective("PvP", "dummy");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("PvP");
            objective.getScore(player).setScore(0);
            player.getPlayer().setScoreboard(board);
            return board;
        } else {
            meta.remove(PlayerKey.PVP_TIME);
            meta.remove(PlayerKey.KILLS);
            return Bukkit.getScoreboardManager().getNewScoreboard();
        }
    }

    public int getKills() {
        try {
            return meta.getInt(PlayerKey.KILLS);
        } catch (NoMetadataSetException ex) {
            return 0;
        }
    }

    public long getPvpTime() throws NoMetadataSetException {
        return meta.getLong(PlayerKey.PVP_TIME);
    }

    public void newLogin(final long time) {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(PlayerKey.LAST_LOGINS, new LinkedList<Long>());
        } catch (NoMetadataSetException ex) {}
        lastLogins.add(time);
        while(lastLogins.size() > 2) {
            lastLogins.remove(2);
        }
    }

    public boolean isActive() {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(PlayerKey.LAST_LOGINS, new LinkedList<Long>());
        } catch (NoMetadataSetException ex) {}
        return lastLogins.size() == 2 && System.currentTimeMillis() - TimeUtils.WEEK < lastLogins.get(1);
    }

    public int getTotalExp() {
        return player.getPlayer().getTotalExperience();
    }

    public boolean hasScuba() {
        ItemStack helmet = player.getPlayer().getInventory().getHelmet();
        if(helmet != null && helmet.getType().equals(Material.GLASS)) return true;
        return false;
    }

    public int countItems(final ItemStack search) {
        final ItemStack[] inventory = player.getPlayer().getInventory().getContents();
        int count = 0;
        for(int i = 0; i < inventory.length; i++) {
            final ItemStack item = inventory[i];
            if(item != null && item.getType() == search.getType() && item.getData().getData() == search.getData().getData()) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public void setVehicle(final Entity vehicle) {
        vehicle.setPassenger(player.getPlayer());
    }

    public void saveInventory() {
        meta.set(PlayerKey.INVENTORY, player.getPlayer().getInventory().getContents());
    }

    public void saveItems() {
        saveInventory();
        saveArmor();
    }

    public void loadArmor() {
        try {
            player.getPlayer().getInventory().setArmorContents(meta.popItemStack(PlayerKey.ARMOR));
        } catch (NoMetadataSetException ex) {
            player.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
            player.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
        }
    }

    public void loadInventory() {
        try {
            player.getPlayer().getInventory().setContents(meta.popItemStack(PlayerKey.INVENTORY));
        } catch (NoMetadataSetException ex) {
            player.getPlayer().getInventory().clear();
        }
    }

    public void saveArmor() {
        meta.set(PlayerKey.ARMOR, player.getPlayer().getInventory().getArmorContents());
    }

    public void loadItems() {
        loadInventory();
        loadArmor();
    }

    public void setBackPoint() {
        setBackPoint(player.getPlayer().getLocation());
    }

    public boolean toggleGameMode() {
        if(player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            player.getPlayer().setGameMode(GameMode.CREATIVE);
            return true;
        } else {
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
            return false;
        }
    }

    public void release() {
        if(!quietTeleport(WarpUtils.getWarp("jailout")) && !quietTeleport(UDSPlugin.getWorldSpawn(player.getPlayer().getWorld()))) {
            player.getPlayer().teleport(UDSPlugin.getWorldSpawn(player.getPlayer().getWorld()));
        }
        meta.remove(PlayerKey.JAIL_TIME);
        meta.remove(PlayerKey.JAIL_SENTENCE);
    }

    public Region getCurrentRegion(final RegionType type) throws NoRegionFoundException {
        if(type == RegionType.CITY) {
            for(Region region : RegionUtils.getRegions(RegionType.CITY)) {
                if(player.getPlayer().getLocation().toVector().isInAABB(region.getV1(), region.getV2())) {
                    return region;
                }
            }
        } else if(type == RegionType.SHOP) {
            for(Region region : RegionUtils.getRegions(RegionType.SHOP)) {
                if(player.getPlayer().getLocation().toVector().isInAABB(region.getV1(), region.getV2())) {
                    return region;
                }
            }
        }
        throw new NoRegionFoundException(type, player.getPlayer().getLocation());
    }

    public void hideFrom(final Member player, final boolean hide) {
        hideFrom(player.player.getPlayer(), hide);
    }

    public void hideFrom(final Player player, final boolean hide) {
        if(hide) {
            this.player.getPlayer().hidePlayer(player);
        } else {
            this.player.getPlayer().showPlayer(player);
        }
    }

    public void giveAndDrop(final ItemStack item) {
        final Map<Integer, ItemStack> drops = player.getPlayer().getInventory().addItem(item);
        for(ItemStack drop : drops.values()) {
            player.getPlayer().getWorld().dropItemNaturally(player.getPlayer().getLocation(), drop);
        }
    }

    public void chat(final ChatChannel channel, final String message) {
        final ChatChannel temp = getChannel();
        meta.set(PlayerKey.CHAT_CHANNEL, channel);
        player.getPlayer().chat(message);
        meta.set(PlayerKey.CHAT_CHANNEL, temp);
    }

    public void sendWhisper(final String message) {
        sendMessage(Color.WHISPER + message);
    }

    public void move(final Location location) {
        final Location destination = location;
        destination.setPitch(player.getPlayer().getLocation().getPitch());
        destination.setYaw(player.getPlayer().getLocation().getYaw());
        quietTeleport(destination);
    }

    public boolean quietTeleport(final Location location) {
        if(location == null) {
            return false;
        } else {
            player.getPlayer().teleport(location);
            return true;
        }
    }

    public boolean quietTeleport(final Warp warp) {
        if(warp == null) {
            return false;
        } else {
            return player.getPlayer().teleport(warp.getLocation());
        }
    }

    public void sendMessage(final String message) {
        player.getPlayer().sendMessage(message);
    }

    public void sendPrivate(final String message) {
        sendMessage(Color.PRIVATE + message);
    }

    public void sendNormal(final String message) {
        sendMessage(Color.MESSAGE + message);
    }

    public void sendError(final String message) {
        sendMessage(Color.ERROR + message);
    }

    public void sendClan(final String message) {
        sendMessage(Color.CLAN + message);
    }

    public void sendBroadcast(final String message) {
        sendMessage(Color.BROADCAST + message);
    }

    public void sendListItem(final String item, final String message) {
        sendMessage(Color.ITEM + item + Color.TEXT + message);
    }

    public void sendText(final String message) {
        sendMessage(Color.TEXT + message);
    }

    public void setFoodLevel(final int level) {
        player.getPlayer().setFoodLevel(level);
    }

    public Location getLocation() {
        return player.getPlayer().getLocation();
    }

    public World getWorld() {
        return player.getPlayer().getWorld();
    }

    public void kickPlayer(final String message) {
        player.getPlayer().kickPlayer(message);
    }

    public PlayerInventory getInventory() {
        return player.getPlayer().getInventory();
    }

    public boolean teleport(final Location location) {
        return player.getPlayer().teleport(location);
    }

    public ItemStack getItemInHand() {
        return player.getPlayer().getItemInHand();
    }

    public boolean performCommand(final String command) {
        return player.getPlayer().performCommand(command);
    }

    public boolean isSneaking() {
        return player.getPlayer().isSneaking();
    }

    public void setItemInHand(final ItemStack item) {
        player.getPlayer().setItemInHand(item);
    }

    public Block getTargetBlock(final HashSet<Byte> transparent, final int range) {
        return player.getPlayer().getTargetBlock(transparent, range);
    }

    public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent, final int range) {
        return player.getPlayer().getLastTwoTargetBlocks(transparent, range);
    }

    public Player getKiller() {
        return player.getPlayer().getKiller();
    }

    public GameMode getGameMode() {
        return player.getPlayer().getGameMode();
    }

    @SuppressWarnings("deprecation")
    public void updateInventory() {
        player.getPlayer().updateInventory();
    }

    public void teleport(final Member target) {
        player.getPlayer().teleport(target.getLocation());
    }

    public void teleportHere(final Entity entity) {
        entity.teleport(player.getPlayer());
    }

    public void giveExpLevels(final int levels) {
        player.getPlayer().giveExpLevels(levels);
    }

    public void setExp(final int exp) {
        player.getPlayer().setExp(exp);
    }

    public void setLevel(final int level) {
        player.getPlayer().setLevel(level);
    }

    public int getLevel() {
        return player.getPlayer().getLevel();
    }

    public double getMaxHealth() {
        return player.getPlayer().getMaxHealth();
    }

    public void setHealth(final double health) {
        player.getPlayer().setHealth(health);
    }

    public List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return player.getPlayer().getNearbyEntities(x, y, z);
    }

    public boolean isInsideVehicle() {
        return player.getPlayer().isInsideVehicle();
    }

    public Entity getVehicle() {
        return player.getPlayer().getVehicle();
    }
}
