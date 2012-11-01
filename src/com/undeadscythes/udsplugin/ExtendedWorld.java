package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.generator.*;
import org.bukkit.inventory.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.bukkit.util.Vector;

/**
 * An extension of the base world class adding various WE-like methods.
 * @author UndeadScythes
 */
public class ExtendedWorld implements World {
    private World base;

    /**
     * Wrap an existing world with these lovely extra methods.
     * @param world World to wrap.
     */
    public ExtendedWorld(World world) {
        base = world;
    }

    /**
     * Build a tower of blocks in the world with an optinoal topper.
     * @param x X coordinate of tower.
     * @param z Z coordinate of tower.
     * @param height Height of tower.
     * @param material Material to make the main tower out of.
     * @param topper Block to place on top, <code>null</code> if none required.
     */
    public void buildTower(int x, int z, int height, Material material, Material topper) {
        int y = getHighestBlockYAt(x, z);
        int maxY = y + height;
        int i;
        for(i = y; i < maxY; i++) {
            getBlockAt(x, i, z).setType(material);
        }
        if(topper != null) {
            getBlockAt(x, i, z).setType(topper);
        }
    }

    /**
     * Place a line of blocks with optional toppers. Either dX or dZ should be 0 to indicate direction.
     * @param x X coordinate of line.
     * @param z Z coordinate of line.
     * @param dX Length of line in X axis.
     * @param dZ Length of line in Z axis.
     * @param material Material to make line out of.
     * @param topper Optional topper to place on top of the line, <code>null</code> if not required.
     */
    public void buildLine(int x, int z, int dX, int dZ, Material material, Material topper) {
        if(dX == 0) {
            int maxZ = z + dZ;
            for(int i = z; i <= maxZ; i++) {
                int y = getHighestBlockYAt(x, i);
                getBlockAt(x, y, i).setType(material);
                if(topper != null) {
                    getBlockAt(x, y + 1, i).setType(topper);
                }
            }
        } else {
            int maxX = x + dX;
            for(int i = x; i <= maxX; i++) {
                int y = getHighestBlockYAt(i, z);
                getBlockAt(i, y, z).setType(material);
                if(topper != null) {
                    getBlockAt(i, y + 1, z).setType(topper);
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block getBlockAt(int x, int y, int z) {
        return base.getBlockAt(x, y, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block getBlockAt(Location location) {
        return base.getBlockAt(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getBlockTypeIdAt(int x, int y, int z) {
        return base.getBlockTypeIdAt(x, y, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getBlockTypeIdAt(Location location) {
        return base.getBlockTypeIdAt(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getHighestBlockYAt(int x, int z) {
        return base.getHighestBlockYAt(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getHighestBlockYAt(Location location) {
        return base.getHighestBlockYAt(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block getHighestBlockAt(int x, int z) {
        return base.getHighestBlockAt(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block getHighestBlockAt(Location location) {
        return base.getHighestBlockAt(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Chunk getChunkAt(int x, int z) {
        return base.getChunkAt(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Chunk getChunkAt(Location location) {
        return base.getChunkAt(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Chunk getChunkAt(Block block) {
        return base.getChunkAt(block);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isChunkLoaded(Chunk chunk) {
        return base.isChunkLoaded(chunk);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Chunk[] getLoadedChunks() {
        return base.getLoadedChunks();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void loadChunk(Chunk chunk) {
        base.loadChunk(chunk);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isChunkLoaded(int x, int z) {
        return base.isChunkLoaded(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isChunkInUse(int x, int z) {
        return base.isChunkInUse(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void loadChunk(int x, int z) {
        base.loadChunk(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        return base.loadChunk(x, z, generate);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunk(Chunk chunk) {
        return base.unloadChunk(chunk);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunk(int x, int z) {
        return base.unloadChunk(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunk(int x, int z, boolean save) {
        return base.unloadChunk(x, z, save);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        return base.unloadChunk(x, z, save, safe);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunkRequest(int x, int z) {
        return base.unloadChunkRequest(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        return base.unloadChunkRequest(x, z, safe);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean regenerateChunk(int x, int z) {
        return base.regenerateChunk(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean refreshChunk(int x, int z) {
        return base.refreshChunk(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Item dropItem(Location location, ItemStack item) {
        return base.dropItem(location, item);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Item dropItemNaturally(Location location, ItemStack item) {
        return base.dropItemNaturally(location, item);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Arrow spawnArrow(Location location, Vector velocity, float speed, float spread) {
        return base.spawnArrow(location, velocity, speed, spread);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean generateTree(Location location, TreeType type) {
        return base.generateTree(location, type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        return base.generateTree(loc, type, delegate);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Entity spawnEntity(Location loc, EntityType type) {
        return base.spawnEntity(loc, type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public LivingEntity spawnCreature(Location loc, EntityType type) {
        return base.spawnCreature(loc, type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public LivingEntity spawnCreature(Location loc, CreatureType type) {
        return base.spawnCreature(loc, type);
    }

    /**
     * @inheritDoc
     */
    @Override
    public LightningStrike strikeLightning(Location loc) {
        return base.strikeLightning(loc);
    }

    /**
     * @inheritDoc
     */
    @Override
    public LightningStrike strikeLightningEffect(Location loc) {
        return base.strikeLightningEffect(loc);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Entity> getEntities() {
        return base.getEntities();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<LivingEntity> getLivingEntities() {
        return base.getLivingEntities();
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
         return base.getEntitiesByClass(classes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> cls) {
         return base.getEntitiesByClass(cls);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        return base.getEntitiesByClasses(classes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Player> getPlayers() {
        return base.getPlayers();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return base.getName();
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUID() {
        return base.getUID();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Location getSpawnLocation() {
        return base.getSpawnLocation();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean setSpawnLocation(int x, int y, int z) {
        return base.setSpawnLocation(x, y, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getTime() {
        return base.getTime();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTime(long time) {
        base.setTime(time);
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getFullTime() {
        return base.getFullTime();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFullTime(long time) {
        base.setFullTime(time);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasStorm() {
        return base.hasStorm();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setStorm(boolean hasStorm) {
        base.setStorm(hasStorm);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getWeatherDuration() {
        return base.getWeatherDuration();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setWeatherDuration(int duration) {
        base.setWeatherDuration(duration);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isThundering() {
        return base.isThundering();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setThundering(boolean thundering) {
        base.setThundering(thundering);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getThunderDuration() {
        return base.getThunderDuration();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setThunderDuration(int duration) {
        base.setThunderDuration(duration);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean createExplosion(double x, double y, double z, float power) {
        return base.createExplosion(x, y, z, power);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return base.createExplosion(x, y, z, power, setFire);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean createExplosion(Location loc, float power) {
        return base.createExplosion(loc, power);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire) {
        return base.createExplosion(loc, power, setFire);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Environment getEnvironment() {
        return base.getEnvironment();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getSeed() {
        return base.getSeed();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getPVP() {
        return base.getPVP();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPVP(boolean pvp) {
        base.setPVP(pvp);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ChunkGenerator getGenerator() {
        return base.getGenerator();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save() {
        base.save();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<BlockPopulator> getPopulators() {
        return base.getPopulators();
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        return base.spawn(location, clazz);
    }

    /**
     * @inheritDoc
     */
    @Override
    public FallingBlock spawnFallingBlock(Location location, Material material, byte data) throws IllegalArgumentException {
        return base.spawnFallingBlock(location, material, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public FallingBlock spawnFallingBlock(Location location, int blockId, byte blockData) throws IllegalArgumentException {
        return base.spawnFallingBlock(location, blockId, blockData);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playEffect(Location location, Effect effect, int data) {
        base.playEffect(location, effect, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playEffect(Location location, Effect effect, int data, int radius) {
        base.playEffect(location, effect, data, radius);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T> void playEffect(Location location, Effect effect, T data) {
        base.playEffect(location, effect, data);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T> void playEffect(Location location, Effect effect, T data, int radius) {
        base.playEffect(location, effect, data, radius);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
        return base.getEmptyChunkSnapshot(x, z, includeBiome, includeBiomeTempRain);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        base.setSpawnFlags(allowMonsters, allowAnimals);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getAllowAnimals() {
        return base.getAllowAnimals();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getAllowMonsters() {
        return base.getAllowMonsters();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Biome getBiome(int x, int z) {
        return base.getBiome(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBiome(int x, int z, Biome bio) {
        base.setBiome(x, z, bio);
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getTemperature(int x, int z) {
        return base.getTemperature(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getHumidity(int x, int z) {
        return base.getHumidity(x, z);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMaxHeight() {
        return base.getMaxHeight();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getSeaLevel() {
        return base.getSeaLevel();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean getKeepSpawnInMemory() {
        return base.getKeepSpawnInMemory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setKeepSpawnInMemory(boolean keepLoaded) {
        base.setKeepSpawnInMemory(keepLoaded);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isAutoSave() {
        return base.isAutoSave();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setAutoSave(boolean value) {
        base.setAutoSave(value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setDifficulty(Difficulty difficulty) {
        base.setDifficulty(difficulty);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Difficulty getDifficulty() {
        return base.getDifficulty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public File getWorldFolder() {
        return base.getWorldFolder();
    }

    /**
     * @inheritDoc
     */
    @Override
    public WorldType getWorldType() {
        return  base.getWorldType();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean canGenerateStructures() {
        return base.canGenerateStructures();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getTicksPerAnimalSpawns() {
        return base.getTicksPerAnimalSpawns();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
        base.setTicksPerAnimalSpawns(ticksPerAnimalSpawns);
    }

    /**
     * @inheritDoc
     */
    @Override
    public long getTicksPerMonsterSpawns() {
        return base.getTicksPerMonsterSpawns();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
        base.setTicksPerMonsterSpawns(ticksPerMonsterSpawns);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getMonsterSpawnLimit() {
        return base.getMonsterSpawnLimit();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMonsterSpawnLimit(int limit) {
        base.setMonsterSpawnLimit(limit);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getAnimalSpawnLimit() {
        return base.getAnimalSpawnLimit();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setAnimalSpawnLimit(int limit) {
        base.setAnimalSpawnLimit(limit);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getWaterAnimalSpawnLimit() {
        return base.getWaterAnimalSpawnLimit();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setWaterAnimalSpawnLimit(int limit) {
        base.setWaterAnimalSpawnLimit(limit);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getAmbientSpawnLimit() {
        return base.getAmbientSpawnLimit();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setAmbientSpawnLimit(int arg0) {
        base.setAmbientSpawnLimit(arg0);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSound(Location loc, Sound sound, float volume, float pitch) {
        base.playSound(loc, sound, volume, pitch);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String[] getGameRules() {
        return base.getGameRules();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getGameRuleValue(String rule) {
        return base.getGameRuleValue(rule);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean setGameRuleValue(String rule, String value) {
        return base.setGameRuleValue(rule, value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isGameRule(String rule) {
        return base.isGameRule(rule);
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
}
