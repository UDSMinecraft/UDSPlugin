package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import java.util.logging.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class OwnedMinecart {
    private OfflineMember owner;
    private final Minecart minecart;

    public OwnedMinecart(final Minecart minecart, final OfflineMember owner) {
        this.owner = owner;
        this.minecart = minecart;
    }

    public void setOwner(final OfflineMember player) {
        owner = player;
    }

    public boolean near(final Location location) {
        return minecart.getWorld().equals(location.getWorld()) && minecart.getLocation().distance(location) < 2;
    }

    public int age(final int ticks) {
        minecart.setTicksLived(minecart.getTicksLived() + ticks);
        return minecart.getTicksLived();
    }

    public boolean isEmpty() {
        return minecart.isEmpty();
    }

    public void remove() {
        this.minecart.remove();
        if(owner != null) {
            try {
                MemberUtils.getOnlineMember(owner).sendNormal("You picked up your minecart.");
                MemberUtils.getOnlineMember(owner).giveAndDrop(new ItemStack(Material.MINECART));
            } catch(PlayerNotOnlineException ex) {}
        }
    }

    public UUID getUUID() {
        return minecart.getUniqueId();
    }
}
