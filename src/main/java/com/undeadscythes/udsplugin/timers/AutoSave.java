package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import java.io.*;
import java.util.logging.*;

/**
 * Scheduled task to save configuration and object data.
 * 
 * @author UndeadScythes
 */
public class AutoSave implements Runnable {
    public AutoSave() {}

    @Override
    public final void run() {
        try {
            UDSPlugin.saveFiles();
        } catch(IOException ex) {
            Logger.getLogger(AutoSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
