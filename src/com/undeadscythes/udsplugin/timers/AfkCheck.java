package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.util.Vector;

/**
 * Scheduled task to check if a player is AFK.
 *
 * @author UndeadScythes
 */
public class AfkCheck implements Runnable {
    @Override
    public final void run() {
        Iterator<SaveablePlayer> i = PlayerUtils.getOnlinePlayers().iterator();
        while(i.hasNext()) {
            SaveablePlayer player = i.next();
            if(player.hasPerm(Perm.UNKICKABLE)) continue;
            final Vector vector = VectorUtils.getFlooredVector(player.getLocation().toVector());
            if(vector.isInSphere(player.getLastVector(), 1)) {
                player.kickPlayer("You have been kicked for idling.");
            } else {
                player.setLastVector(vector);
            }
        }
    }
}
