package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.ListenerWrapper;
import com.undeadscythes.udsplugin.RegionFlag;
import com.undeadscythes.udsplugin.UDSPlugin;
import com.undeadscythes.udsplugin.utilities.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.Dispenser;
import org.bukkit.util.*;

/**
 * Fired when a block dispenses an item.
 * 
 * @author UndeadScythes
 */
public class BlockDispense extends ListenerWrapper implements Listener {
    private Entity fireEntity(final Location location, final EntityType entityType, final Vector vector) {
        final Entity entity = location.getWorld().spawnEntity(location, entityType);
        entity.setVelocity(vector);
        return entity;
    }

    @EventHandler
    public final void onEvent(final BlockDispenseEvent event) {
        if(hasFlag(event.getBlock().getLocation(), RegionFlag.DISPENSER)) {
            final BlockFace blockFace = ((Dispenser)event.getBlock().getState().getData()).getFacing();
            Location location = event.getBlock().getRelative(blockFace).getLocation().add(0.5, 0.5, 0.5);
            switch(event.getItem().getType()) {
                case ARROW:
                    event.getBlock().getWorld().spawnArrow(location, VectorUtils.toVector(blockFace), 1.5f, 5);
                    event.setCancelled(true);
                    break;
                case FIREBALL:
                    fireEntity(location, EntityType.FIREBALL, VectorUtils.toVector(blockFace).multiply(5));
                    break;
                case EGG:
                    fireEntity(location, EntityType.EGG, VectorUtils.toVector(blockFace).multiply(1.5));
                    break;
                case SNOW_BALL:
                    fireEntity(location, EntityType.SNOWBALL, VectorUtils.toVector(blockFace).multiply(5));
                    break;
                case EXP_BOTTLE:
                    fireEntity(location, EntityType.THROWN_EXP_BOTTLE, VectorUtils.toVector(blockFace).multiply(2));
                    break;
                case POTION:
                    //fireEntity(location, Potion.fromItemStack(event.getItem()).splash(), toVector(blockFace)); //TODO: Add potion compat for dispensers.
                    break;
                case FIREWORK:
                    final Firework firework = (Firework)fireEntity(location, EntityType.FIREWORK, new Vector(0, 0, 0));
                    firework.setFireworkMeta((FireworkMeta)(event.getItem().getItemMeta()));
                    break;
                case MINECART:
                    if(UDSPlugin.isRail(location.getBlock().getType())) {
                        fireEntity(location, EntityType.MINECART, new Vector(0, 0, 0));
                    }
                    break;
                case BOAT:
                    if(UDSPlugin.isWater(location.getBlock().getRelative(BlockFace.DOWN).getType())) {
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
}
