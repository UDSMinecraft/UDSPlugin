package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.Region.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When snow or ice forms.
 * @author UndeadScythes
 */
public class BlockForm extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockFormEvent event) {
        if(!hasFlag(event.getNewState().getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
