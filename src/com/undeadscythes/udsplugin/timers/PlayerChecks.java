package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Scheduled task to perform various player checks.
 * 
 * @author UndeadScythes
 */
public class PlayerChecks implements Runnable {
    public PlayerChecks() {}

    @Override
    public final void run() {
        for(SaveablePlayer player : PlayerUtils.getOnlinePlayers()) {
            vipStatus(player);
            jailStatus(player);
            godMode(player);
            checkLocation(player);
            pvpStatus(player);
        }
    }
    
    private void pvpStatus(final SaveablePlayer player) {
        if(player.getKills() > 0 && player.getPvpTime() + TimeUtils.HOUR < System.currentTimeMillis()) {
                player.removeKill();
        }
    }
    
    private void checkLocation(final SaveablePlayer player) {
        final int distanceSq = Math.abs((int)Math.pow(player.getLocation().getBlockX(), 2) + (int)Math.pow(player.getLocation().getBlockZ(), 2));
        if(distanceSq - Config.WORLD_BORDER_SQRD > 100) {
            final double ratio = Config.WORLD_BORDER_SQRD / distanceSq;
            player.move(LocationUtils.findSafePlace(player.getLocation().clone().multiply(ratio)));
            player.sendNormal("You have reached the edge of the currently explorable world.");
        }
    }
    
    private void godMode(final SaveablePlayer player) {
        if(player.hasGodMode()) {
            player.setFoodLevel(20);
        }
    }
    
    private void jailStatus(final SaveablePlayer player) {
        if(player.isJailed() && player.getJailTime() + player.getJailSentence() < System.currentTimeMillis()) {
            player.release();
            player.sendNormal("You have served your time.");
        }
    }
    
    private void vipStatus(final SaveablePlayer player) {
        if(player.hasPermission(Perm.VIP_RANK) && player.getVIPTime() + Config.VIP_TIME < System.currentTimeMillis()) {
            player.setVIPTime(0);
            player.setRank(PlayerRank.MEMBER);
            player.sendNormal("Your time as a VIP has come to an end.");
        }
    }
}
