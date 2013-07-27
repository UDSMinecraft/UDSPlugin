package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class Timer implements Runnable {
    /**
     * Number to divide to convert milliseconds to ticks.
     */
    public static final long TICKS = 50;
    /**
     * The number of milliseconds in a day.
     */
    public static final long DAY = 86400000;
    /**
     * The number of milliseconds in an hour.
     */
    public static final long HOUR = 3600000;
    /**
     * The number of milliseconds in a minute.
     */
    public static final long MINUTE = 60000;
    /**
     * The number of milliseconds in a second.
     */
    public static final long SECOND = 1000;

    private long now = System.currentTimeMillis();
    private long lastSlow = System.currentTimeMillis();

    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public Timer() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        now = System.currentTimeMillis();
        if(UDSPlugin.getData().getLastDaily() + DAY < now) {
            dailyTask();
            UDSPlugin.getData().setLastDaily();
        }
        if(lastSlow + UDSPlugin.getConfigLong(ConfigRef.SLOW_TIME) < now) {
            try {
                slowTask();
            } catch (IOException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
            lastSlow = now;
        }
        fastTask();
    }

    private void dailyTask() {
        for(Region quarry : UDSPlugin.getRegions(RegionType.QUARRY).values()) {
            final Material material = Material.getMaterial(quarry.getData().toUpperCase());
            final int dX = quarry.getV2().getBlockX() - quarry.getV1().getBlockX();
            final int dY = quarry.getV2().getBlockY() - quarry.getV1().getBlockY();
            final int dZ = quarry.getV2().getBlockZ() - quarry.getV1().getBlockZ();
            for(int x = 0; x <= dX; x++) {
                for(int y = 0; y <= dY; y++) {
                    for(int z = 0; z <= dZ; z++) {
                        quarry.getWorld().getBlockAt(x, y, z).setType(material);
                    }
                }
            }
        }
        UDSPlugin.sendBroadcast("The quarries have been refilled.");
        for(SaveablePlayer vip : PlayerUtils.getVips()) {
            vip.setVIPSpawns(UDSPlugin.getConfigInt(ConfigRef.VIP_SPAWNS));
            if(vip.isOnline()) {
                vip.sendNormal("Your daily item spawns have been refilled.");
            }
        }
    }

    private void slowTask() throws IOException {
        if(UDSPlugin.getData().getLastEnderDeath() > -1 && UDSPlugin.getData().getLastEnderDeath() + UDSPlugin.getConfigLong(ConfigRef.DRAGON_RESPAWN) < now) {
            for(World world : Bukkit.getWorlds()) {
                if(world.getEnvironment().equals(World.Environment.THE_END) && world.getEntitiesByClass(EnderDragon.class).isEmpty()) {
                    world.spawnEntity(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 20, 0), EntityType.ENDER_DRAGON);
                    UDSPlugin.getData().setLastEnderDeath(-1);
                    Block block;
                    for(int x = -100; x < 100; x++) {
                        for(int z = -100; z < 100; z++) {
                            block = world.getHighestBlockAt(x, z);
                            if(block.getType().equals(Material.BEDROCK)) {
                                spawnNewEnderCrystal(block.getLocation().add(0.5, 0, 0.5));
                                block.getRelative(BlockFace.UP).setType(Material.FIRE);
                            } else if(block.getType().equals(Material.FIRE) && block.getRelative(BlockFace.DOWN).getType().equals(Material.BEDROCK)) {
                                spawnNewEnderCrystal(block.getRelative(BlockFace.DOWN).getLocation().add(0.5, 0, 0.5));
                            }
                        }
                    }
                    UDSPlugin.sendBroadcast("The Ender Dragon has regained his strength and awaits brave warriors in The End.");
                }
                if(!UDSPlugin.checkWorldFlag(world, RegionFlag.TIME)) {
                    world.setTime(world.getTime() - (now - lastSlow));
                }
            }
        }
        UDSPlugin.saveFiles();
    }

    private void spawnNewEnderCrystal(final Location location) {
        for(Entity entity : location.getWorld().getEntitiesByClass(EnderCrystal.class)) {
            if(entity.getLocation().distanceSquared(location) < 2) {
                return;
            }
        }
        location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
    }

    private void fastTask() {
        for(SaveablePlayer player : PlayerUtils.getOnlinePlayers()) {
            if(player.hasPermission(Perm.VIP) && player.getVIPTime() + UDSPlugin.getConfigLong(ConfigRef.VIP_TIME) < now) {
                player.setVIPTime(0);
                player.setRank(PlayerRank.MEMBER);
                player.sendNormal("Your time as a VIP has come to an end.");
            }
            if(player.isJailed() && player.getJailTime() + player.getJailSentence() < now) {
                player.release();
                player.sendNormal("You have served your time.");
            }
            if(player.hasGodMode()) {
                player.setFoodLevel(20);
            }
            final int distanceSq = Math.abs((int)Math.pow(player.getLocation().getBlockX(), 2) + (int)Math.pow(player.getLocation().getBlockZ(), 2));
            if(distanceSq - UDSPlugin.getConfigIntSq(ConfigRef.WORLD_BORDER) > 100) {
                final double ratio = UDSPlugin.getConfigIntSq(ConfigRef.WORLD_BORDER) / distanceSq;
                player.move(Warp.findSafePlace(player.getLocation().clone().multiply(ratio)));
                player.sendNormal("You have reached the edge of the currently explorable world.");
            }
        }
        for(final Iterator<Map.Entry<String, Request>> i = UDSPlugin.getRequests().entrySet().iterator(); i.hasNext();) {
            final Request request = i.next().getValue();
            if(request.getTime() + UDSPlugin.getConfigLong(ConfigRef.REQUEST_TTL) < now) {
                request.getSender().sendNormal("Your request has timed out.");
                i.remove();
            }
        }
        EntityTracker.checkMinecarts();
    }
}
