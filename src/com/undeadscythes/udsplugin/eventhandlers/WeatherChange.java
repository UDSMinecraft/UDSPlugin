package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.WorldFlag;
import com.undeadscythes.udsplugin.UDSPlugin;
import org.bukkit.event.*;
import org.bukkit.event.weather.*;

/**
 * Fired when the weather is about to change in a world.
 * 
 * @author UndeadScythes
 */
public class WeatherChange extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final WeatherChangeEvent event) {
        if(!UDSPlugin.checkWorldFlag(event.getWorld(), WorldFlag.WEATHER)) {
            event.setCancelled(true);
        }
    }
}
