package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * Scheduled task to reset VIPs item spawn count each day.
 * 
 * @author UndeadScythes
 */
public class VipSpawns implements Runnable {
    public VipSpawns() {}

    @Override
    public final void run() {
        for(SaveablePlayer vip : PlayerUtils.getVips()) {
            vip.setVIPSpawns(Config.VIP_SPAWNS);
            if(vip.isOnline()) {
                vip.sendNormal("Your daily item spawns have been refilled.");
            }
        }
    }
}
