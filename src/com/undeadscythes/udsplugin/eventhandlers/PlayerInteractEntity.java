package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * When a player interacts with an entity.
 * @author UndeadScythes
 */
public class PlayerInteractEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final PlayerInteractEntityEvent event) {
        final Entity entity = event.getRightClicked();
        if(entity instanceof Tameable) {
            final Tameable pet = (Tameable)entity;
            if(pet.isTamed()) {
                final String ownerName = pet.getOwner().getName();
                if(ownerName.equals(event.getPlayer().getName())) {
                    if(event.getPlayer().isSneaking()) {
                        PlayerUtils.getPlayer(event.getPlayer().getName()).selectPet(entity.getUniqueId());
                        event.getPlayer().sendMessage(Color.MESSAGE + "Pet selected.");
                        event.setCancelled(true);
                    }
                } else {
                    event.getPlayer().sendMessage(Color.MESSAGE + "This animal belongs to " + PlayerUtils.getPlayer(ownerName).getNick());
                    event.setCancelled(true);
                }
            }
        } else if(entity instanceof ItemFrame) {
            final SaveablePlayer player = PlayerUtils.getPlayer(event.getPlayer().getName());
            if(!(player.canBuildHere(entity.getLocation()))) {
                event.setCancelled(true);
                player.sendMessage(Message.CANT_BUILD_HERE);
            }
        }
    }
}
