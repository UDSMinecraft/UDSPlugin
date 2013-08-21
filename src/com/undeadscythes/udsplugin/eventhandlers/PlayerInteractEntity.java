package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerInteractEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerInteractEntityEvent event) {
        final Entity entity = event.getRightClicked();
        if(entity instanceof Tameable) {
            final Tameable pet = (Tameable)entity;
            if(pet.isTamed()) {
                final String ownerName = pet.getOwner().getName();
                if(ownerName.equals(event.getPlayer().getName())) {
                    if(event.getPlayer().isSneaking()) {
                        PlayerUtils.getPlayer(event.getPlayer().getName()).selectPet(entity.getUniqueId());
                        PlayerUtils.getOnlinePlayer(event.getPlayer()).sendNormal("Pet selected.");
                        event.setCancelled(true);
                    }
                } else {
                    PlayerUtils.getOnlinePlayer(event.getPlayer()).sendNormal("This animal belongs to " + PlayerUtils.getPlayer(ownerName).getNick());
                    event.setCancelled(true);
                }
            }
        } else if(entity instanceof ItemFrame) {
            final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
            if(!(player.canBuildHere(entity.getLocation()))) {
                event.setCancelled(true);
                player.sendNormal(Message.CANT_BUILD_HERE);
            }
        }
    }
}
