package com.undeadscythes.udsplugin;

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
     * The number of milliseconds in a day.
     */
    public static long DAY = 86400000;
    /**
     * The number of milliseconds in an hour.
     */
    public static long HOUR = 3600000;
    /**
     * The number of milliseconds in a minute.
     */
    public static long MINUTE = 60000;
    /**
     * The number of milliseconds in a second.
     */
    public static long SECOND = 1000;

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
        if(UDSPlugin.LAST_DAILY_EVENT + DAY < now) {
            dailyTask();
            UDSPlugin.LAST_DAILY_EVENT = now;
        }
        if(lastSlow + Config.SLOW_TIME < now) {
            try {
                slowTask();
            } catch (IOException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
            lastSlow = now;
        }
        try {
            fastTask();
        } catch (IOException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dailyTask() {
        for(Region quarry : UDSPlugin.getQuarries().values()) {
            Material material = Material.getMaterial(quarry.getData());
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
        Bukkit.broadcastMessage(Message.QUARRIES_FILLED.toString());
        for(ExtendedPlayer vip : UDSPlugin.getVIPS().values()) {
            vip.setVIPSpawns(Config.VIP_SPAWNS);
            if(vip.isOnline()) {
                vip.sendMessage(Message.SPAWNS_REFILLED);
            }
        }
    }

    private void slowTask() throws IOException {
        UDSPlugin.saveFiles();
        if(UDSPlugin.LAST_ENDER_DEATH + Config.DRAGON_RESPAWN < now) {
            for(World world : Bukkit.getWorlds()) {
                if(world.getEnvironment().equals(World.Environment.THE_END) && world.getEntitiesByClass(EnderDragon.class).isEmpty()) {
                    world.spawnEntity(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 20, 0), EntityType.ENDER_DRAGON);
                    Bukkit.broadcastMessage(Message.DRAGON_RESPAWN.toString());
                }
            }
        }
    }

    private void fastTask() throws IOException {
        for(ExtendedPlayer player : UDSPlugin.getOnlinePlayers().values()) {
            if(player.getRank().equals(Rank.VIP) && player.getVIPTime() + Config.VIP_TIME < now) {
                player.setVIPTime(0);
                player.setRank(Rank.MEMBER);
                player.sendMessage(Message.VIP_END);
            }
            if(player.isJailed() && player.getJailTime() + player.getJailSentence() < now) {
                player.setJailTime(0);
                player.setJailSentence(0);
                player.sendMessage(Message.JAIL_OUT);
                if(!player.quietTeleport(UDSPlugin.getWarps().get("jailout").getLocation())) {
                    BufferedWriter out = new BufferedWriter(new FileWriter(UDSPlugin.TICKET_PATH, true));
                    out.write(Message.NO_JAIL_OUT.toString());
                    out.close();
                }
                player.setGodMode(false);
            }
            if(player.hasGodMode()) {
                player.setFoodLevel(20);
            }
            int distanceSq = Math.abs((int)Math.pow(player.getLocation().getBlockX(), 2) + (int)Math.pow(player.getLocation().getBlockZ(), 2));
            if(distanceSq - worldBorderSq > 100) {
                double ratio = worldBorderSq / distanceSq;
                player.move(Warp.findSafePlace(player.getWorld(), player.getLocation().getX() * ratio, player.getLocation().getZ() * ratio));
                player.sendMessage(Message.WORLD_BORDER);
            }

        }
        for(Request request : UDSPlugin.getRequests().values()) {
            if(request.getTime() + Config.REQUEST_TIME < now) {
                request.getSender().sendMessage(Message.REQUEST_TIMEOUT);
                UDSPlugin.getRequests().remove(request.getRecipient().getName());
            }
        }
    }
}
