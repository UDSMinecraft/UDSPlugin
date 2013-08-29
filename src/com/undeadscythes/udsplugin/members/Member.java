package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.MessageReciever;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Warp;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
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
public class Member extends MessageReciever {
    private Player player; //TODO: check return and meta save types of all these methods i.e. online/offline member
    private OfflineMember parent;
    private MemberMetaUtils meta;

    public Member(final Player player) throws NoPlayerFoundException {
        this.player = player;
        parent = MemberUtils.getMember(player.getName());
        meta = parent.getMeta();
    }

    public Member(final OfflineMember player) {
        this.player = player.getOfflinePlayer().getPlayer();
        parent = player;
        meta = parent.getMeta();
    }

    public OfflineMember getOfflineMember() {
        return parent;
    }

    public void setupPlayer() {
        player.setDisplayName(getNick());
        player.setPlayerListName(getRank().getColor() + getNick());
        meta.set(MemberKey.LAST_VECTOR, player.getLocation().toVector());
    }

    public void setVipForLife(boolean setting) {
        meta.set(MemberKey.VIP_FOR_LIFE, setting);
    }

    public boolean isVipForLife() {
        return meta.getBool(MemberKey.VIP_FOR_LIFE);
    }

    public void addTime(final long time) {
        long current = 0;
        try {
            current = meta.getLong(MemberKey.TIME_LOGGED);
        } catch (NoMetadataSetException ex) {}
        meta.set(MemberKey.TIME_LOGGED, current + time);
    }

    public Vector getLastVector() throws NoMetadataSetException {
        return meta.getVector(MemberKey.LAST_VECTOR);
    }

    public void setLastVector(final Vector vector) {
        meta.set(MemberKey.LAST_VECTOR, vector);
    }

    public int addKill() {
        int kills = 1;
        try {
            kills += meta.getInt(MemberKey.KILLS);
        } catch(NoMetadataSetException ex) {}
        meta.set(MemberKey.PVP_TIME, System.currentTimeMillis());
        meta.set(MemberKey.KILLS, kills);
        player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        return kills;
    }

