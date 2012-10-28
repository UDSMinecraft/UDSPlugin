package com.undeadscythes.udsplugin1;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
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
    private ExtendedLocation back;

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
        back = new ExtendedLocation(recordSplit[5]);
    }

    /**
     * @inheritDoc
     */
    public String getRecord() {
        ArrayList<String> record = new ArrayList<String>();
        record.add(getName());
        record.add(getDisplayName());
        record.add(bounty + "");
        record.add(money + "");
        record.add(rank.toString());
        record.add(back.toString());
        return StringUtils.join(record.toArray(), "\t");
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

    public void sendMessage(Message message) {
        base.sendMessage(message.toString());
    }

    /**
     * @inheritDoc
     */
    public void setDisplayName(String name) {
        base.setDisplayName(name);
    }

    /**
     * @inheritDoc
     */
    public String getPlayerListName() {
        return getPlayerListName();
    }

    /**
     * @inheritDoc
     */
    public void setPlayerListName(String name) {
        base.setPlayerListName(name);
    }

    /**
     * @inheritDoc
     */
    public void setCompassTarget(Location loc) {
        base.setCompassTarget(loc);
    }

    /**
     * @inheritDoc
     */
    public Location getCompassTarget() {
        return base.getCompassTarget();
    }

    /**
     * @inheritDoc
     */
    public InetSocketAddress getAddress() {
        return base.getAddress();
    }

    /**
     * @inheritDoc
     */
    public void sendRawMessage(String message) {
        base.sendRawMessage(message);
    }

    /**
     * @inheritDoc
     */
    public void kickPlayer(String message) {
        base.kickPlayer(message);
    }

    /**
     * @inheritDoc
     */
    public void chat(String msg) {
        base.chat(msg);
    }

    /**
     * @inheritDoc
     */
    public boolean performCommand(String command) {
        return base.performCommand(command);
    }

    /**
     * @inheritDoc
     */
    public boolean isSneaking() {
        return base.isSneaking();
    }

    /**
     * @inheritDoc
     */
    public void setSneaking(boolean sneak) {
        base.setSneaking(sneak);
    }

    /**
     * @inheritDoc
     */
    public boolean isSprinting() {
        return base.isSprinting();
    }

    /**
     * @inheritDoc
     */
    public void setSprinting(boolean sprinting) {
        base.setSprinting(sprinting);
    }

    /**
     * @inheritDoc
     */
    public void saveData() {
        base.saveData();
    }

    /**
     * @inheritDoc
     */
    public void loadData() {
        base.loadData();
    }

    /**
     * @inheritDoc
     */
    public void setSleepingIgnored(boolean isSleeping) {
        base.setSleepingIgnored(isSleeping);
    }

    /**
     * @inheritDoc
     */
    public boolean isSleepingIgnored() {
        return base.isSleepingIgnored();
    }

    /**
     * @inheritDoc
     */
    public void playNote(Location loc, byte instrument, byte note) {
        base.playNote(loc, instrument, note);
    }

    /**
     * @inheritDoc
     */
    public void playNote(Location loc, Instrument instrument, Note note) {
        base.playNote(loc, instrument, note);
    }

    /**
     * @inheritDoc
     */
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        base.playSound(location, sound, volume, pitch);
    }

    /**
     * @inheritDoc
     */
    public void playEffect(Location loc, Effect effect, int data) {
        base.playEffect(loc, effect, data);
    }

    /**
     * @inheritDoc
     */
    public <T> void playEffect(Location loc, Effect effect, T data) {
        playEffect(loc, effect, data);
    }

    /**
     * @inheritDoc
     */
    public void sendBlockChange(Location loc, Material material, byte data) {
        base.sendBlockChange(loc, material, data);
    }

    /**
     * @inheritDoc
     */
    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        return base.sendChunkChange(loc, sx, sy, sz, data);
    }

    /**
     * @inheritDoc
     */
    public void sendBlockChange(Location loc, int material, byte data) {
        base.sendBlockChange(loc, material, data);
    }

    /**
     * @inheritDoc
     */
    public void sendMap(MapView map) {
        base.sendMap(map);
    }

    /**
     * @inheritDoc
     */
    public void updateInventory() {
        base.updateInventory();
    }

    /**
     * @inheritDoc
     */
    public void awardAchievement(Achievement achievement) {
        base.awardAchievement(achievement);
    }

    /**
     * @inheritDoc
     */
    public void incrementStatistic(Statistic statistic) {
        base.incrementStatistic(statistic);
    }

    /**
     * @inheritDoc
     */
    public void incrementStatistic(Statistic statistic, int amount) {
        base.incrementStatistic(statistic, amount);
    }

    /**
     * @inheritDoc
     */
    public void incrementStatistic(Statistic statistic, Material material) {
        base.incrementStatistic(statistic, material);
    }

    /**
     * @inheritDoc
     */
    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        base.incrementStatistic(statistic, material, amount);
    }

    /**
     * @inheritDoc
     */
    public void setPlayerTime(long time, boolean relative) {
        base.setPlayerTime(time, relative);
    }

    /**
     * @inheritDoc
     */
    public long getPlayerTime() {
        return base.getPlayerTime();
    }

    /**
     * @inheritDoc
     */
    public long getPlayerTimeOffset() {
        return base.getPlayerTimeOffset();
    }

    /**
     * @inheritDoc
     */
    public boolean isPlayerTimeRelative() {
        return base.isPlayerTimeRelative();
    }

    /**
     * @inheritDoc
     */
    public void resetPlayerTime() {
        base.resetPlayerTime();
    }

    /**
     * @inheritDoc
     */
    public void giveExp(int amount) {
        base.giveExp(amount);
    }

    /**
     * @inheritDoc
     */
    public float getExp() {
        return base.getExp();
    }

    /**
     * @inheritDoc
     */
    public void setExp(float exp) {
        base.setExp(exp);
    }

    /**
     * @inheritDoc
     */
    public int getLevel() {
        return base.getLevel();
    }

    /**
     * @inheritDoc
     */
    public void setLevel(int level) {
        base.setLevel(level);
    }

    /**
     * @inheritDoc
     */
    public int getTotalExperience() {
        return base.getTotalExperience();
    }

    /**
     * @inheritDoc
     */
    public void setTotalExperience(int exp) {
        base.setTotalExperience(exp);
    }

    /**
     * @inheritDoc
     */
    public float getExhaustion() {
        return base.getExhaustion();
    }

    /**
     * @inheritDoc
     */
    public void setExhaustion(float value) {
        base.setExhaustion(value);
    }

    /**
     * @inheritDoc
     */
    public float getSaturation() {
        return base.getSaturation();
    }

    /**
     * @inheritDoc
     */
    public void setSaturation(float value) {
        base.setSaturation(value);
    }

    /**
     * @inheritDoc
     */
    public int getFoodLevel() {
        return base.getFoodLevel();
    }

    /**
     * @inheritDoc
     */
    public void setFoodLevel(int value) {
        base.setFoodLevel(value);
    }

    /**
     * @inheritDoc
     */
    public Location getBedSpawnLocation() {
        return base. getBedSpawnLocation();
    }

    /**
     * @inheritDoc
     */
    public void setBedSpawnLocation(Location location) {
        base.setBedSpawnLocation(location);
    }

    /**
     * @inheritDoc
     */
    public boolean getAllowFlight() {
        return base.getAllowFlight();
    }

    /**
     * @inheritDoc
     */
    public void setAllowFlight(boolean flight) {
        base.setAllowFlight(flight);
    }

    /**
     * @inheritDoc
     */
    public void hidePlayer(Player player) {
        base.hidePlayer(player);
    }

    /**
     * @inheritDoc
     */
    public void showPlayer(Player player) {
        base.showPlayer(player);
    }

    /**
     * @inheritDoc
     */
    public boolean canSee(Player player) {
        return base.canSee(player);
    }

    /**
     * @inheritDoc
     */
    public boolean isFlying() {
        return base.isFlying();
    }

    /**
     * @inheritDoc
     */
    public void setFlying(boolean value) {
        base.setFlying(value);
    }

    /**
     * @inheritDoc
     */
    public void setFlySpeed(float value) throws IllegalArgumentException {
        base.setFlySpeed(value);
    }

    /**
     * @inheritDoc
     */
    public void setWalkSpeed(float value) throws IllegalArgumentException {
        base.setWalkSpeed(value);
    }

    /**
     * @inheritDoc
     */
    public float getFlySpeed() {
        return base.getFlySpeed();
    }

    /**
     * @inheritDoc
     */
    public float getWalkSpeed() {
        return base.getWalkSpeed();
    }

    /**
     * @inheritDoc
     */
    public String getName() {
        return base. getName();
    }

    /**
     * @inheritDoc
     */
    public PlayerInventory getInventory() {
        return base.getInventory();
    }

    /**
     * @inheritDoc
     */
    public Inventory getEnderChest() {
        return base. getEnderChest();
    }

    /**
     * @inheritDoc
     */
    public boolean setWindowProperty(Property prop, int value) {
        return base.setWindowProperty(prop, value);
    }

    /**
     * @inheritDoc
     */
    public InventoryView getOpenInventory() {
        return base.getOpenInventory();
    }

    /**
     * @inheritDoc
     */
    public InventoryView openInventory(Inventory inventory) {
        return base.openInventory(inventory);
    }

    /**
     * @inheritDoc
     */
    public InventoryView openWorkbench(Location location, boolean force) {
        return base.openWorkbench(location, force);
    }

    /**
     * @inheritDoc
     */
    public InventoryView openEnchanting(Location location, boolean force) {
        return base.openEnchanting(location, force);
    }

    /**
     * @inheritDoc
     */
    public void openInventory(InventoryView inventory) {
        base.openInventory(inventory);
    }

    /**
     * @inheritDoc
     */
    public void closeInventory() {
        base.closeInventory();
    }

    /**
     * @inheritDoc
     */
    public ItemStack getItemInHand() {
        return base.getItemInHand();
    }

    /**
     * @inheritDoc
     */
    public void setItemInHand(ItemStack item) {
        base.setItemInHand(item);
    }

    /**
     * @inheritDoc
     */
    public ItemStack getItemOnCursor() {
        return base.getItemOnCursor();
    }

    /**
     * @inheritDoc
     */
    public void setItemOnCursor(ItemStack item) {
        base.setItemOnCursor(item);
    }

    /**
     * @inheritDoc
     */
    public boolean isSleeping() {
        return base.isSleeping();
    }

    /**
     * @inheritDoc
     */
    public int getSleepTicks() {
        return base.getSleepTicks();
    }

    /**
     * @inheritDoc
     */
    public GameMode getGameMode() {
        return base.getGameMode();
    }

    /**
     * @inheritDoc
     */
    public void setGameMode(GameMode mode) {
        base.setGameMode(mode);
    }

    /**
     * @inheritDoc
     */
    public boolean isBlocking() {
        return base.isBlocking();
    }

    /**
     * @inheritDoc
     */
    public int getExpToLevel() {
        return base.getExpToLevel();
    }

    /**
     * @inheritDoc
     */
    public int getHealth() {
        return base.getHealth();
    }

    /**
     * @inheritDoc
     */
    public void setHealth(int health) {
        base.setHealth(health);
    }

    /**
     * @inheritDoc
     */
    public int getMaxHealth() {
        return base.getMaxHealth();
    }

    /**
     * @inheritDoc
     */
    public double getEyeHeight() {
        return base.getEyeHeight();
    }

    /**
     * @inheritDoc
     */
    public double getEyeHeight(boolean ignoreSneaking) {
        return base.getEyeHeight(ignoreSneaking);
    }

    /**
     * @inheritDoc
     */
    public Location getEyeLocation() {
        return base. getEyeLocation();
    }

    /**
     * @inheritDoc
     */
    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return base.getLineOfSight(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        return base. getTargetBlock(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return base.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    /**
     * @inheritDoc
     */
    public Egg throwEgg() {
        return base. throwEgg();
    }

    /**
     * @inheritDoc
     */
    public Snowball throwSnowball() {
        return base. throwSnowball();
    }

    /**
     * @inheritDoc
     */
    public Arrow shootArrow() {
        return base. shootArrow();
    }

    /**
     * @inheritDoc
     */
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return base.launchProjectile(projectile);
    }

    /**
     * @inheritDoc
     */
    public int getRemainingAir() {
        return base.getRemainingAir();
    }

    /**
     * @inheritDoc
     */
    public void setRemainingAir(int ticks) {
        base.setRemainingAir(ticks);
    }

    /**
     * @inheritDoc
     */
    public int getMaximumAir() {
        return base.getMaximumAir();
    }

    /**
     * @inheritDoc
     */
    public void setMaximumAir(int ticks) {
        base.setMaximumAir(ticks);
    }

    /**
     * @inheritDoc
     */
    public void damage(int amount) {
        base.damage(amount);
    }

    /**
     * @inheritDoc
     */
    public void damage(int amount, Entity source) {
        base.damage(amount, source);
    }

    /**
     * @inheritDoc
     */
    public int getMaximumNoDamageTicks() {
        return base.getMaximumNoDamageTicks();
    }

    /**
     * @inheritDoc
     */
    public void setMaximumNoDamageTicks(int ticks) {
        base.setMaximumNoDamageTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    public int getLastDamage() {
        return base.getLastDamage();
    }

    /**
     * @inheritDoc
     */
    public void setLastDamage(int damage) {
        base.setLastDamage(damage);
    }

    /**
     * @inheritDoc
     */
    public int getNoDamageTicks() {
        return base.getNoDamageTicks();
    }

    /**
     * @inheritDoc
     */
    public void setNoDamageTicks(int ticks) {
        base.setNoDamageTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    public Player getKiller() {
        return base. getKiller();
    }

    /**
     * @inheritDoc
     */
    public boolean addPotionEffect(PotionEffect effect) {
        return base.addPotionEffect(effect);
    }

    /**
     * @inheritDoc
     */
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        return base.addPotionEffect(effect, force);
    }

    /**
     * @inheritDoc
     */
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        return base.addPotionEffects(effects);
    }

    /**
     * @inheritDoc
     */
    public boolean hasPotionEffect(PotionEffectType type) {
        return base.hasPotionEffect(type);
    }

    /**
     * @inheritDoc
     */
    public void removePotionEffect(PotionEffectType type) {
        base.removePotionEffect(type);
    }

    /**
     * @inheritDoc
     */
    public Collection<PotionEffect> getActivePotionEffects() {
        return base.<PotionEffect> getActivePotionEffects();
    }

    /**
     * @inheritDoc
     */
    public boolean hasLineOfSight(Entity other) {
        return base.hasLineOfSight(other);
    }

    /**
     * @inheritDoc
     */
    public Location getLocation() {
        return base. getLocation();
    }

    /**
     * @inheritDoc
     */
    public void setVelocity(Vector velocity) {
        base.setVelocity(velocity);
    }

    /**
     * @inheritDoc
     */
    public Vector getVelocity() {
        return base. getVelocity();
    }

    /**
     * @inheritDoc
     */
    public World getWorld() {
        return base. getWorld();
    }

    /**
     * @inheritDoc
     */
    public boolean teleport(Location location) {
        return base.teleport(location);
    }

    /**
     * @inheritDoc
     */
    public boolean teleport(Location location, TeleportCause cause) {
        return base.teleport(location, cause);
    }

    /**
     * @inheritDoc
     */
    public boolean teleport(Entity destination) {
        return base.teleport(destination);
    }

    /**
     * @inheritDoc
     */
    public boolean teleport(Entity destination, TeleportCause cause) {
        return base.teleport(destination, cause);
    }

    /**
     * @inheritDoc
     */
    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return base.getNearbyEntities(x, y, z);
    }

    /**
     * @inheritDoc
     */
    public int getEntityId() {
        return base.getEntityId();
    }

    /**
     * @inheritDoc
     */
    public int getFireTicks() {
        return base.getFireTicks();
    }

    /**
     * @inheritDoc
     */
    public int getMaxFireTicks() {
        return base.getMaxFireTicks();
    }

    /**
     * @inheritDoc
     */
    public void setFireTicks(int ticks) {
        base.setFireTicks(ticks);
    }

    /**
     * @inheritDoc
     */
    public void remove() {
        base.remove();
    }

    /**
     * @inheritDoc
     */
    public boolean isDead() {
        return base.isDead();
    }

    /**
     * @inheritDoc
     */
    public boolean isValid() {
        return base.isValid();
    }

    /**
     * @inheritDoc
     */
    public Server getServer() {
        return base. getServer();
    }

    /**
     * @inheritDoc
     */
    public Entity getPassenger() {
        return base. getPassenger();
    }

    /**
     * @inheritDoc
     */
    public boolean setPassenger(Entity passenger) {
        return base.setPassenger(passenger);
    }

    /**
     * @inheritDoc
     */
    public boolean isEmpty() {
        return base.isEmpty();
    }

    /**
     * @inheritDoc
     */
    public boolean eject() {
        return base.eject();
    }

    /**
     * @inheritDoc
     */
    public float getFallDistance() {
        return base.getFallDistance();
    }

    /**
     * @inheritDoc
     */
    public void setFallDistance(float distance) {
        base.setFallDistance(distance);
    }

    /**
     * @inheritDoc
     */
    public void setLastDamageCause(EntityDamageEvent event) {
        base.setLastDamageCause(event);
    }

    /**
     * @inheritDoc
     */
    public EntityDamageEvent getLastDamageCause() {
        return base.getLastDamageCause();
    }

    /**
     * @inheritDoc
     */
    public UUID getUniqueId() {
        return base.getUniqueId();
    }

    /**
     * @inheritDoc
     */
    public int getTicksLived() {
        return base.getTicksLived();
    }

    /**
     * @inheritDoc
     */
    public void setTicksLived(int value) {
        base.setTicksLived(value);
    }

    /**
     * @inheritDoc
     */
    public void playEffect(EntityEffect type) {
        base.playEffect(type);
    }

    /**
     * @inheritDoc
     */
    public EntityType getType() {
        return base.getType();
    }

    /**
     * @inheritDoc
     */
    public boolean isInsideVehicle() {
        return base.isInsideVehicle();
    }

    /**
     * @inheritDoc
     */
    public boolean leaveVehicle() {
        return base.leaveVehicle();
    }

    /**
     * @inheritDoc
     */
    public Entity getVehicle() {
        return base. getVehicle();
    }

    /**
     * @inheritDoc
     */
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        base.setMetadata(metadataKey, newMetadataValue);
    }

    /**
     * @inheritDoc
     */
    public List<MetadataValue> getMetadata(String metadataKey) {
        return base.getMetadata(metadataKey);
    }

    /**
     * @inheritDoc
     */
    public boolean hasMetadata(String metadataKey) {
        return base.hasMetadata(metadataKey);
    }

    /**
     * @inheritDoc
     */
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        base.removeMetadata(metadataKey, owningPlugin);
    }

    /**
     * @inheritDoc
     */
    public boolean isPermissionSet(String name) {
        return base.isPermissionSet(name);
    }

    /**
     * @inheritDoc
     */
    public boolean isPermissionSet(Permission perm) {
        return base.isPermissionSet(perm);
    }

    /**
     * @inheritDoc
     */
    public boolean hasPermission(String name) {
        return base.hasPermission(name);
    }

    /**
     * @inheritDoc
     */
    public boolean hasPermission(Permission perm) {
        return base.hasPermission(perm);
    }

    /**
     * @inheritDoc
     */
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return base.addAttachment(plugin, name, value);
    }

    /**
     * @inheritDoc
     */
    public PermissionAttachment addAttachment(Plugin plugin) {
        return base.addAttachment(plugin);
    }

    /**
     * @inheritDoc
     */
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return base.addAttachment(plugin, name, value, ticks);
    }

    /**
     * @inheritDoc
     */
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return base.addAttachment(plugin, ticks);
    }

    /**
     * @inheritDoc
     */
    public void removeAttachment(PermissionAttachment attachment) {
        base.removeAttachment(attachment);
    }

    /**
     * @inheritDoc
     */
    public void recalculatePermissions() {
        base.recalculatePermissions();
    }

    /**
     * @inheritDoc
     */
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return base.<PermissionAttachmentInfo> getEffectivePermissions();
    }

    /**
     * @inheritDoc
     */
    public boolean isOp() {
        return base.isOp();
    }

    /**
     * @inheritDoc
     */
    public void setOp(boolean value) {
        base.setOp(value);
    }

    /**
     * @inheritDoc
     */
    public boolean isConversing() {
        return base.isConversing();
    }

    /**
     * @inheritDoc
     */
    public void acceptConversationInput(String input) {
        base.acceptConversationInput(input);
    }

    /**
     * @inheritDoc
     */
    public boolean beginConversation(Conversation conversation) {
        return base.beginConversation(conversation);
    }

    /**
     * @inheritDoc
     */
    public void abandonConversation(Conversation conversation) {
        base.abandonConversation(conversation);
    }

    /**
     * @inheritDoc
     */
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        base.abandonConversation(conversation, details);
    }

    /**
     * @inheritDoc
     */
    public void sendMessage(String message) {
        base.sendMessage(message);
    }

    /**
     * @inheritDoc
     */
    public void sendMessage(String[] messages) {
        base.sendMessage(messages);
    }

    /**
     * @inheritDoc
     */
    public boolean isOnline() {
        return base.isOnline();
    }

    /**
     * @inheritDoc
     */
    public boolean isBanned() {
        return base.isBanned();
    }

    /**
     * @inheritDoc
     */
    public void setBanned(boolean banned) {
        base.setBanned(banned);
    }

    /**
     * @inheritDoc
     */
    public boolean isWhitelisted() {
        return base.isWhitelisted();
    }

    /**
     * @inheritDoc
     */
    public void setWhitelisted(boolean value) {
        base.setWhitelisted(value);
    }

    /**
     * @inheritDoc
     */
    public Player getPlayer() {
        return base.getPlayer();
    }

    /**
     * @inheritDoc
     */
    public long getFirstPlayed() {
        return base.getFirstPlayed();
    }

    /**
     * @inheritDoc
     */
    public long getLastPlayed() {
        return base.getLastPlayed();
    }

    /**
     * @inheritDoc
     */
    public boolean hasPlayedBefore() {
        return base.hasPlayedBefore();
    }

    /**
     * @inheritDoc
     */
    public Map<String, Object> serialize() {
        return base.serialize();
    }

    /**
     * @inheritDoc
     */
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        base.sendPluginMessage(source, channel, message);
    }

    /**
     * @inheritDoc
     */
    public Set<String> getListeningPluginChannels() {
        return base.getListeningPluginChannels();
    }

    /**
     * @inheritDoc
     */
    public String getDisplayName() {
        return base.getDisplayName();
    }
}
