package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * A minecart owned by a player.
 * @author UndeadScythes
 */
public class OwnedMinecart {
    private final SaveablePlayer owner;
    private final Minecart minecart;
    private final boolean returnToOwner;

    public OwnedMinecart(final Minecart minecart, final SaveablePlayer owner, final boolean returnToOwner) {
        this.owner = owner;
        this.minecart = minecart;
        this.returnToOwner = returnToOwner;
    }

    public boolean near(final Location location) {
        return minecart.getLocation().distance(location) < 2;
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
        if(returnToOwner) {
            owner.giveAndDrop(new ItemStack(Material.MINECART));
        }
    }

    public UUID getUUID() {
        return minecart.getUniqueId();
    }
}
