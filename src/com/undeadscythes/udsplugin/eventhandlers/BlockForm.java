package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.regions.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when snow or ice forms.
 * 
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
