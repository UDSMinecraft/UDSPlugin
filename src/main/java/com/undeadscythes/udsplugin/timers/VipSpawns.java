package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;

/**
 * @author UndeadScythes
 */
public class VipSpawns implements Runnable {
    public VipSpawns() {}

    @Override
    public void run() {
        for(OfflineMember vip : MemberUtils.getVips()) {
            vip.setVIPSpawns(Config.VIP_SPAWNS);
            try {
                MemberUtils.getOnlineMember(vip).sendNormal("Your daily item spawns have been refilled.");
            } catch(PlayerNotOnlineException ex) {}
        }
    }
}
