package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class Validator {
    private SaveablePlayer player;

    protected final SaveablePlayer getPlayer() {
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
    protected final PlayerRank getRank(final String string) {
        final PlayerRank rank = PlayerRank.getByName(string);
        if(rank == null) player.sendError("You have not entered a valid rank.");
        return rank;
    }
    
    /**
     * Gets the players selected pet.
     * 
     * @return The UUID of the pet or <code>null</code> if the player has no pet selected.
     */
    protected final UUID getPet() {
        final UUID pet = player.getSelectedPet();
        if(pet == null) player.sendError("Right click a pet while sneaking to select it first.");
        return pet;
    }

    /**
     * Get the players shop.
     * 
     * @return The shop region or <code>null</code> if the player does not own a shop.
     */
    protected final Region getShop() {
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
    protected final Region getShop(final SaveablePlayer target) {
        final Region shop = RegionUtils.getRegion(RegionType.SHOP, target.getName() + "shop");
        if(shop == null) player.sendError("That player does not own a shop.");
        return shop;
    }

    /**
     * Get the players whisperer.
     * 
     * @return The players whisperer, <code>null</code> if the player is not whispering.
     */
    protected final SaveablePlayer getWhisperer() {
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
    protected final Region getRegion(final String regionName) {
        final Region region = RegionUtils.getRegion(regionName);
        if(region == null) player.sendError("No region exists by that name.");
        return region;
    }
    
    protected final Portal getPortal(final String portalName) {
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
    protected final RegionFlag getRegionFlag(final String name) {
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
    protected final WorldFlag getWorldFlag(final String name) {
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
    protected final RegionType getRegionType(final String name) {
        final RegionType type = RegionType.getByName(name);
        if(type == null) player.sendError("That is not a valid region type.");
        return type;
    }

    /**
     * Get the players currently pending request.
     * 
     * @return The players current request if it exists, <code>null</code> otherwise.
     */
    protected final Request getRequest() {
        final Request request = UDSPlugin.getRequest(player);
        if(request == null) player.sendError("You have no pending requests.");
        return request;
    }

    /**
     * Get the players home region.
     * 
     * @return The players home region if it exists, <code>null</code> otherwise.
     */
    protected final Region getHome() {
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
    protected final Direction getDirection(final String dir) {
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
    protected final Direction getCardinalDirection(final String dir) {
        final Direction direction = getDirection(dir);
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
    protected final Clan getClan(final String name) {
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
    protected final Clan getClan() {
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
    protected final Region getBase(final Clan clan) {
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
    protected final SaveablePlayer matchOtherPlayer(final String name) {
        final SaveablePlayer target = matchPlayer(name);
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
    protected final ItemStack getItem(final String string) {
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
    protected final Enchantment getEnchantment(final String enchant) {
        final Enchantment enchantment = Enchantment.getByName(enchant.toUpperCase());
        if(enchantment == null) {
            player.sendError("That is not a valid enchantment.");
            return null;
        } else {
            return enchantment;
        }
    }

    /**
     * Get the first region the player is currently in.
     * 
     * @return The first region the player is in or <code>null</code> if none found.
     */
    protected final Region getCurrentRegion() {
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
    protected final Region getCurrentShop() {
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
    protected final Region getHome(final SaveablePlayer target) {
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
    protected final Region matchCity(final String cityName) {
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
    protected final Region getMunicipality(final String cityName) {
        final Region city = matchCity(cityName);
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
    protected final SaveablePlayer matchOtherOnlinePlayer(final String name) {
        final SaveablePlayer target = matchOnlinePlayer(name);
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
    protected final SaveablePlayer matchPlayer(final String partial) {
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
    protected final SaveablePlayer matchOnlinePlayer(final String partial) {
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
    protected final Warp getWarp(final String partial) {
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


}
