package com.undeadscythes.udsplugin;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;

/**
 * @author UndeadScythes
 */
public abstract class Validator {
    private Member player;

    protected Member player() {
        return player;
    }

    protected void setPlayer(final Member player) {
        this.player = player;
    }

    protected PlayerRank getRank(final String name) {
        final PlayerRank rank = PlayerRank.getByName(name);
        if(rank == null) player.sendError("You have not entered a valid rank.");
        return rank;
    }

    protected UUID getPetId() {
        try {
            return player.getSelectedPet();
        } catch (NoMetadataSetException ex) {
            player.sendError("Right click a pet while sneaking to select it first.");
            return null;
        }
    }

    protected Region getShop() {
        final Region shop = RegionUtils.getRegion(RegionType.SHOP, player.getName() + "shop");
        if(shop == null) player.sendError("You do not own a shop.");
        return shop;
    }

    protected Region getShop(final Member target) {
        final Region shop = RegionUtils.getRegion(RegionType.SHOP, target.getName() + "shop");
        if(shop == null) player.sendError("That player does not own a shop.");
        return shop;
    }

    protected Member getWhisperer() {
        try {
            return PlayerUtils.getOnlinePlayer(player.getWhisperer());
        } catch (NoMetadataSetException ex) {} catch (PlayerNotOnlineException ex) {}
        player.sendError("There is no one to send this message to.");
        return null;
    }

    protected Region getRegion(final String name) {
        final Region region = RegionUtils.getRegion(name);
        if(region == null) player.sendError("No region exists by that name.");
        return region;
    }

    protected Portal getPortal(final String name) {
        final Portal portal = PortalUtils.getPortal(name);
        if(portal == null) player.sendError("No portal exists by that name.");
        return portal;
    }

    protected RegionFlag getRegionFlag(final String name) {
        final RegionFlag flag = RegionFlag.getByName(name);
        if(flag == null) player.sendError("That is not a valid region flag.");
        return flag;
    }

    protected Flag getWorldFlag(final String name) {
        Flag flag = WorldFlag.getByName(name);
        if(flag == null) flag = RegionFlag.getByName(name);
        if(flag == null) player.sendError("That is not a valid world flag.");
        return flag;
    }

    protected RegionType getRegionType(final String name) {
        final RegionType type = RegionType.getByName(name);
        if(type == null) player.sendError("That is not a valid region type.");
        return type;
    }

    protected Request getRequest() {
        final Request request = UDSPlugin.getRequest(player);
        if(request == null) player.sendError("You have no pending requests.");
        return request;
    }

    protected Region getHome() {
        final Region home = RegionUtils.getRegion(RegionType.HOME, player.getName() + "home");
        if(home == null) player.sendError("You do not have a home.");
        return home;
    }

    protected Direction getDirection(final String name) {
        final Direction direction = Direction.getByName(name);
        if(direction == null) player.sendError("That is not a valid direction.");
        return direction;
    }

    protected Direction getCardinalDirection(final String name) {
        final Direction direction = getDirection(name);
        if(direction == null) return null;
        if(direction.isCardinal()) return direction;
        player.sendError("You must choose a cardinal direction.");
        return null;
    }

    protected Clan getClan(final String name) {
        final Clan clan = ClanUtils.getClan(name);
        if(clan == null) {
            player.sendError("That clan does not exist.");
        }
        return clan;
    }

    protected Clan getClan() {
        try {
            return player.getClan();
        } catch (NoMetadataSetException ex) {
            player.sendError("You are not in a clan.");
            return null;
        }
    }

    protected Region getClanBase(final Clan clan) {
        final Region region = RegionUtils.getRegion(RegionType.BASE, clan.getName() + "base");
        if(region == null) {
            player.sendError("Your clan does not have a base.");
        }
        return region;
    }

