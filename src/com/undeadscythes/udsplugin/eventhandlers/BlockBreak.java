package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
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
    private final static List<String> SPECIAL_SIGNS = new ArrayList<String>(Arrays.asList(Color.SIGN + "[CHECKPOINT]", Color.SIGN + "[MINECART]", Color.SIGN + "[PRIZE]", Color.SIGN + "[ITEM]", Color.SIGN + "[WARP]", Color.SIGN + "[SPLEEF]"));

    @EventHandler
    public void onEvent(final BlockBreakEvent event) {
        final SaveablePlayer player = UDSPlugin.getPlayers().get(event.getPlayer().getName());
        if(player.isJailed()) {
            event.setCancelled(true);
        } else if(!player.canBuildHere(event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(Message.CANT_BUILD_HERE);
        } else if(isSpecialSign(event.getBlock()) && !player.isSneaking()) {
            event.setCancelled(true);
            player.sendMessage(Color.ERROR + "Sneak while punching if you want to break this block.");
        } else if(event.getBlock().getType().equals(Material.MOB_SPAWNER)) {
            player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(Config.HALF_BLOCK), new ItemStack(Material.MOB_SPAWNER));
            event.setExpToDrop(Config.spawnerEXP);
        } else if(event.getBlock().getType().equals(Material.SNOW_BLOCK)) {
            final ItemStack item = new ItemStack(player.getItemInHand());
            for(Region arena : UDSPlugin.getArenas().values()) {
                if(arena.getData().equals("spleef")) {
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
        while(blockUp.getType().equals(Material.LOG) && player.canBuildHere(block.getLocation())) {
            blockUp.breakNaturally();
            player.getItemInHand().setDurability((short)(player.getItemInHand().getDurability() - 1));
            blockUp = blockUp.getRelative(BlockFace.UP);
        }
    }
}
