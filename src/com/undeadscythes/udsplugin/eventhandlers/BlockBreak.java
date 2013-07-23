package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;

/**
 * Fired when a player breaks a block.
 * @author UndeadScythes
 */
public class BlockBreak implements Listener {
    private static final List<String> SPECIAL_SIGNS = new ArrayList<String>(Arrays.asList(Color.SIGN + "[CHECKPOINT]", Color.SIGN + "[MINECART]", Color.SIGN + "[PRIZE]", Color.SIGN + "[ITEM]", Color.SIGN + "[WARP]", Color.SIGN + "[SPLEEF]"));

    @EventHandler
    public final void onEvent(final BlockBreakEvent event) {
        final SaveablePlayer player = PlayerUtils.getOnlinePlayer(event.getPlayer().getName());
        if(player.isJailed()) {
            event.setCancelled(true);
        } else if(!player.canBuildHere(event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(Message.CANT_BUILD_HERE);
        } else if(isSpecialSign(event.getBlock()) && !player.isSneaking()) {
            event.setCancelled(true);
            player.sendMessage(Color.ERROR + "Sneak while punching if you want to break this block.");
        } else if(event.getBlock().getType().equals(Material.MOB_SPAWNER)) {
            player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(UDSPlugin.getHalfBlock()), new ItemStack(Material.MOB_SPAWNER));
            event.setExpToDrop(UDSPlugin.getConfigInt(ConfigRef.SPAWNER_EXP));
        } else if(event.getBlock().getType().equals(Material.SNOW_BLOCK)) {
            final ItemStack item = new ItemStack(player.getItemInHand());
            for(Region arena : UDSPlugin.getRegions(RegionType.ARENA).values()) {
                if(arena.contains(event.getBlock().getLocation())) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    player.setItemInHand(item);
                    player.updateInventory();
                }
            }
        } else if(event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_AXE) && player.getGameMode().equals(GameMode.SURVIVAL)) {
            chopTree(player, event.getBlock());
        }
    }

    private boolean isSpecialSign(final Block block) {
        if(block.getType().equals(Material.WALL_SIGN) || block.getType().equals(Material.SIGN_POST)) {
            final Sign sign = (Sign)block.getState();
            if(SPECIAL_SIGNS.contains(sign.getLine(0))) {
                return true;
            }
        }
        return false;
    }

    private void chopTree(final SaveablePlayer player, final Block block) {
        Block blockUp = block.getRelative(BlockFace.UP);
        while(blockUp.getType().equals(Material.LOG) && player.canBuildHere(blockUp.getLocation()) && single(blockUp)) {
            blockUp.breakNaturally();
            short durability = player.getItemInHand().getDurability();
            durability++;
            if(durability == 1562) {
                player.setItemInHand(new ItemStack(Material.AIR));
                break;
            } else if(block.getLocation().getBlockY() < UDSPlugin.BUILD_LIMIT) {
                player.getItemInHand().setDurability(durability);
                player.updateInventory();
                blockUp = blockUp.getRelative(BlockFace.UP);
            } else {
                break;
            }
        }
    }

    private boolean single(final Block block) {
        boolean northLog = !block.getRelative(BlockFace.NORTH).getType().equals(Material.LOG);
        boolean southLog = !block.getRelative(BlockFace.SOUTH).getType().equals(Material.LOG);
        boolean eastLog = !block.getRelative(BlockFace.EAST).getType().equals(Material.LOG);
        boolean westLog = !block.getRelative(BlockFace.WEST).getType().equals(Material.LOG);
        return northLog && southLog && eastLog && westLog;
    }
}
