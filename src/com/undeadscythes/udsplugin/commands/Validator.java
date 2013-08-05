package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;

/**
 * This class can perform various tests and will report errors to a player.
 * In general the methods supplied by this class will return an object as
 * verification that such an object exists or <code>null</code> if no such
 * object can be found.
 * 
 * @author UndeadScythes
 */
public abstract class Validator {
    private SaveablePlayer player;

    protected final SaveablePlayer player() {
        return player;
    }
    
    protected final void setPlayer(final SaveablePlayer player) {
        this.player = player;
    }
    
    /**
     * Checks if a string corresponds to a valid rank.
     * 
     * @param string String to check.
     * @return The rank if it is valid, <code>null</code> otherwise.
     */
    protected final PlayerRank rankExists(final String string) {
        final PlayerRank rank = PlayerRank.getByName(string);
        if(rank == null) player.sendError("You have not entered a valid rank.");
        return rank;
    }
    
    /**
     * Gets the players selected pet.
     * 
     * @return The UUID of the pet or <code>null</code> if the player has no pet selected.
     */
    protected final UUID petSelected() {
        final UUID pet = player.getSelectedPet();
        if(pet == null) player.sendError("Right click a pet while sneaking to select it first.");
        return pet;
    }

    /**
     * Get the players shop.
     * 
     * @return The shop region or <code>null</code> if the player does not own a shop.
     */
    protected final Region ownsAShop() {
        final Region shop = RegionUtils.getRegion(RegionType.SHOP, player.getName() + "shop");
        if(shop == null) player.sendError("You do not own a shop.");
        return shop;
    }

    /**
     * Get the targets shop.
     * 
     * @param target The player.
     * @return The targets shop if they own one, <code>null</code> otherwise.
     */
    protected final Region ownsAShop(final SaveablePlayer target) {
        final Region shop = RegionUtils.getRegion(RegionType.SHOP, target.getName() + "shop");
        if(shop == null) player.sendError("That player does not own a shop.");
        return shop;
    }

    /**
     * Get the players whisperer.
     * 
     * @return The players whisperer, <code>null</code> if the player is not whispering.
     */
    protected final SaveablePlayer isWhispering() {
        final SaveablePlayer target = player.getWhisperer();
        if(target == null) player.sendError("There is no one to send this message to.");
        return target;
    }

    /**
     * Get a region by name.
     * 
     * @param regionName Region name.
     * @return The region or <code>null</code> if no region exists by this name.
     */
    protected final Region regionExists(final String regionName) {
        final Region region = RegionUtils.getRegion(regionName);
        if(region == null) player.sendError("No region exists by that name.");
        return region;
    }
    
    protected final Portal portalExists(final String portalName) {
        final Portal portal = PortalUtils.getPortal(portalName);
        if(portal == null) player.sendError("No portal exists by that name.");
        return portal;
    }

    /**
     * Get a region flag by name.
     * 
     * @param name Region flag name.
     * @return The region flag if it exists, <code>null</code> otherwise.
     */
    protected final RegionFlag regionFlagExists(final String name) {
        final RegionFlag flag = RegionFlag.getByName(name);
        if(flag == null) player.sendError("That is not a valid region type.");
        return flag;
    }
    
    /**
     * Get a world flag by name.
     * 
     * @param name World flag name.
     * @return The world flag if it exists, <code>null</code> otherwise.
     */
    protected final WorldFlag worldFlagExists(final String name) {
        final WorldFlag flag = WorldFlag.getByName(name);
        if(flag == null) player.sendError("That is not a valid region type.");
        return flag;
    }

    /**
     * Get a region type by name.
     * 
     * @param name The region type name.
     * @return The region type if it exists, <code>null</code> otherwise.
     */
    protected final RegionType regionTypeExists(final String name) {
        final RegionType type = RegionType.getByName(name);
        if(type == null) player.sendError("That is not a valid region type.");
        return type;
    }

    /**
     * Get the players currently pending request.
     * 
     * @return The players current request if it exists, <code>null</code> otherwise.
     */
    protected final Request hasRequest() {
        final Request request = UDSPlugin.getRequest(player);
        if(request == null) player.sendError("You have no pending requests.");
        return request;
    }

