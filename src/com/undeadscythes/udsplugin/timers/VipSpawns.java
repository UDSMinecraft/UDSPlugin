package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class VipSpawns implements Runnable {
    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public VipSpawns() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        for(SaveablePlayer vip : PlayerUtils.getVips()) {
            vip.setVIPSpawns(UDSPlugin.getConfigInt(ConfigRef.VIP_SPAWNS));
            if(vip.isOnline()) {
                vip.sendNormal("Your daily item spawns have been refilled.");
            }
        }
    }
}
