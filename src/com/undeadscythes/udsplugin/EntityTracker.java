package com.undeadscythes.udsplugin;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Tracking entities and various properties.
 * @author UndeadScythes
 */
public final class EntityTracker {
    private final static Set<OwnedMinecart> MINECARTS = new HashSet<OwnedMinecart>();

    public static boolean minecartNear(final Location location) {
        for(OwnedMinecart minecart : MINECARTS) {
            if(minecart.near(location)) {
                return true;
            }
        }
        return false;
    }

    public static void addMinecart(final Minecart minecart, final SaveablePlayer owner, final boolean returnToSender) {
        for(OwnedMinecart listed : MINECARTS) {
            if(listed.getUUID().equals(minecart.getUniqueId())) {
                return;
            }
        }
        MINECARTS.add(new OwnedMinecart(minecart, owner, returnToSender));
    }

    public static void spawnMinecart(final SaveablePlayer player, final Location location, final boolean returnToSender) {
        EntityTracker.addMinecart(location.getWorld().spawn(location.clone().add(0.5, 0.5, 0.5), Minecart.class), player, returnToSender);
    }

    public static void checkMinecarts() {
        for(final Iterator<OwnedMinecart> i = MINECARTS.iterator() ; i.hasNext();) {
            final OwnedMinecart minecart = i.next();
            if(minecart.isEmpty() && minecart.age(100) > Config.minecartTTL / Timer.TICKS) {
                minecart.remove();
                i.remove();
            }
        }
    }

    public static void minecartRemoved(final UUID id) {
        for(final Iterator<OwnedMinecart> i = MINECARTS.iterator() ; i.hasNext();) {
            final OwnedMinecart minecart = i.next();
            if(minecart.getUUID().equals(id)) {
                i.remove();
            }
        }
    }

    public static void findMinecarts() {
        for(World world : Bukkit.getWorlds()) {
            for(Minecart minecart : world.getEntitiesByClass(Minecart.class)) {
                addMinecart(minecart, null, false);
            }
        }
    }

    private EntityTracker() {}
}
