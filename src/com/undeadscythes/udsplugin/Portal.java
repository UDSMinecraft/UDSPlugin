package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class Portal implements Saveable {
    private final String name;
    private Warp warp;
    private Portal portal;
    private String portalLink = "";
    private final World world;
    private final Vector min, max;
    private Direction exit;

    public Portal(final String name, final Warp warp, final World world, final Vector v1, final Vector v2) {
        this.name = name;
        this.warp = warp;
        this.world = world;
        this.min = VectorUtils.getFlooredVector(Vector.getMinimum(v1, v2));
        this.max = VectorUtils.getFlooredVector(Vector.getMaximum(v1, v2));
    }

    @SuppressWarnings("fallthrough")
    public Portal(final String record) {
        final String[] recordSplit = record.split("\t");
        switch (recordSplit.length) {
            case 7:
                exit = Direction.getByName(recordSplit[6]);
            default:
                name = recordSplit[0];
                warp = WarpUtils.getWarp(recordSplit[1]);
                portalLink = recordSplit[2];
                world = Bukkit.getWorld(recordSplit[3]);
                min = VectorUtils.getVector(recordSplit[4]);
                max = VectorUtils.getVector(recordSplit[5]);
        }
    }

    @Override
    public String getRecord() {
        final ArrayList<String> record = new ArrayList<String>(7);
        record.add(name);
        record.add(warp == null ? "null" : warp.getName());
        record.add(portal == null ? "null" : portal.getName());
        record.add(world.getName());
        record.add(min.toString());
        record.add(max.toString());
        record.add(exit == null ? "null" : exit.toString());
        return StringUtils.join(record, "\t");
    }

    public boolean hasWarp() {
        if(warp != null || portal != null) {
            return true;
        }
        return false;
    }

    public String getWarpName() {
        if(warp == null) {
            if(portal != null) {
                return portal.getName() + "*";
            } else {
                return null;
            }
        }
        return warp.getName();
    }

    public void setWarp(final Warp warp) {
        this.warp = warp;
    }

    public void setPortal(final Portal portal) {
        warp = null;
        this.portal = portal;
    }

    public String getName() {
        return name;
    }

    public Vector getV1() {
        return min;
    }

    public Vector getV2() {
        return max;
    }

    public World getWorld() {
        return world;
    }

    public void warp(final Player player) {
        if(warp == null) {
            if(portal != null) {
                final Vector half = portal.getV2().clone().subtract(portal.getV1()).multiply(0.5);
                final Location mid = portal.getV1().clone().add(half).add(new Vector(0.5, 0, 0.5)).toLocation(portal.getWorld());
                final Location to = LocationUtils.findFloor(mid);
                to.setYaw(portal.getYaw());
                player.teleport(to);
                player.setFlying(false);
            }
        } else {
            player.teleport(warp.getLocation());
        }
    }

    public void warp(final Entity entity) {
        if(warp == null) {
            if(portal != null) {
                final Vector half = portal.getV2().clone().subtract(portal.getV1()).multiply(0.5);
                final Location mid = portal.getV1().clone().add(half).add(new Vector(0.5, 0, 0.5)).toLocation(portal.getWorld());
                entity.teleport(LocationUtils.findFloor(mid));
            }
        } else {
            entity.teleport(warp.getLocation());
        }
    }

    public boolean crossWorld() {
        if(warp == null) {
            if(portal != null && world.equals(portal.getWorld())) {
                return false;
            }
            return true;
        } else {
            if(world.equals(warp.getLocation().getWorld())) {
                return false;
            }
            return true;
        }
    }

    public void linkPortal() {
        if(!portalLink.isEmpty()) {
            setPortal(PortalUtils.getPortal(portalLink));
        }
    }

    public float getYaw() {
        return exit == null ? 0 : exit.getYaw();
    }

    public void setExit(final Direction exit) {
        this.exit = exit;
    }
}
