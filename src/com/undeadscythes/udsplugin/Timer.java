package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import java.io.*;
import java.util.logging.*;
import org.bukkit.*;
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
    private long worldBorderSq = (int)Math.pow(Config.WORLD_BORDER, 2);

    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public Timer() throws IOException {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        now = System.currentTimeMillis();
        if(UDSPlugin.lastDailyEvent + DAY < now) {
            dailyTask();
            UDSPlugin.lastDailyEvent = now;
        }
        if(lastSlow + Config.SLOW_TIME < now) {
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
        for(Region quarry : UDSPlugin.getQuarries().values()) {
            Material material = Material.getMaterial(quarry.getData().toUpperCase());
            int dX = quarry.getV2().getBlockX() - quarry.getV1().getBlockX();
            int dY = quarry.getV2().getBlockY() - quarry.getV1().getBlockY();
            int dZ = quarry.getV2().getBlockZ() - quarry.getV1().getBlockZ();
            for(int x = 0; x <= dX; x++) {
                for(int y = 0; y <= dY; y++) {
                    for(int z = 0; z <= dZ; z++) {
                        quarry.getWorld().getBlockAt(x, y, z).setType(material);
                    }
                }
            }
        }
        Bukkit.broadcastMessage(Color.BROADCAST + "The quarries have been refilled.");
        for(SaveablePlayer vip : UDSPlugin.getVIPS().values()) {
            vip.setVIPSpawns(Config.VIP_SPAWNS);
            if(vip.isOnline()) {
                vip.sendMessage(Color.MESSAGE + "Your daily item spawns have been refilled.");
            }
        }
    }

    private void slowTask() throws IOException {
        if(UDSPlugin.lastEnderDeath > 0 && UDSPlugin.lastEnderDeath + Config.DRAGON_RESPAWN < now) {
            for(World world : Bukkit.getWorlds()) {
                if(world.getEnvironment().equals(World.Environment.THE_END) && world.getEntitiesByClass(EnderDragon.class).isEmpty()) {
                    UDSPlugin.lastEnderDeath = 0;
                    world.spawnEntity(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 20, 0), EntityType.ENDER_DRAGON);
                    Bukkit.broadcastMessage(Color.BROADCAST + "The Ender Dragon has regained his strength and awaits brave warriors in The End.");
                }
            }
        }
        UDSPlugin.saveFiles();
    }

    private void fastTask() {
        for(SaveablePlayer player : UDSPlugin.getOnlinePlayers().values()) {
            if(player.getRank().equals(PlayerRank.VIP) && player.getVIPTime() + Config.VIP_TIME < now) {
                player.setVIPTime(0);
                player.setRank(PlayerRank.MEMBER);
                player.sendMessage(Color.MESSAGE + "Your time as a VIP has come to an end.");
            }
            if(player.isJailed() && player.getJailTime() + player.getJailSentence() < now) {
                player.release();
                player.sendMessage(Color.MESSAGE + "You have served your time.");
            }
            if(player.hasGodMode()) {
                player.setFoodLevel(20);
            }
            int distanceSq = Math.abs((int)Math.pow(player.getLocation().getBlockX(), 2) + (int)Math.pow(player.getLocation().getBlockZ(), 2));
            if(distanceSq - worldBorderSq > 100) {
                double ratio = worldBorderSq / distanceSq;
                player.move(Warp.findSafePlace(player.getWorld(), player.getLocation().getX() * ratio, player.getLocation().getZ() * ratio));
                player.sendMessage(Color.MESSAGE + "You have reached the edge of the currently explorable world.");
            }

        }
        for(Request request : UDSPlugin.getRequests().values()) {
            if(request.getTime() + Config.REQUEST_TIME < now) {
                request.getSender().sendMessage(Color.MESSAGE + "Your request has timed out.");
                UDSPlugin.getRequests().remove(request.getRecipient().getName());
            }
        }
        for(World world : Bukkit.getWorlds()) {
            for(Minecart minecart : world.getEntitiesByClass(Minecart.class)) {
                if(minecart.isEmpty() && minecart.getTicksLived() > Config.MINECART_LIFE) {
                    minecart.remove();
                    Bukkit.getLogger().info("" + minecart.getTicksLived());
                }
            }
        }
    }
}
