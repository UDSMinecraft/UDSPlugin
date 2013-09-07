package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsmeta.exceptions.*;
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
    public void run() {
        Iterator<Member> i = MemberUtils.getOnlineMembers().iterator();
        while(i.hasNext()) {
            Member player = i.next();
            if(player.hasPerm(Perm.UNKICKABLE)) continue;
            final Vector vector;
            vector = VectorUtils.getFlooredVector(player.getLocation().toVector());
            try {
                if(vector.isInSphere(player.getLastVector(), 1)) {
                    player.kickPlayer("You have been kicked for idling.");
                } else {
                    player.setLastVector(vector);
                }
            } catch(NoMetadataSetException ex) {}
        }
        MemberUtils.updateMembers();
    }
}
