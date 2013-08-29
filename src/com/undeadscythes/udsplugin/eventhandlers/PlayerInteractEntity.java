package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

/**
 * @author UndeadScythes
 */
public class PlayerInteractEntity extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerInteractEntityEvent event) throws NoPlayerFoundException {
        final Entity entity = event.getRightClicked();
        if(entity instanceof Tameable) {
            final Tameable pet = (Tameable)entity;
            if(pet.isTamed()) {
                final String ownerName = pet.getOwner().getName();
                if(ownerName.equals(event.getPlayer().getName())) {
                    if(event.getPlayer().isSneaking()) {
                        MemberUtils.getMember(event.getPlayer().getName()).selectPet(entity.getUniqueId());
                        MemberUtils.getOnlineMember(event.getPlayer()).sendNormal("Pet selected.");
                        event.setCancelled(true);
                    }
                } else {
                    MemberUtils.getOnlineMember(event.getPlayer()).sendNormal("This animal belongs to " + MemberUtils.getMember(ownerName).getNick());
                    event.setCancelled(true);
                }
            }
        } else if(entity instanceof ItemFrame) {
            final Member player = MemberUtils.getOnlineMember(event.getPlayer());
            if(!(player.canBuildHere(entity.getLocation()))) {
                event.setCancelled(true);
                player.sendNormal(Message.CANT_BUILD_HERE);
            }
        }
    }
}
