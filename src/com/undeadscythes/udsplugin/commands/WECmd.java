package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;

/**
 * WorldEdit-like tools.
 * @author UndeadScythes
 */
public class WECmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(args.length == 1) {
            if(args[0].equals("undo")) {
                undo();
            } else if(args[0].equals("copy")) {
                copy();
            } else if(args[0].equals("paste")) {
                paste();
            } else if(args[0].equals("regen")) {
                regen();
            } else if(args[0].equals("ext")) {
                ext(10);
            } else if(args[0].equals("drain")) {
                drain(10);
            } else {
                subCmdHelp();
            }
        } else if(args.length == 2) {
            if(args[0].equals("set")) {
                set();
            } else if(args[0].equals("ext")) {
                final int range = parseInt(args[1]);
                if(range > -1) {
                    ext(range);
                }
            } else if(args[0].equals("drain")) {
                final int range = parseInt(args[1]);
                if(range > -1) {
                    drain(range);
                }
            } else {
                subCmdHelp();
            }
        } else if(numArgsHelp(3)) {
            if(args[0].equals("replace")) {
                replace();
            } else if(args[0].equals("move")) {
                move();
            } else {
                subCmdHelp();
            }
        }
    }

    private void drain(final int range) {
        if(hasPerm(Perm.WE_DRAIN)) {
            if(range <= Config.drainRange) {
                final Session session = getSession();
                final Location location = player.getLocation();
                final Vector v1 = new Vector(location.getX() + (range), location.getY() + (range), location.getZ() + (range));
                final Vector v2 = new Vector(location.getX() - (range), location.getY() - (range), location.getZ() - (range));
                final Vector min = Vector.getMinimum(v1, v2);
                final Vector max = Vector.getMaximum(v1, v2);
                int count = 0;
                final World world = player.getWorld();
                session.save(new Cuboid(world, v1, v2, player.getLocation().toVector()));
                for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                            final Block block = world.getBlockAt(x, y, z);
                            if(block.getType() == Material.STATIONARY_LAVA || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.WATER || block.getType() == Material.LAVA) {
                                if(x == min.getX() || x == max.getX() || z == max.getZ() || z == max.getZ()) {
                                    if(block.getType() == Material.LAVA) {
                                        block.setType(Material.STATIONARY_LAVA);
                                    } else {
                                        block.setType(Material.STATIONARY_WATER);
                                    }
                                } else {
                                    block.setType(Material.AIR);
                                    count++;
                                }
                            }
                        }
                    }
                }
                player.sendMessage(Color.MESSAGE + "Drained " + count + " blocks.");
            }
        } else {
            player.sendMessage(Color.ERROR + "Value is out of range.");
        }
    }

    private void set() {
        if(hasPerm(Perm.WE_SET)) {
            final Session session = getSession();
            if(hasTwoPoints(session)) {
                final ItemStack item = getItem(args[1]);
                if(item != null) {
                    final Vector v1 = session.getV1();
                    final Vector v2 = session.getV2();
                    final int volume = session.getVolume();
                    if(volume <= Config.editRange) {
                        if(item.getType().isBlock()) {
                            final Vector min = Vector.getMinimum(v1, v2);
                            final Vector max = Vector.getMaximum(v1, v2);
                            final World world = player.getWorld();
                            session.save(new Cuboid(world, min, max, player.getLocation().toVector()));
                            final byte itemData = item.getData().getData();
                            final int itemId = item.getTypeId();
                            for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                                for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                                    for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                                        world.getBlockAt(x, y, z).setTypeIdAndData(itemId, itemData, true);
                                    }
                                }
                            }
                            player.sendMessage(Color.MESSAGE + "Set " + volume + " blocks.");
                        } else {
                            player.sendMessage(Color.ERROR + "You have selected a bad block.");
                        }
                    } else {
                        player.sendMessage(Color.ERROR + "You have selected too many blocks.");
                    }
                }
            }
        }
    }

    private void undo() {
        final Session session = getSession();
        if(hasUndo(session) && hasPerm(Perm.WE_UNDO)) {
            final Cuboid undo = session.load();
            undo.revert();
            player.sendMessage(Color.MESSAGE + "Undone " + undo.getVolume() + " blocks.");
        }
    }

    private void replace() {
        if(hasPerm(Perm.WE_REPLACE)) {
            final Session session = getSession();
            if(hasTwoPoints(session)) {
                final Vector v1 = session.getV1();
                final Vector v2 = session.getV2();
                if(session.getVolume() <= Config.editRange) {
                    if(args.length == 3) {
                        final ItemStack itemFrom = getItem(args[1]);
                        final ItemStack itemTo = getItem(args[2]);
                        if(itemFrom != null && itemTo != null) {
                            final World world = player.getWorld();
                            final Vector min = Vector.getMinimum(v1, v2);
                            final Vector max = Vector.getMaximum(v1, v2);
                            session.save(new Cuboid(world, min, max, player.getLocation().toVector()));
                            int count = 0;
                            for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                                for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                                    for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                                        if(world.getBlockAt(x, y, z).getTypeId() == itemFrom.getTypeId() &&
                                            world.getBlockAt(x, y, z).getData() == itemFrom.getData().getData()) {
                                            world.getBlockAt(x, y, z).setTypeIdAndData(itemTo.getTypeId(), itemTo.getData().getData(), true);
                                            count++;
                                        }
                                    }
                                }
                            }
                            player.sendMessage(Color.MESSAGE + "Replaced " + count + " blocks.");
                        }
                    }
                } else {
                    player.sendMessage(Color.ERROR + "You have selected too many blocks.");
                }
            }
        }
    }

    private void move() {
        if(hasPerm(Perm.WE_MOVE)) {
            final int distance = parseInt(args[2]);
            if(distance > -1) {
                if(distance <= Config.moveRange) {
                    final Session session = getSession();
                    if(hasTwoPoints(session)) {
                        final Vector v1 = session.getV1();
                        final Vector v2 = session.getV2();
                        if(session.getVolume() <= Config.editRange) {
                            Direction direction = getDirection(args[1]);
                            if(direction != null) {
                                if(direction == Direction.UP || direction == Direction.EAST || direction == Direction.SOUTH) {
                                    final Vector min = Vector.getMinimum(v1, v2);
                                    final Vector max = Vector.getMaximum(v1, v2);
                                    final World world = player.getWorld();
                                    Cuboid cuboid = new Cuboid(world, min, max, player.getLocation().toVector());
                                    int volume = cuboid.getVolume();
                                    session.save(new Cuboid(world, min, max.clone().add(direction.toVector().clone().multiply(distance)), player.getLocation().toVector()));
                                    put(world, min, max, Material.AIR);
                                    min.add(direction.toVector().clone().multiply(distance));
                                    cuboid.place(world, min);
                                    player.sendMessage(Color.MESSAGE + "Moved " + volume + " blocks.");
                                } else if(direction == Direction.DOWN || direction == Direction.WEST || direction == Direction.NORTH) {
                                    final Vector min = Vector.getMinimum(v1, v2);
                                    final Vector max = Vector.getMaximum(v1, v2);
                                    final World world = player.getWorld();
                                    Cuboid cuboid = new Cuboid(world, min, max, player.getLocation().toVector());
                                    int volume = cuboid.getVolume();
                                    session.save(new Cuboid(world, min.clone().add(direction.toVector().clone().multiply(distance)), max, player.getLocation().toVector()));
                                    put(world, min, max, Material.AIR);
                                    min.add((direction.toVector().clone().multiply(distance)));
                                    cuboid.place(world, min);
                                    player.sendMessage(Color.MESSAGE + "Moved " + volume + " blocks.");
                                }
                            }
                        } else player.sendMessage(Color.ERROR + "The area you have selected is too large.");
                    }
                } else {
                    player.sendMessage(Color.ERROR + "That value is out of range.");
                }
            }
        }


    }

    private void copy() {
        final Session session = getSession();
        if(hasTwoPoints(session) && hasPerm(Perm.WE_COPY)) {
            session.setClipboard(new Cuboid(player.getWorld(), session.getV1(), session.getV2(), player.getLocation().toVector()));
            player.sendMessage(Color.MESSAGE + "Copied " + session.getClipboard().getVolume() + " blocks.");
        }
    }

    private void paste() {
        final Session session = getSession();
        if(session.hasClipboard() && hasPerm(Perm.WE_PASTE)) {
            final Cuboid clipboard = session.getClipboard();
            session.save(clipboard.offset(player.getWorld(), player.getLocation().toVector()));
            player.sendMessage(Color.MESSAGE + "Pasted " + clipboard.getVolume() + " blocks.");
        } else {
            player.sendMessage(Color.ERROR + "You have nothing to paste.");
        }
    }

    private void regen() {
        if(hasPerm(Perm.WE_REGEN)) {
            final Chunk chunk = player.getLocation().getChunk();
            final World world = player.getWorld();
            world.regenerateChunk(chunk.getX(), chunk.getZ());
        }
    }

    private void ext(final int range) {
        final Session session = getSession();
        if(hasPerm(Perm.WE_EXT)) {
            if(range <= Config.drainRange) {
                final Location location = player.getLocation();
                final Vector max = new Vector(location.getX() + (range), location.getY() + (range), location.getZ() + (range));
                final Vector min = new Vector(location.getX() - (range), location.getY() - (range), location.getZ() - (range));
                session.save(new Cuboid(player.getWorld(), min, max, player.getLocation().toVector()));
                int count = 0;
                final World world = player.getWorld();
                for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                            final Block block = world.getBlockAt(x, y, z);
                            if(block.getType() == Material.FIRE) {
                                block.setType(Material.AIR);
                                count++;
                            }
                        }
                    }
                }
                player.sendMessage(Color.MESSAGE + "Extinguished " + count + " fires.");
            } else {
                player.sendMessage(Color.ERROR + "That value is out of range.");
            }
        }
    }

    private void put(final World world, final Vector min, final Vector max, final Material material) {
        for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    world.getBlockAt(x, y, z).setType(material);
                }
            }
        }
    }
}
