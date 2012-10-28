package com.undeadscythes.udsplugin1;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * An extension of Minecraft players adding various fields and methods.
 * @author UndeadScythes
 */
public class ExtendedPlayer implements Saveable, Player {
    /**
     * File name of player file.
     */
    public static String PATH = "players.csv";
    /**
     * Current record version.
     */
    public static int VERSION = 1;

    private Player base;
    private int bounty;
    private int money;
    private Rank rank;
    private long vipTime;
    private int vipSpawns;
    private long jailTime;
    private long jailSentence;
    private String clan;
    private Location back;
    private boolean godMode;
    private boolean lockdownPass;
    private Channel channel = Channel.PUBLIC;

    /**
     * Initialise a brand new player extension.
     * @param player Player to connect to this extension.
     */
    public ExtendedPlayer(Player player) {
        this.base = player;
        rank = Rank.DEFAULT;
    }

    /**
     * Initialise an extended player from a string record.
     * @param record A line from a save file.
     */
    public ExtendedPlayer(String record) {
        String[] recordSplit = record.split("\t");
        bounty = Integer.getInteger(recordSplit[2]);
        money = Integer.parseInt(recordSplit[3]);
        rank = Rank.valueOf(recordSplit[4]);
        vipTime = Long.parseLong(recordSplit[5]);
        vipSpawns = Integer.parseInt(recordSplit[6]);
        jailTime = Long.parseLong(recordSplit[7]);
        jailSentence = Long.parseLong(recordSplit[8]);
        clan = recordSplit[9];
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(getName());
        record.add(getDisplayName());
        record.add(bounty + "");
        record.add(money + "");
        record.add(rank.toString());
        record.add(vipTime + "");
        record.add(vipSpawns + "");
        record.add(jailTime + "");
        record.add(jailSentence + "");
        record.add(clan);
        return StringUtils.join(record.toArray(), "\t");
    }

    /**
     * Warp an existing player with these extensions.
     * @param player Player to wrap.
     */
    public void wrapPlayer(Player player) {
        this.base = player;
    }

    /**
     * Get the last recorded location of the player.
     * @return The last recorded location of the player.
     */
    public Location getBack() {
        return back;
    }

    /**
     * Get a player current rank.
     * @return Player rank.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Set the player rank.
     * @param rank The rank to set.
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * Check to see if a player belongs to a clan.
     * @return <code>true</code> if a player is in a clan, <code>false</code> otherwise.
     */
    public boolean isInClan() {
        return !(clan.equals("") || clan == null);
    }

    /**
     * Get the name of the clan the player is a member of.
     * @return Clan name.
     */
    public String getClan() {
        return clan;
    }

    /**
     * Toggle the players chat channel.
     * @param channel Channel to toggle.
     * @return <code>true</code> if channel was toggled on, <code>false</code> if channel switched back to public.
     */
    public boolean toggleChannel(Channel channel) {
        if(this.channel.equals(channel)) {
            this.channel = Channel.PUBLIC;
            return false;
        } else {
            this.channel = channel;
            return true;
        }
    }

    /**
     * Check whether this player has a lockdown pass.
     * @return Has player got lockdown pass.
     */
    public boolean hasLockdownPass() {
        return lockdownPass;
    }

    /**
     * Get the time when this player rented VIP status.
     * @return When VIP was rented.
     */
    public long getVIPTime() {
        return vipTime;
    }

    /**
     * Set when a player rented VIP status.
     * @param time Time to set.
     */
    public void setVIPTime(long time) {
        vipTime = time;
    }

    /**
     * Get the number of free item spawns this player has left.
     * @return Number of spawns left.
     */
    public int getVIPSpawns() {
        return vipSpawns;
    }

    /**
     * Set the number of free VIP item spawns a player has remaining.
     * @param spawns Number of spawns to set.
     */
    public void setVIPSpawns(int spawns) {
        vipSpawns = spawns;
    }

    /**
     * Get the time that this player was put in jail.
     * @return Players jail time.
     */
    public long getJailTime() {
        return jailTime;
    }

    /**
     * Set when a player was put in jail.
     * @param time Jail time.
     */
    public void setJailTime(long time) {
        jailTime = time;
    }

    /**
     * Get how long this player was sentenced to jail for.
     * @return Players sentence.
     */
    public long getJailSentence() {
        return jailSentence;
    }

    /**
     * Set the length of a players jail sentence.
     * @param sentence Length of sentence.
     */
    public void setJailSentence(long sentence) {
        jailSentence = sentence;
    }

    /**
     * Check if a player is currently in jail.
     * @return <code>true</code> if a player is in jail, <code>false</code> otherwise.
     */
    public boolean isJailed() {
        return (jailTime > 0);
    }

