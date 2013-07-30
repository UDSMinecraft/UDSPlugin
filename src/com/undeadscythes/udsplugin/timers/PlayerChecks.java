package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class PlayerChecks implements Runnable {
    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public PlayerChecks() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        for(SaveablePlayer player : PlayerUtils.getOnlinePlayers()) {
            if(player.hasPermission(Perm.VIP) && player.getVIPTime() + UDSPlugin.getConfigLong(ConfigRef.VIP_TIME) < System.currentTimeMillis()) {
                player.setVIPTime(0);
                player.setRank(PlayerRank.MEMBER);
                player.sendNormal("Your time as a VIP has come to an end.");
            }
            if(player.isJailed() && player.getJailTime() + player.getJailSentence() < System.currentTimeMillis()) {
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
            if(player.getKills() > 0 && player.getPvpTime() + TimeUtils.HOUR < System.currentTimeMillis()) {
                    player.removeKill();
            }
        }
    }
}
