package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

/**
 * When snow or ice forms.
 * @author UndeadScythes
 */
public class BlockForm extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(BlockFormEvent event) {
        if(!hasSnow(event.getNewState().getLocation())) {
            event.setCancelled(true);
        }
    }
}