    /**
     * Get the players home region.
     * 
     * @return The players home region if it exists, <code>null</code> otherwise.
     */
    protected final Region hasHome() {
        final Region home = RegionUtils.getRegion(RegionType.HOME, player.getName() + "home");
        if(home == null) player.sendError("You do not have a home.");
        return home;
    }

    /**
     * Get a direction by name.
     * 
     * @param dir The direction name.
     * @return The direction if it exists, <code>null</code> otherwise.
     */
    protected final Direction directionExists(final String dir) {
        final Direction direction = Direction.getByName(dir);
        if(direction == null) player.sendError("That is not a valid direction.");
        return direction;
    }

    /**
     * Get a cardinal direction by name.
     * 
     * @param dir The direction name.
     * @return The cardinal direction if it exists, <code>null</code> otherwise.
     */
    protected final Direction isCardinalDirection(final String dir) {
        final Direction direction = directionExists(dir);
        if(direction == null) return null;
        if(direction.isCardinal()) return direction;
        player.sendError("You must choose a cardinal direction.");
        return null;
    }

    /**
     * Get a clan by name.
     * 
     * @param name Clan name.
     * @return The clan if it exists, <code>null</code> otherwise.
     */
    protected final Clan clanExists(final String name) {
        final Clan clan = ClanUtils.getClan(name);
        if(clan == null) {
            player.sendError("That clan does not exist.");
        }
        return clan;
    }

    /**
     * Check that a player is in a clan.
     * 
     * @return The player's clan or <code>null</code> if the player is clanless.
     */
    protected final Clan isInClan() {
        final Clan clan = player.getClan();
        if(clan == null) {
            player.sendError("You are not in a clan.");
        }
        return clan;
    }

    /**
     * Check if the players clan has a base.
     * 
     * @param clan The players clan.
     * @return The clan's base or <code>null</code> if the clan does not have a base.
     */
    protected final Region hasClanBase(final Clan clan) {
        final Region region = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base");
        if(region == null) {
            player.sendError("Your clan does not have a base.");
        }
        return region;
    }

