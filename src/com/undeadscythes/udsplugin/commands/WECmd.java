package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * WorldEdit-like tools.
 * @author UndeadScythes
 */
public class WECmd extends AbstractPlayerCommand {
    /**
     * @inheritDoc
     */
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        player.sendMessage(Color.MESSAGE + "This command has not been re-written yet, sorry for the inconvienience.");
    }
//        final String senderName = commandSender.getName();
//        final Server server = Bukkit.getServer();
//        final Player sender = server.getPlayer(senderName);
//        if(args.length != 0) {
//            final String subCommand = args[0];
//            if(subCommand.equalsIgnoreCase("set")) {
//                if(sender.hasPermission("udsplugin.we.set")) {
//                    if(args.length == 2) {
//                        final ItemStack item = ItemUtils.findItem(args[1]);
//                        if(item != null) {
//                            set(sender, item);
//                        } else sender.sendMessage(UDSMessage.NO_BLOCK);
//                    } else sender.sendMessage(UDSMessage.BAD_ARGS);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("undo")) {
//                if(sender.hasPermission("udsplugin.we.undo")) {
//                    undo(sender);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("replace")) {
//                if(sender.hasPermission("udsplugin.we.replace")) {
//                    final UDSPlayer senderPlayer = UDSPlugin.getPlayers().get(senderName);
//                    final Vector vector1 = senderPlayer.getEditPoint1();
//                    final Vector vector2 = senderPlayer.getEditPoint2();
//                    if(vector1 != null && vector2 != null) {
//                        final UDSConfig config = UDSPlugin.getUDSConfig();
//                        if(RegionUtils.findVolume(vector1, vector2) <= config.getEditRange()) {
//                            if(args.length == 3) {
//                                final ItemStack itemFrom = ItemUtils.findItem(args[1]);
//                                final ItemStack itemTo = ItemUtils.findItem(args[2]);
//                                replace(sender, itemFrom, itemTo);
//                            } else sender.sendMessage(UDSMessage.BAD_ARGS);
//                        } else sender.sendMessage(UDSMessage.BAD_VOLUME);
//                    } else sender.sendMessage(UDSMessage.NO_POINTS);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("regen")) {
//                if(sender.hasPermission("udsplugin.we.regen")) {
//                    regen(sender);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("drain")) {
//                if(sender.hasPermission("udsplugin.we.drain")) {
//                    if(args.length < 3) {
//                        int range = 5;
//                        if(args.length == 2 && args[1].matches("[0-9][0-9]*")) {
//                            range = Integer.parseInt(args[1]);
//                        }
//                        if(range <= UDSPlugin.getUDSConfig().getDrainRange()) {
//                            drain(sender, range);
//                        } else sender.sendMessage(UDSMessage.OUT_OF_RANGE);
//                    } else sender.sendMessage(UDSMessage.BAD_ARGS);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equals("move")) {
//                if(sender.hasPermission("udsplugin.we.move")) {
//                    if(args.length == 3) {
//                        if(args[2].matches("[0-9][0-9]*")) {
//                            final int distance = Integer.parseInt(args[2]);
//                            if(distance <= plugin.getConfig().getInt("range.move")) {
//                                final UDSPlayer senderPlayer = PlayerUtils.getUDS(sender);
//                                final Vector v1 = senderPlayer.getEditPoint1();
//                                final Vector v2 = senderPlayer.getEditPoint2();
//                                if(v1 != null && v2 != null) {
//                                    if(RegionUtils.findVolume(v1, v2) <= UDSPlugin.getUDSConfig().getEditRange()) {
//                                        Direction direction = Direction.getDirection(args[1]);
//                                        if(direction != Direction.NEUTRAL) {
//                                            move(sender, distance, direction);
//                                        } else sender.sendMessage(UDSMessage.BAD_DIRECTION);
//                                    } else sender.sendMessage(UDSMessage.BAD_VOLUME);
//                                } else sender.sendMessage(UDSMessage.NO_POINTS);
//                            } else sender.sendMessage(UDSMessage.BAD_MOVE);
//                        } else sender.sendMessage(UDSMessage.NO_NUMBER);
//                    } else sender.sendMessage(UDSMessage.BAD_ARGS);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("ext")) {
//                if(sender.hasPermission("udsplugin.we.ext")) {
//                    if (args.length < 3) {
//                        final UDSConfig config = UDSPlugin.getUDSConfig();
//                        int radius = 5;
//                        if(args.length == 2 && args[1].matches("[0-9][0-9]*")) {
//                            radius = Integer.parseInt(args[1]);
//                        }
//                        if (radius <= config.getDrainRange()) {
//                            final Location location = sender.getLocation();
//                            final Vector vector1 = new Vector(location.getX() + (radius), location.getY() + (radius), location.getZ() + (radius));
//                            final Vector vector2 = new Vector(location.getX() - (radius), location.getY() - (radius), location.getZ() - (radius));
//                            final Vector min = VectorUtils.findMin(vector1, vector2);
//                            final Vector max = VectorUtils.findMax(vector1, vector2);
//                            final LinkedList<UDSBlock> blocks = new LinkedList();
//                            int count = 0;
//                            final World world = server.getWorld(config.getWorldName());
//                            for(int ix = (int) min.getX(); ix <= (int) max.getX(); ix++) {
//                                for(int iy = (int) min.getY(); iy <= (int) max.getY(); iy++) {
//                                    for(int iz = (int) min.getZ(); iz <= (int) max.getZ(); iz++) {
//                                        blocks.add(new UDSBlock(world.getBlockAt(ix, iy, iz)));
//                                        final Block block = world.getBlockAt(ix, iy, iz);
//                                        if(block.getType() == Material.FIRE) {
//                                            block.setType(Material.AIR);
//                                            count++;
//                                        }
//                                    }
//                                }
//                            }
//                            UDSPlugin.getPlayers().get(sender.getName()).saveUndo(min, max, blocks);
//                            sender.sendMessage(Color.MESSAGE + "Extinguished " + count + " fires.");
//                        } else sender.sendMessage(UDSMessage.OUT_OF_RANGE);
//                    } else sender.sendMessage(UDSMessage.BAD_ARGS);
//                }
//            } else if(subCommand.equalsIgnoreCase("copy")) {
//                if(sender.hasPermission("udsplugin.we.copy")) {
//                    copy(sender);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("paste")) {
//                if(sender.hasPermission("udsplugin.we.paste")) {
//                    paste(sender);
//                } else sender.sendMessage(UDSMessage.NO_PERM);
//            } else if(subCommand.equalsIgnoreCase("help")) {
//                sender.performCommand("help we");
//            } else sender.sendMessage(UDSMessage.BAD_COMMAND);
//        } else {
//            sender.performCommand("help we");
//        }
//        return true;
//    }
//
//    public void drain(Player player, int range) {
//        final Location location = player.getLocation();
//        final Vector v1 = new Vector(location.getX() + (range), location.getY() + (range), location.getZ() + (range));
//        final Vector v2 = new Vector(location.getX() - (range), location.getY() - (range), location.getZ() - (range));
//        final Vector min = VectorUtils.findMin(v1, v2);
//        final Vector max = VectorUtils.findMax(v1, v2);
//        final LinkedList<UDSBlock> blocks = new LinkedList();
//        int count = 0;
//        final World world = player.getWorld();
//        WEUtils.forceSession(player).save(new Cuboid(world, v1, v2));
//        for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
//            for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
//                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
//                    blocks.add(new UDSBlock(world.getBlockAt(x, y, z)));
//                    final Block block = world.getBlockAt(x, y, z);
//                    if(block.getType() == Material.STATIONARY_LAVA
//                    || block.getType() == Material.STATIONARY_WATER
//                    || block.getType() == Material.WATER
//                    || block.getType() == Material.LAVA) {
//                        if(x == min.getX()
//                        || x == max.getX()
//                        || z == max.getZ()
//                        || z == max.getZ()) {
//                            if(block.getType() == Material.LAVA) {
//                                block.setType(Material.STATIONARY_LAVA);
//                            } else {
//                                block.setType(Material.STATIONARY_WATER);
//                            }
//                        } else {
//                            block.setType(Material.AIR);
//                            count++;
//                        }
//                    }
//                }
//            }
//        }
//        player.sendMessage(Color.MESSAGE + "Drained " + count + " blocks.");
//    }
//
//    public void set(Player player, ItemStack item) {
//        final UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//        final Vector v1 = udsPlayer.getEditPoint1();
//        final Vector v2 = udsPlayer.getEditPoint2();
//        if(v1 != null && v2 != null) {
//            final UDSConfig config = UDSPlugin.getUDSConfig();
//            final int volume = RegionUtils.findVolume(v1, v2);
//            if(volume <= config.getEditRange()) {
//                if(item.getType().isBlock()) {
//                    WESession session = WEUtils.getSession(player);
//                    if(session == null) {
//                        session = new WESession(player);
//                        WEUtils.addSession(session);
//                    }
//                    final Vector min = VectorUtils.findMin(v1, v2);
//                    final Vector max = VectorUtils.findMax(v1, v2);
//                    final World world = player.getWorld();
//                    session.save(new Cuboid(world, player));
//                    final byte itemData = item.getData().getData();
//                    final int itemId = item.getTypeId();
//                    for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
//                        for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
//                            for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
//                                world.getBlockAt(x, y, z).setTypeIdAndData(itemId, itemData, true);
//                            }
//                        }
//                    }
//                    player.sendMessage(Color.MESSAGE + "Set " + volume + " blocks.");
//                } else {
//                    player.sendMessage(UDSMessage.BAD_BLOCK);
//                }
//            } else {
//                player.sendMessage(UDSMessage.BAD_VOLUME);
//            }
//        } else {
//            player.sendMessage(UDSMessage.NO_POINTS);
//        }
//    }
//
//    public void undo(Player player) {
//        WESession session = WEUtils.getSession(player);
//        if(session != null && session.hasUndo()) {
//            WEUtils.put(session.load());
//        } else {
//            player.sendMessage(UDSMessage.NO_UNDO);
//        }
//    }
//
//    public void replace(Player player, ItemStack block, ItemStack replacement) {
//        final World world = player.getWorld();
//        UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//        final Vector min = VectorUtils.findMin(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        final Vector max = VectorUtils.findMax(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        WEUtils.forceSession(player).save(new Cuboid(world, min, max));
//        int count = 0;
//        for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
//            for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
//                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
//                    if(world.getBlockAt(x, y, z).getTypeId() == block.getTypeId() &&
//                        world.getBlockAt(x, y, z).getData() == block.getData().getData()) {
//                        world.getBlockAt(x, y, z).setTypeIdAndData(replacement.getTypeId(), replacement.getData().getData(), true);
//                        count++;
//                    }
//                }
//            }
//        }
//        player.sendMessage(Color.MESSAGE + "Replaced " + count + " blocks.");
//    }
//
//    public void move(Player player, int distance, Direction direction) {
//        if(direction == Direction.UP || direction == Direction.EAST || direction == Direction.SOUTH) {
//            UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//            final Vector min = VectorUtils.findMin(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//            final Vector max = VectorUtils.findMax(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//            final World world = player.getWorld();
//            Cuboid cuboid = new Cuboid(world, min, max);
//            int volume = RegionUtils.findVolume(min, max);
//            WEUtils.forceSession(player).save(new Cuboid(world, min, max.clone().add(direction.toVector().clone().multiply(distance))));
//            WEUtils.put(world, min, max, Material.AIR);
//            min.add((direction.toVector().clone().multiply(distance)));
//            max.add((direction.toVector().clone().multiply(distance)));
//            WEUtils.put(world, cuboid.getBlocks(), min, max);
//            player.sendMessage(Color.MESSAGE + "Moved " + volume + " blocks.");
//        } else if(direction == Direction.DOWN || direction == Direction.WEST || direction == Direction.NORTH) {
//            UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//            final Vector min = VectorUtils.findMin(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//            final Vector max = VectorUtils.findMax(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//            final World world = player.getWorld();
//            Cuboid cuboid = new Cuboid(world, min, max);
//            int volume = RegionUtils.findVolume(min, max);
//            WEUtils.forceSession(player).save(new Cuboid(world, min.clone().add((direction.toVector().clone().multiply(distance))), max));
//            WEUtils.put(world, min, max, Material.AIR);
//            min.add((direction.toVector().clone().multiply(distance)));
//            max.add((direction.toVector().clone().multiply(distance)));
//            WEUtils.put(world, cuboid.getBlocks(), min, max);
//            player.sendMessage(Color.MESSAGE + "Moved " + volume + " blocks.");
//        } else {
//            player.sendMessage(UDSMessage.NO_DIRECTION);
//        }
//    }
//
//    public void copy(Player player) {
//        UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//        final Vector min = VectorUtils.findMin(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        final Vector max = VectorUtils.findMax(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        final World world = player.getWorld();
//        WEUtils.forceSession(player).copy(new Cuboid(world, player));
//        player.sendMessage(Color.MESSAGE + "Copied " + RegionUtils.findVolume(min, max) + " blocks.");
//    }
//
//    public void paste(Player player) {
//        UDSPlayer udsPlayer = PlayerUtils.getUDS(player);
//        Cuboid clipboard = WEUtils.forceSession(player).get();
//        Location location = player.getLocation();
//        int dX = location.getBlockX() - clipboard.getPOV().getBlockX();
//        int dY = location.getBlockY() - clipboard.getPOV().getBlockY();
//        int dZ = location.getBlockZ() - clipboard.getPOV().getBlockZ();
//        Vector dV = new Vector(dX, dY, dZ);
//        WEUtils.forceSession(player).save(new Cuboid(clipboard.getWorld(), clipboard.getV1().clone().add(dV), clipboard.getV2().clone().add(dV)));
//        Location pastePoint = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
//        WEUtils.put(clipboard, pastePoint);
//        final Vector min = VectorUtils.findMin(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        final Vector max = VectorUtils.findMax(udsPlayer.getEditPoint1(), udsPlayer.getEditPoint2());
//        player.sendMessage(Color.MESSAGE + "Pasted " + RegionUtils.findVolume(min, max) + " blocks.");
//    }
//
//    public void regen(Player player) {
//        final Chunk chunk = player.getLocation().getChunk();
//        World world = player.getWorld();
//        world.regenerateChunk(chunk.getX(), chunk.getZ());
//    }
}
