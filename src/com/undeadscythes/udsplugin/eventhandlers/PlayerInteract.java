package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.timers.MinecartCheck;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;
import org.bukkit.util.Vector;

/**
 * @author UndeadScythes
 */
public class PlayerInteract extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final PlayerInteractEvent event) {
        final Member player = PlayerUtils.getOnlinePlayer(event.getPlayer());
        final Material inHand = player.getItemInHand().getType();
        final Block block = event.getClickedBlock();
        switch(event.getAction()) {
            case LEFT_CLICK_AIR:
                if(inHand == Material.COMPASS && player.hasPerm(Perm.COMPASS)) {
                    compassTeleport(player, false);
                    event.setCancelled(true);
                }
                break;
            case LEFT_CLICK_BLOCK:
                if(inHand == Material.COMPASS && player.hasPerm(Perm.COMPASS)) {
                    compassTeleport(player, false);
                    event.setCancelled(true);
                } else if(inHand == Material.STICK && player.hasPerm(Perm.WAND)) {
                    setPoint1(player, block);
                    event.setCancelled(true);
                } else if((block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) && !player.isSneaking()) {
                    signPunch(player, (Sign)block.getState());
                } else {
                    event.setCancelled(blockLocked(block, player));
                }
                break;
            case RIGHT_CLICK_AIR:
                if(inHand == Material.PAPER && player.hasPerm(Perm.PAPER_COMPLEX)) {
                    sendRegionInfo(player, player.getLocation(), true);
                    event.setCancelled(true);
                } else if(inHand == Material.PAPER && player.hasPerm(Perm.PAPER_SIMPLE)) {
                    sendRegionInfo(player, player.getLocation(), false);
                    event.setCancelled(true);
                } else if(inHand == Material.COMPASS && player.hasPerm(Perm.COMPASS)) {
                    compassTeleport(player, true);
                    event.setCancelled(true);
                } else {
                    try {
                        if(inHand.getId() == player.getPowertoolID()) {
                            activatePowertool(player);
                            event.setCancelled(true);
                        }
                    } catch (NoMetadataSetException ex) {
                        event.setCancelled(expBlocked(player));
                    }
                }
                break;
            case RIGHT_CLICK_BLOCK:
                if(inHand == Material.STICK && player.hasPerm(Perm.WAND)) {
                    setPoint2(player, block);
                    event.setCancelled(true);
                } else if(inHand == Material.PAPER && player.hasPerm(Perm.PAPER_COMPLEX)) {
                    sendRegionInfo(player, block.getLocation(), true);
                    event.setCancelled(true);
                } else if(inHand == Material.PAPER && player.hasPerm(Perm.PAPER_SIMPLE)) {
                    sendRegionInfo(player, block.getLocation(), false);
                    event.setCancelled(true);
                } else if(inHand == Material.COMPASS) {
                    compassTeleport(player, true);
                    event.setCancelled(true);
                } else if(inHand == Material.MONSTER_EGG && block.getType() == Material.MOB_SPAWNER) {
                    setMobSpawner(block, player);
                    event.setCancelled(true);
                } else if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
                    signPunch(player, (Sign)block.getState());
                    event.setCancelled(true);
                } else if(inHand == Material.MINECART && UDSPlugin.isRail(block.getType())) {
                    minecartPlaced(player, block.getLocation());
                    player.setItemInHand(new ItemStack(Material.AIR));
                    event.setCancelled(true);
                } else {
                    try {
                        if(inHand.getId() == player.getPowertoolID()) {
                            activatePowertool(player);
                            event.setCancelled(true);
                        }
                    } catch (NoMetadataSetException ex) {
                        event.setCancelled(blockLocked(block, player) || bonemealBlocked(block, player) || expBlocked(player));
                    }
                }
                break;
            case PHYSICAL:
                event.setCancelled(blockLocked(block, player));
                break;
        }
    }

    private void activatePowertool(final Member player) {
        player.performCommand(player.getPowertool());
    }

    private boolean expBlocked(final Member player) {
        if(UDSPlugin.getWorldMode(player.getWorld()).equals(GameMode.CREATIVE)) {
            if(player.getItemInHand().getType().equals(Material.EXP_BOTTLE) || player.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
                return true;
            }
        }
        return false;
    }

    private boolean bonemealBlocked(final Block block, final Member player) {
        return player.getItemInHand().getType() == Material.INK_SACK && player.getItemInHand().getData().getData() == (byte)15 && !player.canBuildHere(block.getLocation());
    }

    private boolean blockLocked(final Block block, final Member player) {
        final Material material = block.getType();
        if(material == Material.WOODEN_DOOR || material == Material.IRON_DOOR_BLOCK || material == Material.TRAP_DOOR || material == Material.FENCE_GATE) {
            final Location location = block.getLocation();
            if(!player.canBuildHere(location) && hasFlag(location, RegionFlag.LOCK)) {
                if(player.hasPerm(Perm.BYPASS)) {
                    player.sendNormal("Protection bypassed.");
                } else {
                    player.sendError("You can't do that here.");
                    return true;
                }
            }
        } else if(material == Material.STONE_BUTTON || material == Material.LEVER) {
            final Location location = block.getLocation();
            if(!player.canBuildHere(location) && !hasFlag(location, RegionFlag.POWER)) {
                player.sendError("You can't do that here.");
                return true;
            }
        } else if(material == Material.WOOD_PLATE || material == Material.STONE_PLATE || material == Material.TRIPWIRE) {
            final Location location = block.getLocation();
            if(!player.canBuildHere(location) && !hasFlag(location, RegionFlag.POWER)) {
                return true;
            }
        }
        return false;
    }

    private void setMobSpawner(final Block block, final Member player) {
        final byte data = player.getItemInHand().getData().getData();
        final MaterialData material = new MaterialData(Material.MONSTER_EGG, data);
        player.setItemInHand(material.toItemStack(player.getItemInHand().getAmount() - 1));
        ((CreatureSpawner)block.getState()).setSpawnedType(EntityType.fromId(data));
        player.sendNormal("Spawner set.");
    }

    private void setPoint1(final Member player, final Block block) {
        final EditSession session = player.forceSession();
        session.setPoint1(block.getLocation());
        player.sendNormal("Point 1 set.");
        if(session.getV1() != null && session.getV2() != null) {
            player.sendNormal(session.getVolume() + " blocks selected.");
        }
    }

    private void setPoint2(final Member player, final Block block) {
        final EditSession session = player.forceSession();
        session.setPoint2(block.getLocation());
        player.sendNormal("Point 2 set.");
        if(session.getV1() != null && session.getV2() != null) {
            player.sendNormal(session.getVolume() + " blocks selected.");
        }
    }

    private void compassTeleport(final Member player, final boolean phase) {
        if(phase) {
            final List<Block> los = player.getLastTwoTargetBlocks(UDSPlugin.TRANSPARENT_BLOCKS, Config.COMPASS_RANGE);
            if(los.size() == 1) {
                return;
            }
            final Block block = los.get(1);
            if(block.getType().isSolid()) {
                final Location location = block.getRelative(los.get(0).getFace(block)).getLocation();
                player.move(LocationUtils.findSafePlace(location));
            } else {
                player.sendError("No block in range.");
            }
        } else {
            final Block block = player.getTargetBlock(UDSPlugin.TRANSPARENT_BLOCKS, Config.COMPASS_RANGE);
            if(block.getType().isSolid()) {
                player.move(LocationUtils.findSafePlace(block.getLocation()));
            } else {
                player.sendError("No block in range.");
            }
        }
    }

    private void sendRegionInfo(final Member player, final Location location, final boolean verbose) {
        if(verbose) {
            final List<Region> testRegions = RegionUtils.getRegionsHere(location);
            if(testRegions.isEmpty()) {
                player.sendNormal("No regions here.");
            } else {
                for(Region region : testRegions) {
                    region.sendInfo(player);
                }
            }
        } else {
            if(RegionUtils.getRegionsHere(location).isEmpty()) {
                player.sendNormal("No regions here.");
            } else {
                final List<Region> testRegions = RegionUtils.getRegionsHere(location);
                for(Region region : testRegions) {
                    if(region.getOwner() == null) {
                        player.sendNormal("This area is protected.");
                    } else if(region.getOwner().equals(player)) {
                        player.sendNormal("You own this block.");
                    } else if(region.getMembers().contains(player)) {
                        player.sendNormal("Your room mate owns this block.");
                    } else {
                        player.sendNormal("Somebody else owns this block.");
                    }
                }
            }
        }
    }

    private void signPunch(final Member player, final Sign sign) {
        final String cantDoThat = "You can't do that.";
        if(sign.getLine(0).equals(Color.SIGN + "[CHECKPOINT]")) {
            if(player.hasPerm(Perm.CHECK)) {
                player.setCheckPoint(player.getLocation());
                player.sendNormal("Checkpoint set. Use /check to return here. Good luck.");
            } else {
                player.sendError(cantDoThat);
            }
        } else if(sign.getLine(0).equals(Color.SIGN + "[MINECART]")) {
            if(player.hasPerm(Perm.MINECART)) {
                final Location location = sign.getBlock().getLocation();
                if(MinecartCheck.minecartNear(location)) {
                    player.sendNormal("There is a minecart already at the station.");
                } else {
                    player.getWorld().spawnEntity(location.clone().add(0.5, -1, 0.5), EntityType.MINECART);
                }
            } else {
                player.sendError(cantDoThat);
            }
        } else if(sign.getLine(0).equals(Color.SIGN + "[PRIZE]")) {
            if(player.hasPerm(Perm.PRIZE)) {
                if(player.hasClaimedPrize()) {
                    player.claimPrize();
                    final ItemStack item = findItem(sign.getLine(1));
                    item.setAmount(Integer.parseInt(sign.getLine(2)));
                    player.getInventory().addItem(item);
                } else {
                    player.sendError("You have already claimed a prize today.");
                }
            } else {
                player.sendError(cantDoThat);
            }
        } else if(sign.getLine(0).equals(Color.SIGN + "[ITEM]")) {
            if(player.hasPerm(Perm.ITEM)) {
                    final ItemStack item = findItem(sign.getLine(1));
                    final int owned = player.countItems(item);
                    if(owned < Integer.parseInt(sign.getLine(2))) {
                        item.setAmount(Integer.parseInt(sign.getLine(2)) - owned);
                        player.getInventory().addItem(item);
                    } else {
                        player.sendError("You already have enough of that item.");
                    }
            } else {
                player.sendError(cantDoThat);
            }
        } else if(sign.getLine(0).equals(Color.SIGN + "[WARP]")) {
            if(player.hasPerm(Perm.WARP)) {
                final Warp warp = WarpUtils.getWarp(sign.getLine(1));
                if(warp == null) {
                    player.sendError("This warp cannot be found.");
                } else {
                    player.teleport(warp.getLocation());
                }
            } else {
                player.sendError(cantDoThat);
            }
        } else if(sign.getLine(0).equals(Color.SIGN + "[SPLEEF]")) {
            if(player.hasPerm(Perm.SPLEEF)) {
                final Region region = RegionUtils.getRegion(RegionType.ARENA, sign.getLine(1));
                if(region == null) {
                    player.sendError("No region exists to refresh.");
                } else {
                    final Vector min = region.getV1();
                    final Vector max = region.getV2();
                    final World world = region.getWorld();
                    for(int ix = (int) min.getX(); ix <= (int) max.getX(); ix++) {
                        for(int iy = (int) min.getY(); iy <= (int) max.getY(); iy++) {
                            for(int iz = (int) min.getZ(); iz <= (int) max.getZ(); iz++) {
                                world.getBlockAt(ix, iy, iz).setType(Material.SNOW_BLOCK);
                            }
                        }
                    }
                }
            } else {
                player.sendError(cantDoThat);
            }
        }
    }

    private void minecartPlaced(final Member player, final Location location) {
        MinecartCheck.tagMinecart(player, location);
    }
}
