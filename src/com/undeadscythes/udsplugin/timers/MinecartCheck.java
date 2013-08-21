package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * Scheduled task for tracking {@link org.bukkit.entity.Minecart} entities.
 * 
 * @author UndeadScythes
 */
public class MinecartCheck implements Runnable {
    private static final HashSet<OwnedMinecart> MINECARTS = new HashSet<OwnedMinecart>(0);

    public static boolean minecartNear(final Location location) {
        for(OwnedMinecart minecart : MINECARTS) {
            if(minecart.near(location)) {
                return true;
            }
        }
        return false;
    }

    public static void addMinecart(final Minecart minecart, final Member owner) {
        for(OwnedMinecart listed : MINECARTS) {
            if(listed.getUUID().equals(minecart.getUniqueId())) {
                listed.setOwner(owner);
                return;
            }
        }
        MINECARTS.add(new OwnedMinecart(minecart, owner));
    }

    public static void tagMinecart(final Member player, final Location location) {
        addMinecart(location.getWorld().spawn(location.clone().add(0.5, 0.5, 0.5), Minecart.class), player);
    }

    public static void checkMinecarts() {
        for(final Iterator<OwnedMinecart> i = MINECARTS.iterator(); i.hasNext();) {
            final OwnedMinecart minecart = i.next();
            if(minecart.isEmpty() && minecart.age(100) > Config.MINECART_TTL * TimeUtils.TICK) {
                minecart.remove();
                i.remove();
            }
        }
    }

    public static void minecartRemoved(final UUID id) {
        final Iterator<OwnedMinecart> i = MINECARTS.iterator();
        while(i.hasNext()) {
            final OwnedMinecart minecart = i.next();
            if(minecart.getUUID().equals(id)) {
                i.remove();
            }
        }
    }

    public static void findMinecarts() {
        for(World world : Bukkit.getWorlds()) {
            for(Minecart minecart : world.getEntitiesByClass(Minecart.class)) {
                addMinecart(minecart, null);
            }
        }
    }

    public static void removeMinecart(final UUID minecartId) {
        for(final Iterator<OwnedMinecart> i = MINECARTS.iterator(); i.hasNext();) {
            final OwnedMinecart listed = i.next();
            if(listed.getUUID().equals(minecartId)) {
                listed.remove();
                i.remove();
                return;
            }
        }
    }

    @Override
    public final void run() {
        checkMinecarts();
    }
}
