package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class MinecartChecks implements Runnable {
    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public MinecartChecks() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        EntityTracker.checkMinecarts();
    }
}