    public int removeKill() {
        int kills = 0;
        try {
            kills = meta.getInt(MemberKey.KILLS) - 1;
        } catch(NoMetadataSetException ex) {}
        meta.set(MemberKey.PVP_TIME, System.currentTimeMillis());
        meta.set(MemberKey.KILLS, kills);
        player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME).getScore(player).setScore(kills);
        return kills;
    }

    public void toggleLockdownPass() {
        boolean pass = meta.getBool(MemberKey.LOCKDOWN_PASS);
        meta.set(MemberKey.LOCKDOWN_PASS, !pass);
    }

    public UUID getSelectedPet() throws NoMetadataSetException {
        return meta.getUUID(MemberKey.PET);
    }

    public void selectPet(final UUID id) {
        meta.set(MemberKey.PET, id);
    }

    public boolean isShopping() {
        return meta.getBool(MemberKey.SHOPPING);
    }

    public void setShopping(final boolean isShopping) {
        meta.set(MemberKey.SHOPPING, isShopping);
    }

    public void setWhisperer(final Member member) {
        meta.set(MemberKey.WHISPERER, member);
    }

    public boolean toggleHidden() {
        boolean hidden = !meta.getBool(MemberKey.HIDDEN);
        meta.set(MemberKey.HIDDEN, hidden);
        return hidden;
    }

    public boolean isHidden() {
        return meta.getBool(MemberKey.HIDDEN);
    }

    public int getWager() {
        try {
            return meta.getInt(MemberKey.WAGER);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean isBuying() {
        return meta.getBool(MemberKey.BUYING);
    }

    public boolean hasPvp() {
        return meta.getBool(MemberKey.PVP);
    }

    public void setBuying(final boolean isBuying) {
        meta.set(MemberKey.BUYING, isBuying);
    }

    public Location getShop() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.SHOP);
    }

    public void setShop(final Location shop) {
        meta.set(MemberKey.SHOP, shop);
    }

    public OfflineMember getWhisperer() throws NoMetadataSetException {
        return meta.getSaveablePlayer(MemberKey.WHISPERER);
    }

    public int getPowertoolID() throws NoMetadataSetException {
        return meta.getInt(MemberKey.POWERTOOL_ID);
    }

    public String getPowertool() {
        try {
            return meta.getString(MemberKey.POWERTOOL_COMMAND);
        } catch(NoMetadataSetException ex) {
            return "";
        }
    }

    public void claimPrize() {
        meta.set(MemberKey.LAST_PRIZE_CLAIMED, System.currentTimeMillis());
    }

    public boolean hasClaimedPrize() {
        try {
            return (meta.getLong(MemberKey.LAST_PRIZE_CLAIMED) + TimeUtils.DAY > System.currentTimeMillis());
        } catch(NoMetadataSetException ex) {
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
        meta.set(MemberKey.POWERTOOL_ID, ID);
    }

    public void setPowertool(final String cmd) {
        meta.set(MemberKey.POWERTOOL_COMMAND, cmd);
    }

    public ItemStack[] getInventoryCopy() {
        try {
            return meta.getArray(MemberKey.INVENTORY, new ItemStack[36]);
        } catch(NoMetadataSetException ex) {
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
        meta.set(MemberKey.CLAN, clan);
    }

    public void endChallenge() {
        meta.remove(MemberKey.WAGER);
        meta.remove(MemberKey.CHALLENGER);
    }

    public int getBail() {
        try {
            return meta.getInt(MemberKey.BAIL);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public boolean newChat() {
        List<Long> lastChats;
        try {
            lastChats = meta.getList(MemberKey.LAST_CHATS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {
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
            return TimeUtils.timeToString(meta.getLong(MemberKey.TIME_LOGGED));
        } catch(NoMetadataSetException ex) {
            return "no time";
        }
    }

    public String getLastSeen() {
        return TimeUtils.timeToString(System.currentTimeMillis() - parent.getLastPlayed());
    }

    public String getVIPTimeString() {
        try {
            return TimeUtils.timeToString(Config.VIP_TIME - System.currentTimeMillis() + meta.getLong(MemberKey.VIP_TIME));
        } catch(NoMetadataSetException ex) {
            return "no time";
        }
    }

    public int useVIPSpawns(final int amount) throws NoMetadataSetException {
        int vipSpawns = meta.getInt(MemberKey.VIP_SPAWNS) - amount;
        meta.set(MemberKey.VIP_SPAWNS, vipSpawns);
        return vipSpawns;
    }

    public void setBackPoint(final Location location) {
        meta.set(MemberKey.BACK_POINT, location);
    }

    public int getMoney() {
        try {
            return meta.getInt(MemberKey.MONEY);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public ChatRoom getChatRoom() throws NoMetadataSetException {
        return meta.getChatRoom(MemberKey.CHATROOM);
    }

    public void jail(final long sentence, final int bail) {
        meta.set(MemberKey.JAIL_TIME, System.currentTimeMillis());
        meta.set(MemberKey.JAIL_SENTENCE, sentence * TimeUtils.MINUTE);
        meta.set(MemberKey.BAIL, bail);
    }

    public MemberRank demote() {
        final MemberRank newRank = MemberRank.getBelow(getRank());
        if(newRank != null) {
            meta.set(MemberKey.RANK, newRank.name());
        }
        return newRank;
    }

    public MemberRank promote() {
        final MemberRank newRank = MemberRank.getAbove(getRank());
        if(newRank == MemberRank.OWNER) {
            return null;
        } else if(newRank != null) {
            meta.set(MemberKey.RANK, newRank.name());
        }
        return newRank;
    }

    public ChatChannel getChannel() {
        try {
            return meta.getChatChannel(MemberKey.CHAT_CHANNEL);
        } catch(NoMetadataSetException ex) {
            return ChatChannel.PUBLIC;
        }
    }

    public Location getCheckPoint() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.CHECK_POINT);
    }

    public void setCheckPoint(final Location location) {
        meta.set(MemberKey.CHECK_POINT, location);
    }

    public boolean isDuelling() {
        return meta.exists(MemberKey.CHALLENGER);
    }

    public void setChallenger(final Member challenger) {
        meta.set(MemberKey.CHALLENGER, challenger.getOfflineMember());
    }

    public OfflineMember getChallenger() throws NoMetadataSetException {
        return meta.getSaveablePlayer(MemberKey.CHALLENGER);
    }

    public boolean canBuildHere(final Location location) {
        boolean contained = false;
        for(Region region : RegionUtils.getRegions()) {
            if(location.toVector().isInAABB(region.getV1(), region.getV2())) {
                Bukkit.getLogger().info(region.hasMember(parent) + "Cant build here");
                if(((region.getRank() != null && getRank().compareTo(region.getRank()) >= 0)) || region.isOwnedBy(parent) || region.hasMember(parent)) {
                    return true;
                }
                contained = true;
            }
        }
        return contained ? false : UDSPlugin.checkWorldFlag(location.getWorld(), WorldFlag.BUILD);
    }

    public boolean ignorePlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).add(player);
        } catch(NoMetadataSetException ex) {
            HashSet<OfflineMember> ignores = new HashSet<OfflineMember>(1);
            ignores.add(player);
            meta.set(MemberKey.IGNORES, ignores);
            return false;
        }
    }

    public boolean unignorePlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).remove(player);
        } catch(NoMetadataSetException ex) {
            return false;
        }
    }

    public boolean isIgnoringPlayer(final OfflineMember player) {
        try {
            return meta.getSet(MemberKey.IGNORES, new HashSet<OfflineMember>(0)).contains(player);
        } catch(NoMetadataSetException ex) {
            return false;
        }
    }

    public long getLastDamageCaused() throws NoMetadataSetException {
        return meta.getLong(MemberKey.LAST_ATTACKED);
    }

    public int getBounty() {
        try {
            return meta.getInt(MemberKey.BOUNTY);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setBounty(final int bounty) {
        meta.set(MemberKey.BOUNTY, bounty);
    }

    public void addBounty(final int bounty) {
        setBounty(getBounty() + bounty);
    }

    public Location getBack() throws NoMetadataSetException {
        return meta.getLocation(MemberKey.BACK_POINT);
    }

    public boolean hasRank(final MemberRank rank) {
        return getRank().compareTo(rank) >= 0;
    }

    public boolean isRank(final MemberRank rank) {
        return getRank().equals(rank);
    }

    public void setRank(final MemberRank rank) {
        meta.set(MemberKey.RANK, rank.name());
    }

    public boolean isInClan() {
        return meta.exists(MemberKey.CLAN);
    }

    public Clan getClan() throws NoMetadataSetException {
        return meta.getClan(MemberKey.CLAN);
    }

    public String getNick() {
        try {
            return meta.getString(MemberKey.NICK);
        } catch(NoMetadataSetException ex) {
            return parent.getName();
        }
    }

    public boolean toggleChannel(final ChatChannel channel) {
        if(!getChannel().equals(channel)) {
            meta.set(MemberKey.CHAT_CHANNEL, channel);
            return true;
        }
        meta.set(MemberKey.CHAT_CHANNEL, ChatChannel.PUBLIC);
        return false;
    }

    public boolean hasLockdownPass() {
        return meta.getBool(MemberKey.LOCKDOWN_PASS);
    }

    public void setLoadItems(final boolean loadItems) {
        meta.set(MemberKey.LOADITEMS, loadItems);
    }

    public boolean hasLoadItems() {
        return meta.getBool(MemberKey.LOADITEMS);
    }

    public long getVIPTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.VIP_TIME);
    }

    public void setVIPTime(final long time) {
        meta.set(MemberKey.VIP_TIME, time);
    }

    public int getVIPSpawns() {
        try {
            return meta.getInt(MemberKey.VIP_SPAWNS);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setVIPSpawns(final int spawns) {
        meta.set(MemberKey.VIP_SPAWNS, spawns);
    }

    public long getJailTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.JAIL_TIME);
    }

    public void setJailTime(final long time) {
        meta.set(MemberKey.JAIL_TIME, time);
    }

    public long getJailSentence() {
        try {
            return meta.getLong(MemberKey.JAIL_SENTENCE);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public void setJailSentence(final long sentence) {
        meta.set(MemberKey.JAIL_SENTENCE, sentence);
    }

    public boolean isJailed() {
        return meta.exists(MemberKey.JAIL_TIME);
    }

    public boolean hasGodMode() {
        return meta.getBool(MemberKey.GODMODE);
    }

    public boolean toggleGodMode() {
        boolean hasGodMode = !meta.getBool(MemberKey.GODMODE);
        meta.set(MemberKey.GODMODE, hasGodMode);
        return hasGodMode;
    }

    public void setGodMode(final boolean mode) {
        meta.set(MemberKey.GODMODE, mode);
    }

    public boolean canAfford(final int price) {
        return getMoney() >= price || hasPerm(Perm.MIDAS);
    }

    public void setWager(final int wager) {
        meta.set(MemberKey.WAGER, wager);
    }

    public void debit(final int amount) {
        meta.set(MemberKey.MONEY, getMoney() - amount);
    }

    public void credit(final int amount) {
        meta.set(MemberKey.MONEY, getMoney() + amount);
    }

    public void setMoney(final int amount) {
        meta.set(MemberKey.MONEY, amount);
    }

    public boolean isAfk() {
        return meta.getBool(MemberKey.AFK);
    }

    public boolean toggleAfk() {
        boolean isAfk = !meta.getBool(MemberKey.AFK);
        meta.set(MemberKey.AFK, isAfk);
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
        meta.set(MemberKey.NICK, name);
    }

    public String getName() {
        return parent.getName();
    }

    public boolean isOnline() {
        return parent.isOnline();
    }

    public long getLastPlayed() {
        return parent.getLastPlayed();
    }

    public void setBanned(final boolean banned) {
        parent.setBanned(banned);
    }

    public boolean isBanned() {
        return parent.isBanned();
    }

    public boolean isOp() {
        return parent.isOp();
    }

    public void setPet(final Tameable pet) {
        pet.setOwner(player);
    }

    public Location getBedSpawnLocation() {
        return parent.getBedSpawnLocation();
    }

    public ChatColor getRankColor() {
        return getRank().getColor();
    }

    public boolean outRanks(final OfflineMember player) {
        return !player.hasRank(getRank());
    }

    public String getRankName() {
        return getRank().name();
    }

    public boolean sameRank(final OfflineMember player) {
        return player.isRank(getRank());
    }

    public MemberRank getRank() {
        try {
            return meta.getPlayerRank(MemberKey.RANK);
        } catch(NoMetadataSetException ex) {
            return MemberRank.NEWBIE;
        }
    }

    public Scoreboard togglePvp() {
        boolean pvp = !meta.getBool(MemberKey.PVP);
        if(pvp) {
            meta.set(MemberKey.PVP_TIME, System.currentTimeMillis());
            meta.set(MemberKey.KILLS, 0);
            final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            final Objective objective = board.registerNewObjective("PvP", "dummy");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("PvP");
            objective.getScore(player).setScore(0);
            player.setScoreboard(board);
            return board;
        } else {
            meta.remove(MemberKey.PVP_TIME);
            meta.remove(MemberKey.KILLS);
            return Bukkit.getScoreboardManager().getNewScoreboard();
        }
    }

    public int getKills() {
        try {
            return meta.getInt(MemberKey.KILLS);
        } catch(NoMetadataSetException ex) {
            return 0;
        }
    }

    public long getPvpTime() throws NoMetadataSetException {
        return meta.getLong(MemberKey.PVP_TIME);
    }

    public void newLogin(final long time) {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(MemberKey.LAST_LOGINS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {}
        lastLogins.add(time);
        while(lastLogins.size() > 2) {
            lastLogins.remove(2);
        }
    }

    public boolean isActive() {
        List<Long> lastLogins = new LinkedList<Long>();
        try {
            lastLogins = meta.getList(MemberKey.LAST_LOGINS, new LinkedList<Long>());
        } catch(NoMetadataSetException ex) {}
        return lastLogins.size() == 2 && System.currentTimeMillis() - TimeUtils.WEEK < lastLogins.get(1);
    }

    public int getTotalExp() {
        return player.getTotalExperience();
    }

    public boolean hasScuba() {
        ItemStack helmet = player.getInventory().getHelmet();
        if(helmet != null && helmet.getType().equals(Material.GLASS)) return true;
        return false;
    }

    public int countItems(final ItemStack search) {
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

    public void setVehicle(final Entity vehicle) {
        vehicle.setPassenger(player);
    }

    public void saveInventory() {
        meta.set(MemberKey.INVENTORY, player.getInventory().getContents());
    }

    public void saveItems() {
        saveInventory();
        saveArmor();
    }

    public void loadArmor() {
        try {
            player.getInventory().setArmorContents(meta.popItemStack(MemberKey.ARMOR));
        } catch(NoMetadataSetException ex) {
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
        }
    }

    public void loadInventory() {
        try {
            player.getInventory().setContents(meta.popItemStack(MemberKey.INVENTORY));
        } catch(NoMetadataSetException ex) {
            player.getInventory().clear();
        }
    }

    public void saveArmor() {
        meta.set(MemberKey.ARMOR, player.getInventory().getArmorContents());
    }

    public void loadItems() {
        loadInventory();
        loadArmor();
    }

    public void setBackPoint() {
        setBackPoint(player.getLocation());
    }

    public boolean toggleGameMode() {
        if(player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setGameMode(GameMode.CREATIVE);
            return true;
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            return false;
        }
    }

    public void release() {
        if(!quietTeleport(WarpUtils.getWarp("jailout")) && !quietTeleport(UDSPlugin.getWorldSpawn(player.getWorld()))) {
            player.teleport(UDSPlugin.getWorldSpawn(player.getWorld()));
        }
        meta.remove(MemberKey.JAIL_TIME);
        meta.remove(MemberKey.JAIL_SENTENCE);
    }

    public Region getCurrentRegion(final RegionType type) throws NoRegionFoundException {
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
        throw new NoRegionFoundException(type, player.getLocation());
    }

    public void hideFrom(final OfflineMember member, final boolean hide) {
        hideFrom(member, hide);
    }

    public void hideFrom(final Player player, final boolean hide) {
        if(hide) {
            this.player.hidePlayer(player);
        } else {
            this.player.showPlayer(player);
        }
    }

    public void giveAndDrop(final ItemStack item) {
        final Map<Integer, ItemStack> drops = player.getInventory().addItem(item);
        for(ItemStack drop : drops.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), drop);
        }
    }

    public void chat(final ChatChannel channel, final String message) {
        final ChatChannel temp = getChannel();
        meta.set(MemberKey.CHAT_CHANNEL, channel);
        player.chat(message);
        meta.set(MemberKey.CHAT_CHANNEL, temp);
    }

    public void move(final Location location) {
        final Location destination = location;
        destination.setPitch(player.getLocation().getPitch());
        destination.setYaw(player.getLocation().getYaw());
        quietTeleport(destination);
    }

    public boolean quietTeleport(final Location location) {
        if(location == null) {
            return false;
        } else {
            player.teleport(location);
            return true;
        }
    }

    public boolean quietTeleport(final Warp warp) {
        if(warp == null) {
            return false;
        } else {
            return player.teleport(warp.getLocation());
        }
    }

    @Override
    public void sendMessage(final String message) {
        if(parent.isOnline()) player.sendMessage(message);
    }

    public void setFoodLevel(final int level) {
        player.setFoodLevel(level);
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public World getWorld() {
        return player.getWorld();
    }

    public void kickPlayer(final String message) {
        player.kickPlayer(message);
    }

    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    public boolean teleport(final Location location) {
        return player.teleport(location);
    }

    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    public boolean performCommand(final String command) {
        return player.performCommand(command);
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public void setItemInHand(final ItemStack item) {
        player.setItemInHand(item);
    }

    public Block getTargetBlock(final HashSet<Byte> transparent, final int range) {
        return player.getTargetBlock(transparent, range);
    }

    public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent, final int range) {
        return player.getLastTwoTargetBlocks(transparent, range);
    }

    public Player getKiller() {
        return player.getKiller();
    }

    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @SuppressWarnings("deprecation")
    public void updateInventory() {
        player.updateInventory();
    }

    public void teleport(final Member member) {
        player.teleport(member.getLocation());
    }

    public void teleportHere(final Entity entity) {
        entity.teleport(player);
    }

    public void giveExpLevels(final int levels) {
        player.giveExpLevels(levels);
    }

    public void setExp(final int exp) {
        player.setExp(exp);
    }

    public void setLevel(final int level) {
        player.setLevel(level);
    }

    public int getLevel() {
        return player.getLevel();
    }

    public double getMaxHealth() {
        return player.getMaxHealth();
    }

    public void setHealth(final double health) {
        player.setHealth(health);
    }

    public List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return player.getNearbyEntities(x, y, z);
    }

    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    public Entity getVehicle() {
        return player.getVehicle();
    }

    public void setGameMode(GameMode mode) {
        player.setGameMode(mode);
    }
}
