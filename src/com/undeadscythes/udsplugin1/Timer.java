package com.undeadscythes.udsplugin1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class Timer implements Runnable {
    /**
     * The number of milliseconds in a day.
     */
    public static long DAY = 86400000;

    UDSPlugin plugin;
    private FileConfiguration config;
    private long now = System.currentTimeMillis();
    private long day;
    private long lastSlow = System.currentTimeMillis();
    private long slowTime;
    private long vipTime;
    private int vipSpawns;
    private int worldBorderSq;
    private long requestTime;

    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public Timer(UDSPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        slowTime = config.getLong(Config.SLOW_TIME + "");
        vipTime = config.getLong(Config.VIP_TIME + "");
        vipSpawns = config.getInt(Config.VIP_SPAWNS + "");
        worldBorderSq = (int)Math.pow(config.getInt(Config.WORLD_BORDER + ""), 2);
        requestTime = config.getLong(Config.REQUEST_TIME + "");
    }

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        now = System.currentTimeMillis();
        if(day + DAY < now) {
            try {
                dailyTask();
            } catch (IOException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }
            day = now;
        }
        if(lastSlow + slowTime < now) {
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

    private void dailyTask() throws IOException {
        config.set(Config.DAY + "", day);
        plugin.saveConfig();
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
        Bukkit.broadcastMessage(Message.QUARRIES_FILLED + "");
        for(ExtendedPlayer vip : UDSPlugin.getVIPS().values()) {
            vip.setVIPSpawns(vipSpawns);
            if(vip.isOnline()) {
                vip.sendMessage(Message.SPAWNS_REFILLED + "");
            }
        }
    }

    private void slowTask() throws IOException {
        UDSPlugin.saveFiles();
    }

    private void fastTask() throws IOException {
        for(ExtendedPlayer player : UDSPlugin.getOnlinePlayers().values()) {
            if(player.getRank().equals(Rank.VIP) && player.getVIPTime() + vipTime < now) {
                player.setVIPTime(0);
                player.setRank(Rank.MEMBER);
                player.sendMessage(Message.VIP_END + "");
            }
            if(player.isJailed() && player.getJailTime() + player.getJailSentence() < now) {
                player.setJailTime(0);
                player.setJailSentence(0);
                player.sendMessage(Message.JAIL_OUT + "");
                if(!player.quietTeleport(UDSPlugin.getWarps().get("jailout").getLocation())) {
                    BufferedWriter out = new BufferedWriter(new FileWriter(UDSPlugin.TICKET_PATH, true));
                    out.write(Message.NO_JAIL_OUT + "");
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
                player.sendMessage(Message.WORLD_BORDER + "");
            }

        }
        for(Request request : UDSPlugin.getRequests().values()) {
            if(request.getTime() + requestTime < now) {
                UDSPlugin.getPlayers().get(request.getSender()).sendMessage(Message.REQUEST_TIMEOUT + "");
                UDSPlugin.getRequests().remove(request.getReceiver());
            }
        }
    }
}
