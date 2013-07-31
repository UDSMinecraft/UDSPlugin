package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class AfkCheck implements Runnable {
    @Override
    public void run() {
        for(final SaveablePlayer player : PlayerUtils.getOnlinePlayers()) {
            final ArrayList<Float> nowView = new ArrayList<Float>(2);
            nowView.add(0, player.getLocation().getPitch());
            nowView.add(1, player.getLocation().getYaw());
            final ArrayList<Float> lastView = player.getLastView();
            if(lastView.get(0) == nowView.get(0) && lastView.get(1) == nowView.get(1)) {
                player.kickPlayer("You have been kicked for idling.");
            } else {
                player.setLastView(nowView);
            }
        }
    }
}
