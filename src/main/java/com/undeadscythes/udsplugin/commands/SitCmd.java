package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.block.*;

/**
 * @author UndeadScythes
 */
public class SitCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        player.sendNormal("Sorry this command is currently unavailable.");
//        if(!player.isInsideVehicle()) { //TODO: Fix the sit command!
//            final Block target = player.getTargetBlock(null, 3);
//            if((isStairs(target)) && target.getRelative(BlockFace.DOWN).getTypeId() != 0) {
//                //final Item seat = player.getWorld().dropItemNaturally(target.getLocation(), new ItemStack(Material.SNOW_BALL));
//                //seat.setPickupDelay(2147483647);
//                //seat.teleport(target.getLocation().add(0.5, 0.2, 0.5));
//                //seat.setVelocity();
//                final Arrow seat = player.getWorld().spawnArrow(target.getLocation().add(0.5, 0, 0.5), new Vector(), 0, 0);
//                seat.setBounce(false);
//                seat.setShooter(null);
//                final Stairs chair = (Stairs)target.getState().getData();
//                Location view = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
//                if(chair.getDescendingDirection() == BlockFace.NORTH) {
//                    view.setYaw(180);
//                } else if(chair.getDescendingDirection() == BlockFace.EAST) {
//                    view.setYaw(270);
//                } else if(chair.getDescendingDirection() == BlockFace.SOUTH) {
//                    view.setYaw(0);
//                } else {
//                    view.setYaw(90);
//                }
//                player.teleport(view);
//                player.setVehicle(seat);
//
//            }
//        } else {
//            final Entity seat = player.getVehicle();
////            final Block block = player.getWorld().getBlockAt(player.getLocation());
//            seat.eject();
////            if(isStairs(block)) {
////                final Stairs chair = (Stairs)block.getState().getData();
////                if(chair.getDescendingDirection() == BlockFace.NORTH) {
////                    player.teleport(player.getLocation().add(-1, 0, 0));
////                } else if(chair.getDescendingDirection() == BlockFace.EAST) {
////                    player.teleport(player.getLocation().add(0, 0, -1));
////                } else if(chair.getDescendingDirection() == BlockFace.SOUTH) {
////                    player.teleport(player.getLocation().add(1, 0, 0));
////                } else {
////                    player.teleport(player.getLocation().add(0, 0, 1));
////                }
////            } else {
//                player.move(seat.getLocation());
////            }
//            seat.remove();
//        }
    }

    private boolean isStairs(final Block block) {
        return block.getTypeId() == 53 || block.getTypeId() == 67 || block.getTypeId() == 108 || block.getTypeId() == 109 || block.getTypeId() == 114 || block.getTypeId() == 128;
    }
}
