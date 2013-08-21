package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.RegionFlag;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * Fired when snow or ice forms.
 * 
 * @author UndeadScythes
 */
public class BlockForm extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockFormEvent event) {
        if(!hasFlag(event.getNewState().getLocation(), RegionFlag.SNOW)) {
            event.setCancelled(true);
        }
    }
}