    protected Member matchOtherPlayer(final String name) {
        final Member target = matchPlayer(name);
        if(target != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    protected ItemStack getItem(final String name) {
        Material material;
        String matString = name.toUpperCase();
        byte data = 0;
        if(name.contains(":")) {
            if(name.split(":")[1].matches("[0-9][0-9]*")) {
                data = Byte.parseByte(name.split(":")[1]);
            } else {
                player.sendError("That is not a valid data value.");
                return null;
            }
            matString = name.split(":")[0].toUpperCase();
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

    protected Enchantment getEnchantment(final String name) {
        final Enchantment enchantment = Enchantment.getByName(name.toUpperCase());
        if(enchantment == null) {
            player.sendError("That is not a valid enchantment.");
            return null;
        } else {
            return enchantment;
        }
    }

    protected Region getCurrentCity() {
        try {
            return player.getCurrentRegion(RegionType.CITY);
        } catch (NoRegionFoundException ex) {
            player.sendError("You are not in a city.");
            return null;
        }
    }

    protected Region getCurrentShop() {
        try {
            return player.getCurrentRegion(RegionType.SHOP);
        } catch (NoRegionFoundException ex) {
            player.sendError("You must be stood inside a shop to buy it.");
            return null;
        }
    }

    protected Region getHome(final Member target) {
        final Region home = RegionUtils.getRegion(RegionType.HOME, target.getName() + "home");
        if(home == null) {
            player.sendError("That player does not have a home.");
        }
        return home;
    }

    protected Region matchCity(final String cityName) {
        final Region city = RegionUtils.matchRegion(RegionType.CITY, cityName);
        if(city == null) {
            player.sendError("No city exists by that name.");
        }
        return city;
    }

    protected Region getCity(final String cityName) {
        final Region city = matchCity(cityName);
        if(city != null && !city.isOwnedBy(player)) {
            player.sendError("You are not the mayor of that city.");
            return null;
        }
        return city;
    }

    protected Member matchOtherOnlinePlayer(final String name) {
        final Member target = matchOnlinePlayer(name);
        if(target != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    protected Member matchPlayer(final String partial) {
        final String lowPartial = partial.toLowerCase();
        Member target;
        try {
            target = PlayerUtils.getOnlinePlayer(lowPartial);
            return target;
        } catch (PlayerNotOnlineException ex) {
            for(Member test : PlayerUtils.getOnlinePlayers()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = PlayerUtils.matchOnlinePlayer(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(Member test : PlayerUtils.getOnlinePlayers()) {
                    if(test.getNick().toLowerCase().contains(lowPartial)) {
                        return test;
                    }
                }
                target = PlayerUtils.getPlayer(lowPartial);
                if(target != null) {
                    return target;
                } else {
                    for(Member test : PlayerUtils.getPlayers()) {
                        if(test.getNick().equalsIgnoreCase(lowPartial)) {
                            return test;
                        }
                    }
                    target = PlayerUtils.matchPlayer(lowPartial);
                    if(target != null) {
                        return target;
                    } else {
                        for(Member test : PlayerUtils.getPlayers()) {
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

    protected Member matchOnlinePlayer(final String partial) {
        final String lowPartial = partial.toLowerCase();
        Member target;
        try {
            return PlayerUtils.getOnlinePlayer(lowPartial);
        } catch (PlayerNotOnlineException ex) {
            for(Member test : PlayerUtils.getOnlinePlayers()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = PlayerUtils.matchOnlinePlayer(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(Member test : PlayerUtils.getOnlinePlayers()) {
                    if(test.getNick().contains(lowPartial)) {
                        return test;
                    }
                }
                player.sendError("Cannot find that player.");
                return null;
            }
        }
    }

    protected Warp matchWarp(final String partial) {
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

    protected ItemStack getItemInHand() {
        final ItemStack item = player.getItemInHand();
        if(!item.getType().equals(Material.AIR)) return item;
        player.sendError("You need an item in your hand.");
        return null;
    }

    protected boolean hasPerm(final Perm perm) {
        if(player.hasPerm(perm) || player.isOp()) {
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

    protected boolean notIgnoredBy(final Member target) {
        if(target.isIgnoringPlayer(player)) {
            player.sendError("This player can't be reached at this time.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean canRequestTo(final Member target) {
        if(UDSPlugin.getRequest(target) != null) {
            player.sendError("That player already has a request pending.");
            return false;
        }
        return true;
    }

    protected boolean notJailed(final Member target) { //TODO: Rename this method to be of the form 'PLAYER' can/is whatever 'TARGET'
        if(target.isJailed()) {
            player.sendError("You cannot do this while that player is in jail.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean notJailed() {
        if(player.isJailed()) {
            player.sendError("You cannot do this while you are in jail.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean notPinned() {
        try {
            if(player.getLastDamageCaused() + Config.PVP_TIME >= System.currentTimeMillis()) {
                player.sendError("You can't do that at this time.");
                return false;
            }
        } catch (NoMetadataSetException ex) {}
        return true;
    }

    protected boolean noClanExists(final String name) {
        if(ClanUtils.clanExists(name)) {
            player.sendError("A clan already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean noWarpExists(final String warpName) {
        if(WarpUtils.getWarp(warpName) == null) {
            return true;
        } else {
            player.sendError("A warp already exists called " + warpName + ".");
            return false;
        }
    }

    protected boolean isJailed() {
        if(player.isJailed()) {
            return true;
        } else {
            player.sendError("You are not in jail.");
            return false;
        }
    }

    protected boolean isJailed(final Member target) {
        if(target.isJailed()) {
            return true;
        } else {
            player.sendError(target.getNick() + " is not in jail.");
            return false;
        }
    }

    protected boolean hasRank(final PlayerRank rank) {
        if(player.hasRank(rank)) {
            return true;
        } else {
            player.sendError("You don't have the rank required to do that.");
            return false;
        }
    }

    protected boolean isRoomie(final Region home) {
        if(home.hasMember(player)) {
            return true;
        } else {
            player.sendError("You are not that players room mate.");
            return false;
        }
    }

    protected boolean isWorker(final Member target, final Region shop) {
        if(shop.hasMember(target)) {
            return true;
        } else {
            player.sendError("That player is not your worker.");
            return false;
        }
    }

    protected boolean isClanLeader(final Clan clan) {
        if(clan.getLeader().equals(player)) {
            return true;
        } else {
            player.sendError("You must be clan leader to do this.");
            return false;
        }
    }

    protected boolean isEnchantable(final Enchantment enchantment, final ItemStack item) {
        if(enchantment.canEnchantItem(item)) return true;
        player.sendError("You cannot use that enchantment on that item.");
        return false;
    }

    protected boolean notNearMobs() {
        final List<Entity> entities = player.getNearbyEntities(10, 3, 10);
        for(Entity entity : entities) {
            if(UDSPlugin.isHostileMob(entity.getType())) {
                player.sendError("You cannot do that now, there are monsters nearby.");
                return false;
            }
        }
        return true;
    }

    protected boolean notAfk(final Member target) {
        if(!target.isAfk()) return true;
        player.sendNormal("That player is currently AFK.");
        return false;
    }

    protected boolean canRequest(final Member target) {
        return canRequestTo(target) && notIgnoredBy(target) && notAfk(target);
    }

    protected boolean canTeleport() {
        return notPinned() && notJailed();
    }

    protected boolean hasUndo(EditSession session) {
        if(session.hasUndo()) return true;
        player.sendError("You have nothing to undo.");
        return false;
    }

    protected boolean goodEnchantLevel(final Enchantment enchantment, final int level) {
        if(level <= enchantment.getMaxLevel()) return true;
        player.sendError("The level you have chosen is too high.");
        return false;
    }

    protected ChatRoom getChatRoom() {
        try {
            return player.getChatRoom();
        } catch (NoMetadataSetException ex) {
            player.sendError("You are not in any private chat rooms.");
            return null;
        }
    }

    protected boolean hasTwoPoints(final EditSession session) {
        if(session.getV1() == null || session.getV2() == null) {
            player.sendError("You need to select two points.");
            return false;
        }
        return true;
    }

    protected boolean hasNoShop() {
        if(!RegionUtils.regionExists(RegionType.SHOP, player.getName() + "shop")) return true;
        player.sendError("You already own a shop.");
        return false;
    }

    protected boolean isBanned(final Member target) {
        if(target.isBanned()) return true;
        player.sendError("That player is not banned.");
        return false;
    }

    protected boolean hasNoHome() {
        if(!RegionUtils.regionExists(RegionType.HOME, player.getName() + "home")) return true;
        player.sendError("You already have a home.");
        return false;
    }

    protected boolean hasNoBase(final Clan clan) {
        if(!RegionUtils.regionExists(RegionType.BASE, clan.getName() + "base")) return true;
        player.sendError("Your clan already has a base.");
        return false;
    }

    protected boolean hasNoClan() {
        try {
            player.getClan();
            player.sendError("You are already in a clan.");
            return false;
        } catch (NoMetadataSetException ex) {
            return true;
        }
    }

    protected boolean isInClan(final Member target, final Clan clan) {
        try {
            if(target.getClan().equals(clan)) {
                return true;
            }
        } catch (NoMetadataSetException ex) {}
        player.sendError("That player is not in your clan.");
        return false;
    }

    protected boolean noBadLang(final String string) {
        if(Censor.noCensor(string)) {
            return true;
        } else {
            player.sendError("You can't use bad words here.");
            return false;
        }
    }

    protected boolean isEmptyShop(final Region shop) {
        if(shop.getOwner() == null) {
            return true;
        } else {
            player.sendError("Somebody already owns this shop.");
            return false;
        }
    }

    protected boolean isRoomie(final Member target, final Region home) {
        if(home.hasMember(target)) {
            return true;
        } else {
            player.sendError("That player is not your room mate.");
            return false;
        }
    }

    protected boolean isInHome(final Member target, final Region home) {
        if(target.getLocation().toVector().isInAABB(home.getV1(), home.getV2())) {
            return true;
        } else {
            player.sendError("That player is not in your home.");
            return false;
        }
    }

    protected boolean noRegionExists(final String name) {
        if(RegionUtils.regionExists(name)) {
            player.sendError("A protected final area already exists with that name.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean notMayor(final Region city) {
        if(city.isOwnedBy(player)) {
            player.sendError("You cannot do that while you are the mayor.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean noOverlaps(final Region region) {
        for(Region test : RegionUtils.getRegions()) {
            if(test != region && test.hasOverlap(region)) {
                player.sendError("You cannot do that here, you are too close to another protected final area.");
                return false;
            }
        }
        return true;
    }

    protected boolean notDueling(final Member target) {
        if(target.isDuelling()) {
            player.sendNormal("That player is already dueling someone else.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean selectionIs2D(final EditSession session) {
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

    protected boolean outRanks(final Member target) {
        if(!player.outRanks(target)) {
            player.sendMessage("You do not out rank this player.");
            return false;
        }
        return true;
    }

    protected boolean canAfford(final int cost) {
        if(player.canAfford(cost)) {
            return true;
        } else {
            player.sendError("You do not have enough money to do that.");
            return false;
        }
    }

    protected int canAfford(final String string) {
        final int cost = getInteger(string);
        if(cost > - 1) {
            if(player.canAfford(cost)) {
                return cost;
            } else {
                player.sendError("You do not have enough money to do that.");
            }
        }
        return -1;
    }

    protected boolean notSelf(final Member target) {
        if(target.equals(player)) {
            player.sendError("You cannot use that command on yourself.");
            return false;
        } else {
            return true;
        }
    }

    protected boolean noPortalExists(final String name) {
        if(PortalUtils.portalExists(name)) {
            player.sendError("A portal already exists with that name.");
            return false;
        }
        return true;
    }

    protected int getInteger(final String number) {
        if(number.matches(UDSPlugin.INT_REGEX)) {
            return Integer.parseInt(number);
        } else {
            player.sendError("The number you entered was invalid.");
            return -1;
        }
    }

    protected boolean canGift(final Member target) {
        if(target.isShopping()) {
            player.sendError("You cannot gift this player at this time.");
            return false;
        }
        return true;
    }
}

