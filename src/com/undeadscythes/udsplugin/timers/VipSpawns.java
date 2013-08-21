package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;

/**
 * @author UndeadScythes
 */
public class VipSpawns implements Runnable {
    public VipSpawns() {}

    @Override
    public void run() {
        for(Member vip : PlayerUtils.getVips()) {
            vip.setVIPSpawns(Config.VIP_SPAWNS);
            try {
                PlayerUtils.getOnlinePlayer(vip).sendNormal("Your daily item spawns have been refilled.");
            } catch (PlayerNotOnlineException ex) {}
        }
    }
}
