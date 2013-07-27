package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.logging.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class AutoSave implements Runnable {
    /**
     * Initiates the timer.
     * @param plugin The UDSPlugin.
     * @param interval The interval between passes.
     */
    public AutoSave() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        try {
            UDSPlugin.saveFiles();
        } catch (IOException ex) {
            Logger.getLogger(AutoSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
