package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.regions.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.block.BlockIgniteEvent.*;

/**
 * @author UndeadScythes
 */
public class BlockIgnite extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final BlockIgniteEvent event) {
        if((event.getCause().equals(IgniteCause.FLINT_AND_STEEL) || event.getCause().equals(IgniteCause.FIREBALL))) {
            final Member player = MemberUtils.getOnlineMember(event.getPlayer());
            if(!player.canBuildHere(event.getBlock().getLocation())) {
                player.sendMessage(Message.CANT_BUILD_HERE);
                event.setCancelled(true);
            }
        } else if(!hasFlag(event.getBlock().getLocation(), RegionFlag.FIRE)) {
            event.setCancelled(true);
        }
    }
}