    /**
     * Match an online player by name.
     * 
     * @param name Player name.
     * @return The player if they are online, <code>null</code> otherwise.
     */
    protected final SaveablePlayer matchesOtherPlayer(final String name) {
        final SaveablePlayer target = matchesPlayer(name);
        if(target != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * Check that a material exists.
     * 
     * @param string Material to check.
     * @return The material if it exists, <code>null</code> otherwise.
     */
    protected final ItemStack itemExists(final String string) {
        Material material;
        String matString = string.toUpperCase();
        byte data = 0;
        if(string.contains(":")) {
            if(string.split(":")[1].matches("[0-9][0-9]*")) {
                data = Byte.parseByte(string.split(":")[1]);
            } else {
                player.sendError("That is not a valid data value.");
                return null;
            }
            matString = string.split(":")[0].toUpperCase();
        }
        if(matString.matches("[0-9][0-9]*")) {
            material = Material.getMaterial(Integer.parseInt(matString));
        } else {
            material = Material.getMaterial(matString);
        }
        if(material == null) {
            final ShortItem item = ShortItem.getByName(matString);
            if(item == null) {
                player.sendError("That is not a valid item.");
                return null;
            } else {
                return item.toItemStack();
            }
        } else {
            final MaterialData item = new MaterialData(material, data);
            return item.toItemStack(1);
        }
    }

    /**
     * Check if an enchantment exists.
     * 
     * @param enchant Enchantment name.
     * @return The enchantment if it exists, <code>null</code> otherwise.
     */
    protected final Enchantment enchantmentExists(final String enchant) {
        final Enchantment enchantment = Enchantment.getByName(enchant.toUpperCase());
        if(enchantment == null) {
            player.sendError("That is not a valid enchantment.");
            return null;
        } else {
            return enchantment;
        }
    }

    /**
     * Get the first city region the player is currently in.
     * 
     * @return The first city region the player is in or <code>null</code> if none found.
     */
    protected final Region isInCity() {
        final Region region = player.getCurrentRegion(RegionType.CITY);
        if(region == null) {
            player.sendError("You are not in a city.");
        }
        return region;
    }

    /**
     * Get the shop the player is standing in.
     * 
     * @return The shop the player is standing in or <code>null</code>.
     */
    protected final Region isInShop() {
        final Region shop = player.getCurrentRegion(RegionType.SHOP);
        if(shop == null) {
            player.sendError("You must be stood inside a shop to buy it.");
        }
        return shop;
    }

    /**
     * Get the target players home.
     * 
     * @param target Target player.
     * @return The target players home if it exists, <code>null</code> otherwise.
     */
    protected final Region hasHome(final SaveablePlayer target) {
        final Region home = RegionUtils.getRegion(RegionType.HOME, target.getName() + "home");
        if(home == null) {
            player.sendError("That player does not have a home.");
        }
        return home;
    }

    /**
     * Checks if a city exists by this name.
     * 
     * @param cityName City to check.
     * @return The city if it exists, <code>null</code> otherwise.
     */
    protected final Region matchesCity(final String cityName) {
        final Region city = RegionUtils.matchRegion(RegionType.CITY, cityName);
        if(city == null) {
            player.sendError("No city exists by that name.");
        }
        return city;
    }

    /**
     * Checks first if the city exists then that the player is the mayor of the city.
     * 
     * @param cityName City to check.
     * @return The city if both the city exists and the player is the mayor, <code>null</code> otherwise.
     */
    protected final Region ownsCity(final String cityName) {
        final Region city = matchesCity(cityName);
        if(city != null && !city.isOwnedBy(player)) {
            player.sendError("You are not the mayor of that city.");
            return null;
        }
        return city;
    }

    /**
     * Match an online player that is not this player.
     * 
     * @param name Player name.
     * @return The player if it matched and was not this player, <code>null</code> otherwise.
     */
    protected final SaveablePlayer matchesOtherOnlinePlayer(final String name) {
        final SaveablePlayer target = matchesOnlinePlayer(name);
        if(target != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * Matches a partial player name to the first player it finds.
     * 
     * @param partial Partial player name.
     * @return The first player matched or <code>null</code> if no players matched.
     */
    protected final SaveablePlayer matchesPlayer(final String partial) {
        final String lowPartial = partial.toLowerCase();
        SaveablePlayer target = PlayerUtils.getOnlinePlayer(lowPartial);
        if(target != null) {
            return target;
        } else {
            for(SaveablePlayer test : PlayerUtils.getOnlinePlayers()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = PlayerUtils.matchOnlinePlayer(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(SaveablePlayer test : PlayerUtils.getOnlinePlayers()) {
                    if(test.getNick().toLowerCase().contains(lowPartial)) {
                        return test;
                    }
                }
                target = PlayerUtils.getPlayer(lowPartial);
                if(target != null) {
                    return target;
                } else {
                    for(SaveablePlayer test : PlayerUtils.getPlayers()) {
                        if(test.getNick().equalsIgnoreCase(lowPartial)) {
                            return test;
                        }
                    }
                    target = PlayerUtils.matchPlayer(lowPartial);
                    if(target != null) {
                        return target;
                    } else {
                        for(SaveablePlayer test : PlayerUtils.getPlayers()) {
                            if(test.getNick().toLowerCase().contains(lowPartial)) {
                                return test;
                            }
                        }
                    }
                    player.sendError("Cannot find a player by that name.");
                    return null;
                }
            }
        }
    }

    /**
     * Match an online player by name.
     * 
     * @param partial Partial name.
     * @return A player matching the partial name if it can be found, <code>null</code> otherwise.
     */
    protected final SaveablePlayer matchesOnlinePlayer(final String partial) {
        final String lowPartial = partial.toLowerCase();
        SaveablePlayer target = PlayerUtils.getOnlinePlayer(lowPartial);
        if(target!= null) {
            return target;
        } else {
            for(SaveablePlayer test : PlayerUtils.getOnlinePlayers()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = PlayerUtils.matchOnlinePlayer(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(SaveablePlayer test : PlayerUtils.getOnlinePlayers()) {
                    if(test.getNick().contains(lowPartial)) {
                        return test;
                    }
                }
                player.sendError("Cannot find that player.");
                return null;
            }
        }
    }

    /**
     * Matches a warp name from a partial string.
     * 
     * @param partial Partial warp name.
     * @return The warp matched or <code>null</code> if there were no matches.
     */
    protected final Warp warpExists(final String partial) {
        Warp warp = WarpUtils.getWarp(partial);
        if(warp != null) {
            return warp;
        } else {
            warp = WarpUtils.matchWarp(partial);
            if(warp != null) {
                return warp;
            } else {
                player.sendError("That warp point does not exist.");
                return null;
            }
        }
    }

    /**
     * Checks the player has an item in their hand.
     * 
     * @return <code>true</code> if the player is holding an item.
     */
    protected final ItemStack isHoldingItem() {
        final ItemStack item = player.getItemInHand();
        if(!item.getType().equals(Material.AIR)) return item;
        player.sendError("You need an item in your hand.");
        return null;
    }
    
    /**
     * Check that a price is both a valid price and that the player can afford it.
     * 
     * @param amount The price.
     * @return The price if the player can afford it, <code>-1</code> otherwise.
     */
    protected final int canAfford(final String amount) {
        final int cash = isInteger(amount);
        if(cash > -1 && !canAfford(cash)) return -1;
        return cash;
    }
    
    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * 
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    protected final boolean hasPerm(final Perm perm) {
        if(player.hasPermission(perm) || player.isOp()) {
            if(perm.getMode() == null || UDSPlugin.getWorldMode(player.getWorld()).equals(perm.getMode())) {
                return true;
            } else {
                player.sendError("You can't use that command in this world.");
            }
        } else {
            player.sendError("You do not have permission to do that.");
        }
        return false;
    }
    
    /**
     * Checks if a player is online.
     * 
     * @param target Player to check.
     * @return <code>true</code> if the player is online, <code>false</code> otherwise.
     */
    protected final boolean isOnline(final SaveablePlayer target) {
        if(target.isOnline()) {
            return true;
        } else {
            player.sendError("That player is not online.");
            return false;
        }
    }

    /**
     * Check that the target player is not being ignored by the command sender.
     * 
     * @param target Player to check.
     * @return <code>true</code> if the target player is not ignoring the command sender, <code>false</code> otherwise.
     */
    protected final boolean notIgnored(final SaveablePlayer target) {
        if(target.isIgnoringPlayer(player)) {
            player.sendError("This player can't be reached at this time.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that the target player has no requests pending.
     * 
     * @param target The player to check.
     * @return <code>true</code> if the target player has no requests pending, <code>false</code> otherwise.
     */
    protected final boolean noRequests(final SaveablePlayer target) {
        if(UDSPlugin.getRequest(target) != null) {
            player.sendError("That player already has a request pending.");
            return false;
        }
        return true;
    }

    /**
     * Check that the target player is not in jail.
     * 
     * @param target
     * @return <code>true</code> if the target player is not in jail, <code>false</code> otherwise.
     */
    protected final boolean notJailed(final SaveablePlayer target) {
        if(target.isJailed()) {
            player.sendError("You can't do this while that player is in jail.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that this player is not in jail.
     * 
     * @return <code>true</code> if the player is not in jail, <code>false</code> otherwise.
     */
    protected final boolean notJailed() {
        if(player.isJailed()) {
            player.sendError("You cannot do this while you are in jail.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that the player is not pinned due to attacking another player recently.
     * 
     * @return <code>true</code> if the player is not pinned, <code>false</code> otherwise.
     */
    protected final boolean notPinned() {
        if(player.getLastDamageCaused() + Config.PVP_TIME < System.currentTimeMillis()) {
            return true;
        } else {
            player.sendError("You can't do that at this time.");
            return false;
        }
    }

    /**
     * Check that no clan exists by this name.
     * 
     * @param name Clan name.
     * @return <code>true</code> if no clan exists by this name, <code>false</code> otherwise.
     */
    protected final boolean notClan(final String name) {
        if(ClanUtils.clanExists(name)) {
            player.sendError("A clan already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that no warp by this name exists.
     * 
     * @param warpName Warp name to check.
     * @return <code>true</code> if no warp exists by this name, <code>false</code> otherwise.
     */
    protected final boolean notWarp(final String warpName) {
        if(WarpUtils.getWarp(warpName) == null) {
            return true;
        } else {
            player.sendError("A warp already exists called " + warpName + ".");
            return false;
        }
    }

    /**
     * Check that the player is in jail.
     * 
     * @return <code>true</code> if the player is in jail, <code>false</code> otherwise.
     */
    protected final boolean isJailed() {
        if(player.isJailed()) {
            return true;
        } else {
            player.sendError("You are not in jail.");
            return false;
        }
    }

    /**
     * Check that the player is in jail.
     * 
     * @param target Player to check.
     * @return <code>true</code> if the player is in jail, <code>false</code> otherwise.
     */
    protected final boolean isJailed(final SaveablePlayer target) {
        if(target.isJailed()) {
            return true;
        } else {
            player.sendError(target.getNick() + " is not in jail.");
            return false;
        }
    }

    /**
     * Check if the player has the required rank.
     * 
     * @param rank Rank required.
     * @return <code>true</code> if the player has the required rank, <code>false</code> otherwise.
     */
    protected final boolean hasRank(final PlayerRank rank) {
        if(player.hasRank(rank)) {
            return true;
        } else {
            player.sendError("You don't have the rank required to do that.");
            return false;
        }
    }

    /**
     * Check this player is a room mate in this home.
     * 
     * @param home Home to check.
     * @return <code>true</code> if this player is a room mate in this home, <code>false</code> otherwise.
     */
    protected final boolean isRoomie(final Region home) {
        if(home.hasMember(player)) {
            return true;
        } else {
            player.sendError("You are not that players room mate.");
            return false;
        }
    }

    /**
     * Check the target player works in the shop.
     * 
     * @param target Player to check.
     * @param shop Shop to check.
     * @return <code>true</code> if the target player works in the shop, <code>false</code> otherwise.
     */
    protected final boolean isWorker(final SaveablePlayer target, final Region shop) {
        if(shop.hasMember(target)) {
            return true;
        } else {
            player.sendError("That player is not your worker.");
            return false;
        }
    }

    /**
     * Check this player is leader of the clan.
     * 
     * @param clan Clan to check.
     * @return <code>true</code> if this player is clan leader, <code>false</code> otherwise.
     */
    protected final boolean isClanLeader(final Clan clan) {
        if(clan.getLeader().equals(player)) {
            return true;
        } else {
            player.sendError("You must be clan leader to do this.");
            return false;
        }
    }

    /**
     * Check that the item is enchantable.
     * 
     * @param enchantment The enchantment.
     * @param item The item.
     * @return <code>true</code> if the item can be enchanted.
     */
    protected final boolean isEnchantable(final Enchantment enchantment, final ItemStack item) {
        if(enchantment.canEnchantItem(item)) return true;
        player.sendError("You cannot use that enchantment on that item.");
        return false;
    }

    /**
     * Check if the player is near monsters.
     * 
     * @return <code>true</code> if the player is not near any hostile monsters.
     */
    protected final boolean notNearMobs() {
        final List<Entity> entities = player.getNearbyEntities(10, 3, 10);
        for(Entity entity : entities) {
            if(UDSPlugin.isHostileMob(entity.getType())) {
                player.sendError("You cannot do that now, there are monsters nearby.");
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the target player is marked as AFK.
     * 
     * @param target Target player.
     * @return Is target player marked as AFK.
     */
    protected final boolean notAfk(final SaveablePlayer target) {
        if(!target.isAfk()) return true;
        player.sendNormal("That player is currently AFK.");
        return false;
    }

    /**
     * Check that the player can send a request.
     * 
     * @param target The requestee.
     * @return <code>true</code> if the player can send a request.
     */
    protected final boolean canRequest(final SaveablePlayer target) {
        return noRequests(target) && notIgnored(target) && notAfk(target);
    }

    /**
     * Check that the player can teleport.
     * 
     * @return <code>true</code> if the player is free to teleport.
     */
    protected final boolean canTeleport() {
        return notPinned() && notJailed();
    }

    protected final boolean hasUndo(EditSession session) {
        if(session.hasUndo()) return true;
        player.sendError("You have nothing to undo.");
        return false;
    }

    /**
     * Check that the enchantment can accept the level.
     * 
     * @param enchantment Enchantment.
     * @param level Level to check.
     * @return <code>true</code> if the enchantment can accept the level, <code>false</code> otherwise.
     */
    protected final boolean goodEnchantLevel(final Enchantment enchantment, final int level) {
        if(level <= enchantment.getMaxLevel()) return true;
        player.sendError("The level you have chosen is too high.");
        return false;
    }

    /**
     * Check if the player is in a chat room.
     * 
     * @return <code>true</code> if the player is in a chat room, <code>false</code> otherwise.
     */
    protected final ChatRoom inChatRoom() {
        final ChatRoom chatRoom = player.getChatRoom();
        if(chatRoom != null) return chatRoom;
        player.sendError("You are not in any private chat rooms.");
        return null;
    }

    /**
     * Check that the player has two World Edit points selected.
     * 
     * @param session The players session.
     * @return <code>true</code> if the session has two points, <code>false</code> otherwise.
     */
    protected final boolean hasTwoPoints(final EditSession session) {
        if(session.getV1() == null || session.getV2() == null) {
            player.sendError("You need to select two points.");
            return false;
        }
        return true;
    }

    /**
     * Check that the player does not own a shop.
     * 
     * @return <code>true</code> if the players does not own a shop.
     */
    protected final boolean noShop() {
        if(!RegionUtils.regionExists(RegionType.SHOP, player.getName() + "shop")) return true;
        player.sendError("You already own a shop.");
        return false;
    }

    /**
     * Check that the target player is banned.
     * 
     * @param target Target player.
     * @return <code>true</code> if the target is banned.
     */
    protected final boolean isBanned(final SaveablePlayer target) {
        if(target.isBanned()) return true;
        player.sendError("That player is not banned.");
        return false;
    }

    /**
     * Check that the player is homeless.
     * 
     * @return <code>true</code> if the player does not have a home.
     */
    protected final boolean noHome() {
        if(!RegionUtils.regionExists(RegionType.HOME, player.getName() + "home")) return true;
        player.sendError("You already have a home.");
        return false;
    }

    /**
     * Check that the clan has no base region.
     * 
     * @param clan The clan to check.
     * @return <code>true</code> if the clan does not have a base.
     */
    protected final boolean noBase(final Clan clan) {
        if(!RegionUtils.regionExists(RegionType.BASE, clan.getName() + "base")) return true;
        player.sendError("Your clan already has a base.");
        return false;
    }

    /**
     * Check that a player is clanless.
     * 
     * @return <code>true</code> if the player is clanless, <code>false</code> otherwise.
     */
    protected final boolean noClan() {
        if(player.getClan() == null) {
            return true;
        } else {
            player.sendError("You are already in a clan.");
            return false;
        }
    }

    /**
     * Check that the target player is in the clan.
     * 
     * @param player Target player.
     * @param clan Clan to check.
     * @return <code>true</code> if the player is a member of the clan, <code>false</code> otherwise.
     */
    protected final boolean isInClan(final SaveablePlayer player, final Clan clan) {
        if(player.getClan().equals(clan)) {
            return true;
        } else {
            player.sendError("That player is not in your clan.");
            return false;
        }
    }

    /**
     * Check if a string has bad words in it.
     * 
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    protected final boolean noBadLang(final String string) {
        if(Censor.noCensor(string)) {
            return true;
        } else {
            player.sendError("You can't use bad words here.");
            return false;
        }
    }

    /**
     * Check the shop is not owned by another player.
     * 
     * @param shop Shop to check.
     * @return <code>true</code> if the shop is empty, <code>false</code> otherwise.
     */
    protected final boolean isEmptyShop(final Region shop) {
        if(shop.getOwner() == null) {
            return true;
        } else {
            player.sendError("Somebody already owns this shop.");
            return false;
        }
    }

    /**
     * Check the target player is a room mate in the home.
     * 
     * @param target Player to check.
     * @param home Home region to check.
     * @return <code>true</code> if the player is a room mate, <code>false</code> otherwise.
     */
    protected final boolean isRoomie(final SaveablePlayer target, final Region home) {
        if(home.hasMember(target)) {
            return true;
        } else {
            player.sendError("That player is not your room mate.");
            return false;
        }
    }

    /**
     * Check the target player is in the home region.
     * 
     * @param target Target player.
     * @param home Home region.
     * @return <code>true</code> if the player is in the home region, <code>false</code> otherwise.
     */
    protected final boolean isInHome(final SaveablePlayer target, final Region home) {
        if(target.getLocation().toVector().isInAABB(home.getV1(), home.getV2())) {
            return true;
        } else {
            player.sendError("That player is not in your home.");
            return false;
        }
    }

    /**
     * Checks that no region already exists with a given name.
     * 
     * @param name Name to check.
     * @return <code>true</code> if no region already exists with the given name, <code>false</code> otherwise.
     */
    protected final boolean noRegionExists(final String name) {
        if(RegionUtils.regionExists(name)) {
            player.sendError("A protected final area already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that the player is not the mayor of the city region.
     * 
     * @param city City region.
     * @return <code>true</code> if the player is the mayor of the city, <code>false</code> otherwise.
     */
    protected final boolean notMayor(final Region city) {
        if(city.isOwnedBy(player)) {
            player.sendError("You cannot do that while you are the mayor.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks that a region has no overlaps with any other regions.
     * 
     * @param region Region to check for overlaps.
     * @return <code>true</code> if there are no overlaps with other regions, <code>false</code> otherwise.
     */
    protected final boolean noOverlaps(final Region region) {
        for(Region test : RegionUtils.getRegions()) {
            if(test != region && test.hasOverlap(region)) {
                player.sendError("You cannot do that here, you are too close to another protected final area.");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that a player is not already engaged in a duel with another player.
     * 
     * @param target Player to check.
     * @return <code>true</code> if the player is not duelling, <code>false</code> otherwise.
     */
    protected final boolean notDueling(final SaveablePlayer target) {
        if(target.isDuelling()) {
            player.sendNormal("That player is already dueling someone else.");
            return false;
        } else {
            return true;
        }
    }

    protected final boolean selectionIs2D(final EditSession session) {
        if(hasTwoPoints(session)) {
            final int x = session.getV1().getBlockX() - session.getV2().getBlockX();
            final int y = session.getV1().getBlockY() - session.getV2().getBlockY();
            final int z = session.getV1().getBlockZ() - session.getV2().getBlockZ();
            if(x != 0 && y != 0 && z != 0) {
                player.sendError("The edit points must form a square, not a cube.");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected final boolean outRanks(final SaveablePlayer target) {
        if(!player.outRanks(target)) {
            player.sendMessage("You do not out rank this player.");
            return false;
        }
        return true;
    }

    /**
     * Checks if the player can afford a certain cost.
     * 
     * @param cost The cost to check.
     * @return <code>true</code> if the player can afford the cost, <code>false</code> otherwise.
     */
    protected final boolean canAfford(final int cost) {
        if(player.canAfford(cost)) {
            return true;
        } else {
            player.sendError("You do not have enough money to do that.");
            return false;
        }
    }

    /**
     * Check that the player is not targeting themselves.
     * 
     * @param target Target to check.
     * @return <code>true</code> if target and this player are distinct.
     */
    protected final boolean notSelf(final SaveablePlayer target) {
        if(target.equals(player)) {
            player.sendError("You cannot use that command on yourself.");
            return false;
        } else {
            return true;
        }
    }
    
    protected final boolean notPortal(final String name) {
        if(PortalUtils.portalExists(name)) {
            player.sendError("A portal already exists with that name.");
            return false;
        }
        return true;
    }
    
    /**
     * Checks to see if a string is a positive number.
     * 
     * @param number String to check.
     * @return The number if it was one, -1 otherwise.
     */
    protected final int isInteger(final String number) {
        if(number.matches(UDSPlugin.INT_REGEX)) {
            return Integer.parseInt(number);
        } else {
            player.sendError("The number you entered was invalid.");
            return -1;
        }
    }


}