    /**
     * Check if a player currently has god mode enabled.
     * @return God mode setting.
     */
    public boolean hasGodMode() {
        return godMode;
    }

    /**
     * Toggle a players god mode.
     * @return Players current god mode setting.
     */
    public boolean toggleGodMode() {
        godMode ^= true;
        return godMode;
    }

    /**
     * Set a players god mode.
     * @param mode God mode setting.
     */
    public void setGodMode(boolean mode) {
        godMode = mode;
    }

    /**
     * Send a message in a particular channel.
     * @param channel Channel to send message in.
     * @param message Message to send.
     */
    public void chat(Channel channel, String message) {
        Channel temp = this.channel;
        this.channel = channel;
        chat(message);
        this.channel = temp;
    }

    /**
     * Check if a player can afford to pay some value.
     * @param price Price to pay.
     * @return <code>true</code> if player has enough money, <code>false</code> otherwise.
     */
    public boolean canAfford(int price) {
        return (price >= money);
    }

    /**
     * Debit a players account the amount passed.
     * @param price Amount to debit.
     */
    public void debit(int price) {
        money -= price;
    }

    /**
     * Check if a player has a rank.
     * @param rank Rank to check.
     * @return <code>true</code> if player has rank, <code>false</code> otherwise.
     */
    public boolean hasRank(Rank rank) {
        if(this.rank.compareTo(rank) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Teleport a player but reserve pitch and yaw of player.
     * @param location Location to teleport to.
     */
    public void move(Location location) {
        Location destination = location;
        destination.setPitch(getLocation().getPitch());
        destination.setYaw(getLocation().getYaw());
        teleport(destination);
    }

    /**
     * Teleport a player but fail quietly if location is <code>null</code>.
     * @param location Location to teleport player to.
     * @return <code>true</code> if location is not <code>null</code>, <code>false</code> otherwise.
     */
    public boolean quietTeleport(Location location) {
        if(location == null) {
            return false;
        } else {
            teleport(location);
            return true;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setDisplayName(String name) {
        base.setDisplayName(name);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getPlayerListName() {
        return getPlayerListName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPlayerListName(String name) {
        base.setPlayerListName(name);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCompassTarget(Location loc) {
        base.setCompassTarget(loc);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Location getCompassTarget() {
        return base.getCompassTarget();
    }

    /**
     * @inheritDoc
     */
    @Override
    public InetSocketAddress getAddress() {
        return base.getAddress();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendRawMessage(String message) {
        base.sendRawMessage(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kickPlayer(String message) {
        base.kickPlayer(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void chat(String msg) {
        base.chat(msg);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean performCommand(String command) {
        return base.performCommand(command);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSneaking() {
        return base.isSneaking();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSneaking(boolean sneak) {
        base.setSneaking(sneak);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSprinting() {
        return base.isSprinting();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSprinting(boolean sprinting) {
        base.setSprinting(sprinting);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void saveData() {
        base.saveData();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void loadData() {
        base.loadData();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSleepingIgnored(boolean isSleeping) {
        base.setSleepingIgnored(isSleeping);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSleepingIgnored() {
        return base.isSleepingIgnored();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playNote(Location loc, byte instrument, byte note) {
        base.playNote(loc, instrument, note);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playNote(Location loc, Instrument instrument, Note note) {
        base.playNote(loc, instrument, note);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        base.playSound(location, sound, volume, pitch);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playEffect(Location loc, Effect effect, int data) {
        base.playEffect(loc, effect, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T> void playEffect(Location loc, Effect effect, T data) {
        playEffect(loc, effect, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendBlockChange(Location loc, Material material, byte data) {
        base.sendBlockChange(loc, material, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        return base.sendChunkChange(loc, sx, sy, sz, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendBlockChange(Location loc, int material, byte data) {
        base.sendBlockChange(loc, material, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendMap(MapView map) {
        base.sendMap(map);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateInventory() {
        base.updateInventory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void awardAchievement(Achievement achievement) {
        base.awardAchievement(achievement);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void incrementStatistic(Statistic statistic) {
        base.incrementStatistic(statistic);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void incrementStatistic(Statistic statistic, int amount) {
        base.incrementStatistic(statistic, amount);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void incrementStatistic(Statistic statistic, Material material) {
        base.incrementStatistic(statistic, material);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        base.incrementStatistic(statistic, material, amount);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPlayerTime(long time, boolean relative) {
        base.setPlayerTime(time, relative);
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getPlayerTime() {
        return base.getPlayerTime();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getPlayerTimeOffset() {
        return base.getPlayerTimeOffset();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isPlayerTimeRelative() {
        return base.isPlayerTimeRelative();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void resetPlayerTime() {
        base.resetPlayerTime();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void giveExp(int amount) {
        base.giveExp(amount);
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getExp() {
        return base.getExp();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setExp(float exp) {
        base.setExp(exp);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getLevel() {
        return base.getLevel();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setLevel(int level) {
        base.setLevel(level);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getTotalExperience() {
        return base.getTotalExperience();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTotalExperience(int exp) {
        base.setTotalExperience(exp);
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getExhaustion() {
        return base.getExhaustion();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setExhaustion(float value) {
        base.setExhaustion(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getSaturation() {
        return base.getSaturation();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSaturation(float value) {
        base.setSaturation(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getFoodLevel() {
        return base.getFoodLevel();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFoodLevel(int value) {
        base.setFoodLevel(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Location getBedSpawnLocation() {
        return base. getBedSpawnLocation();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBedSpawnLocation(Location location) {
        base.setBedSpawnLocation(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getAllowFlight() {
        return base.getAllowFlight();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setAllowFlight(boolean flight) {
        base.setAllowFlight(flight);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hidePlayer(Player player) {
        base.hidePlayer(player);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showPlayer(Player player) {
        base.showPlayer(player);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean canSee(Player player) {
        return base.canSee(player);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isFlying() {
        return base.isFlying();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFlying(boolean value) {
        base.setFlying(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFlySpeed(float value) throws IllegalArgumentException {
        base.setFlySpeed(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setWalkSpeed(float value) throws IllegalArgumentException {
        base.setWalkSpeed(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getFlySpeed() {
        return base.getFlySpeed();
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getWalkSpeed() {
        return base.getWalkSpeed();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return base. getName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public PlayerInventory getInventory() {
        return base.getInventory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Inventory getEnderChest() {
        return base. getEnderChest();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean setWindowProperty(Property prop, int value) {
        return base.setWindowProperty(prop, value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public InventoryView getOpenInventory() {
        return base.getOpenInventory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public InventoryView openInventory(Inventory inventory) {
        return base.openInventory(inventory);
    }

    /**
     * @inheritDoc
     */
    @Override
    public InventoryView openWorkbench(Location location, boolean force) {
        return base.openWorkbench(location, force);
    }

    /**
     * @inheritDoc
     */
    @Override
    public InventoryView openEnchanting(Location location, boolean force) {
        return base.openEnchanting(location, force);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void openInventory(InventoryView inventory) {
        base.openInventory(inventory);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void closeInventory() {
        base.closeInventory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public ItemStack getItemInHand() {
        return base.getItemInHand();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setItemInHand(ItemStack item) {
        base.setItemInHand(item);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ItemStack getItemOnCursor() {
        return base.getItemOnCursor();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setItemOnCursor(ItemStack item) {
        base.setItemOnCursor(item);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSleeping() {
        return base.isSleeping();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getSleepTicks() {
        return base.getSleepTicks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public GameMode getGameMode() {
        return base.getGameMode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setGameMode(GameMode mode) {
        base.setGameMode(mode);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isBlocking() {
        return base.isBlocking();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getExpToLevel() {
        return base.getExpToLevel();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getHealth() {
        return base.getHealth();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setHealth(int health) {
        base.setHealth(health);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMaxHealth() {
        return base.getMaxHealth();
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getEyeHeight() {
        return base.getEyeHeight();
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getEyeHeight(boolean ignoreSneaking) {
        return base.getEyeHeight(ignoreSneaking);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Location getEyeLocation() {
        return base. getEyeLocation();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return base.getLineOfSight(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        return base. getTargetBlock(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return base.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Egg throwEgg() {
        return base. throwEgg();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Snowball throwSnowball() {
        return base. throwSnowball();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Arrow shootArrow() {
        return base. shootArrow();
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return base.launchProjectile(projectile);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getRemainingAir() {
        return base.getRemainingAir();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setRemainingAir(int ticks) {
        base.setRemainingAir(ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMaximumAir() {
        return base.getMaximumAir();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMaximumAir(int ticks) {
        base.setMaximumAir(ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void damage(int amount) {
        base.damage(amount);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void damage(int amount, Entity source) {
        base.damage(amount, source);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMaximumNoDamageTicks() {
        return base.getMaximumNoDamageTicks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        base.setMaximumNoDamageTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getLastDamage() {
        return base.getLastDamage();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setLastDamage(int damage) {
        base.setLastDamage(damage);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getNoDamageTicks() {
        return base.getNoDamageTicks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setNoDamageTicks(int ticks) {
        base.setNoDamageTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Player getKiller() {
        return base. getKiller();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addPotionEffect(PotionEffect effect) {
        return base.addPotionEffect(effect);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        return base.addPotionEffect(effect, force);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        return base.addPotionEffects(effects);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPotionEffect(PotionEffectType type) {
        return base.hasPotionEffect(type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removePotionEffect(PotionEffectType type) {
        base.removePotionEffect(type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return base.<PotionEffect> getActivePotionEffects();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasLineOfSight(Entity other) {
        return base.hasLineOfSight(other);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Location getLocation() {
        return base. getLocation();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setVelocity(Vector velocity) {
        base.setVelocity(velocity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Vector getVelocity() {
        return base. getVelocity();
    }

    /**
     * @inheritDoc
     */
    @Override
    public World getWorld() {
        return base. getWorld();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean teleport(Location location) {
        return base.teleport(location);
    }

    /**
     * @inheritDoc
     * @Override
     */
    @Override
    public boolean teleport(Location location, TeleportCause cause) {
        return base.teleport(location, cause);
    }

    /**
     *
     * @inheritDoc
     */
    @Override
    public boolean teleport(Entity destination) {
        return base.teleport(destination);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean teleport(Entity destination, TeleportCause cause) {
        return base.teleport(destination, cause);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return base.getNearbyEntities(x, y, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getEntityId() {
        return base.getEntityId();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getFireTicks() {
        return base.getFireTicks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMaxFireTicks() {
        return base.getMaxFireTicks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFireTicks(int ticks) {
        base.setFireTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void remove() {
        base.remove();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isDead() {
        return base.isDead();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isValid() {
        return base.isValid();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Server getServer() {
        return base. getServer();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Entity getPassenger() {
        return base. getPassenger();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean setPassenger(Entity passenger) {
        return base.setPassenger(passenger);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean eject() {
        return base.eject();
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getFallDistance() {
        return base.getFallDistance();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFallDistance(float distance) {
        base.setFallDistance(distance);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setLastDamageCause(EntityDamageEvent event) {
        base.setLastDamageCause(event);
    }

    /**
     * @inheritDoc
     */
    @Override
    public EntityDamageEvent getLastDamageCause() {
        return base.getLastDamageCause();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUniqueId() {
        return base.getUniqueId();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getTicksLived() {
        return base.getTicksLived();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTicksLived(int value) {
        base.setTicksLived(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playEffect(EntityEffect type) {
        base.playEffect(type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public EntityType getType() {
        return base.getType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isInsideVehicle() {
        return base.isInsideVehicle();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean leaveVehicle() {
        return base.leaveVehicle();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Entity getVehicle() {
        return base. getVehicle();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        base.setMetadata(metadataKey, newMetadataValue);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return base.getMetadata(metadataKey);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasMetadata(String metadataKey) {
        return base.hasMetadata(metadataKey);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        base.removeMetadata(metadataKey, owningPlugin);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isPermissionSet(String name) {
        return base.isPermissionSet(name);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isPermissionSet(Permission perm) {
        return base.isPermissionSet(perm);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPermission(String name) {
        return base.hasPermission(name);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPermission(Permission perm) {
        return base.hasPermission(perm);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return base.addAttachment(plugin, name, value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return base.addAttachment(plugin);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return base.addAttachment(plugin, name, value, ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return base.addAttachment(plugin, ticks);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        base.removeAttachment(attachment);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void recalculatePermissions() {
        base.recalculatePermissions();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return base.<PermissionAttachmentInfo> getEffectivePermissions();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isOp() {
        return base.isOp();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setOp(boolean value) {
        base.setOp(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConversing() {
        return base.isConversing();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void acceptConversationInput(String input) {
        base.acceptConversationInput(input);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean beginConversation(Conversation conversation) {
        return base.beginConversation(conversation);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void abandonConversation(Conversation conversation) {
        base.abandonConversation(conversation);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        base.abandonConversation(conversation, details);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendMessage(String message) {
        base.sendMessage(message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendMessage(String[] messages) {
        base.sendMessage(messages);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isOnline() {
        return base.isOnline();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isBanned() {
        return base.isBanned();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBanned(boolean banned) {
        base.setBanned(banned);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isWhitelisted() {
        return base.isWhitelisted();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setWhitelisted(boolean value) {
        base.setWhitelisted(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Player getPlayer() {
        return base.getPlayer();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getFirstPlayed() {
        return base.getFirstPlayed();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getLastPlayed() {
        return base.getLastPlayed();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPlayedBefore() {
        return base.hasPlayedBefore();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Map<String, Object> serialize() {
        return base.serialize();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        base.sendPluginMessage(source, channel, message);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<String> getListeningPluginChannels() {
        return base.getListeningPluginChannels();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName() {
        return base.getDisplayName();
    }
}
