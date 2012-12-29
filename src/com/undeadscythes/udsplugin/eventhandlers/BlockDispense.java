package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.Dispenser;
import org.bukkit.util.*;

/**
 * A block dispenses an item.
 * @author UndeadScythes
 */
public class BlockDispense extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final BlockDispenseEvent event) {
        final Material itemType = event.getItem().getType();
        if(hasFlag(event.getBlock().getLocation(), RegionFlag.DISPENSER)) {
            final BlockFace blockFace = ((Dispenser)event.getBlock().getState().getData()).getFacing();
            Location location = event.getBlock().getRelative(blockFace).getLocation().add(0.5, 0.5, 0.5);
            switch(event.getItem().getType()) {
                case ARROW:
                    event.getBlock().getWorld().spawnArrow(location, toVector(blockFace), 1.5f, 5);
                    event.setCancelled(true);
                    break;
                case FIREBALL:
                    fireEntity(location, EntityType.FIREBALL, toVector(blockFace).multiply(5));
                    break;
                case EGG:
                    fireEntity(location, EntityType.EGG, toVector(blockFace).multiply(1.5));
                    break;
                case SNOW_BALL:
                    fireEntity(location, EntityType.SNOWBALL, toVector(blockFace).multiply(5));
                    break;
                case EXP_BOTTLE:
                    fireEntity(location, EntityType.THROWN_EXP_BOTTLE, toVector(blockFace).multiply(2));
                    break;
                case POTION:
                    //fireEntity(location, Potion.fromItemStack(event.getItem()).splash(), toVector(blockFace));
                    break;
                case FIREWORK:
                    final Firework firework = (Firework)fireEntity(location, EntityType.FIREWORK, new Vector(0, 0, 0));
                    firework.setFireworkMeta((FireworkMeta)(event.getItem().getItemMeta()));
                    break;
                case MINECART:
                    if(UDSPlugin.getRails().contains(location.getBlock().getType())) {
                        fireEntity(location, EntityType.MINECART, new Vector(0, 0, 0));
                    }
                    break;
                case BOAT:
                    if(UDSPlugin.getWater().contains(location.getBlock().getRelative(BlockFace.DOWN).getType())) {
                        fireEntity(location, EntityType.BOAT, new Vector(0, 0, 0));
                    }
                    break;
                case MONSTER_EGG:
                    fireEntity(location, EntityType.fromId(event.getItem().getData().getData()), new Vector(0, 0, 0));
                    break;
                default:
                    location.getWorld().dropItemNaturally(location, event.getItem());
            }
            event.setCancelled(true);
        }
    }

    private static Vector toVector(final BlockFace blockFace) {
        switch(blockFace) {
            case UP:
                return new Vector(0, 1, 0);
            case DOWN:
                return new Vector(0, -1, 0);
            case NORTH:
                return new Vector(0, 0, -1);
            case SOUTH:
                return new Vector(0, 0, 1);
            case EAST:
                return new Vector(1, 0, 0);
            case WEST:
                return new Vector(-1, 0, 0);
            default:
                return new Vector(0, 0, 0);
        }
    }

    private static Entity fireEntity(final Location location, final EntityType entityType, final Vector vector) {
        final Entity entity = location.getWorld().spawnEntity(location, entityType);
        entity.setVelocity(vector);
        return entity;
    }
}
