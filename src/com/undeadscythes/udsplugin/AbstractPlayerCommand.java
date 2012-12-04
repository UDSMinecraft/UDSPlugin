package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.SaveablePlayer.PlayerRank;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * A command that is designed to be run by a player.
 * @author UndeadScythes
 */
public abstract class AbstractPlayerCommand implements CommandExecutor {
    private transient SaveablePlayer player;
    private transient String commandName;
    private transient int argsLength;

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender instanceof Player) {
            commandName = command.getName();
            player = UDSPlugin.getOnlinePlayers().get(sender.getName());
            if(hasPerm(Perm.valueOf(commandName.toUpperCase()))) {
                argsLength = args.length;
                playerExecute(player, args);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a string corresponds to a valid rank.
     * @param string String to check.
     * @return The rank if the rank is valid, <code>null</code> otherwise.
     */
    protected PlayerRank getRank(final String string) {
        PlayerRank rank = PlayerRank.getByName(string);
        if(rank != null) {
            return rank;
        } else {
            player.sendMessage(Color.ERROR + "You have not entered a valid rank.");
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected boolean notAirHand() {
        if(!player.getItemInHand().getType().equals(Material.AIR)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You need an item in your hand.");
            return false;
        }
    }

    /**
     *
     * @param enchantment
     * @param item
     * @return
     */
    protected boolean canEnchant(final Enchantment enchantment, final ItemStack item) {
        if(enchantment.canEnchantItem(item)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You cannot use that enchantment on that item.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected UUID getSelectedPet() {
        UUID pet;
        if((pet = player.getSelectedPet()) != null) {
            return pet;
        } else {
            player.sendMessage(Color.ERROR + "Right click a pet while sneaking to select it first.");
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected Region getShop() {
        Region shop;
        if((shop = UDSPlugin.getShops().get(player.getName() + "shop")) != null) {
            return shop;
        } else {
            player.sendMessage(Color.ERROR + "You do not own a shop.");
            return null;
        }

    }

    /**
     *
     * @param num
     * @return
     */
    protected boolean numArgsHelp(final int num) {
        if(argsLength == num) {
            return true;
        } else {
            numArgsHelp();
            return false;
        }
    }

    /**
     *
     * @param num
     * @return
     */
    protected boolean minArgsHelp(final int num) {
        if(argsLength >= num) {
            return true;
        } else {
            numArgsHelp();
            return false;
        }
    }

    /**
     *
     * @param num
     * @return
     */
    protected boolean maxArgsHelp(final int num) {
        if(argsLength <= num) {
            return true;
        } else {
            numArgsHelp();
            return false;
        }
    }

    /**
     *
     */
    protected void numArgsHelp() {
        player.sendMessage(Color.ERROR + "You have made an error using this command.");
        player.sendMessage(Color.MESSAGE + "Use /help " + commandName + " to check the correct usage.");
    }

    /**
     *
     * @param args
     */
    protected void subCmdHelp(final String[] args) {
        if(args[0].equalsIgnoreCase("help")) {
            if(args.length == 2 && args[1].matches("[0-9][0-9]*")) {
                sendHelp(Integer.parseInt(args[1]));
            } else {
                sendHelp(1);
            }
        } else {
            subCmdHelp();
        }
    }

    /**
     *
     */
    protected void subCmdHelp() {
        player.sendMessage(Color.ERROR + "That is not a valid sub command.");
        player.sendMessage(Color.MESSAGE + "Use /help " + commandName + " to check the available sub commands.");
    }

    /**
     *
     * @param page
     */
    protected void sendHelp(final int page) {
        player.performCommand("help " + commandName + " " + page);
    }

    /**
     *
     * @param target
     * @return
     */
    protected boolean canRequest(final SaveablePlayer target) {
        if(noRequests(target) && notIgnored(target)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected boolean canTP() {
        if(notPinned() && notJailed()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param target
     * @return
     */
    protected Region getShop(final SaveablePlayer target) {
        Region shop;
        if((shop = UDSPlugin.getShops().get(target.getName() + "shop")) != null) {
            return shop;
        } else {
            player.sendMessage(Color.ERROR + "That player does not own a shop.");
            return null;
        }

    }

    /**
     *
     * @param enchantment
     * @param level
     * @return
     */
    protected boolean goodEnchantLevel(final Enchantment enchantment, final int level) {
        if(level <= enchantment.getMaxLevel()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "The level you have chosen is too high.");
            return false;
        }
    }

    /**
     *
     * @param amount
     * @return
     */
    protected int getAffordablePrice(final String amount) {
        int cash;
        if((cash = parseInt(amount)) != -1 && canAfford(cash)) {
            return cash;
        } else {
            return -1;
        }
    }

    /**
     *
     * @return
     */
    protected SaveablePlayer getWhisperer() {
        SaveablePlayer target;
        if((target = player.getWhisperer()) != null) {
            return target;
        } else {
            player.sendMessage(Color.ERROR + "There is no one to send this message to.");
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected boolean inChatRoom() {
        if(player.getChatRoom() != null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not in any private chat rooms.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected WESession getSession() {
        WESession session;
        if((session = UDSPlugin.getSessions().get(player.getName())) != null) {
            return session;
        } else {
            player.sendMessage(Color.ERROR + "You do not have a current WE session.");
            return null;
        }
    }

    /**
     *
     * @param regionName
     * @return
     */
    protected Region getRegion(final String regionName) {
        Region region;
        if((region = UDSPlugin.getRegions().get(regionName)) != null) {
            return region;
        } else {
            player.sendMessage(Color.ERROR + "No region exists by that name.");
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected Region.RegionFlag getFlag(final String name) {
        Region.RegionFlag flag;
        if((flag = Region.RegionFlag.getByName(name)) != null) {
            return flag;
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid region type.");
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected Region.RegionType getRegionType(final String name) {
        Region.RegionType type;
        if((type = Region.RegionType.getByName(name)) != null) {
            return type;
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid region type.");
            return null;
        }
    }

    /**
     *
     * @param session
     * @return
     */
    protected boolean hasTwoPoints(final WESession session) {
        if(session.getV1() != null && session.getV2() != null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You need to select two points.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected boolean noShop() {
        if(!UDSPlugin.getShops().containsKey(player.getName() + "shop")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You already own a shop.");
            return false;
        }
    }

    /**
     *
     * @param target
     * @return
     */
    protected boolean isBanned(final SaveablePlayer target) {
        if(target.isBanned()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not banned.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected Request getRequest() {
        Request request;
        if((request = UDSPlugin.getRequests().get(player.getName())) != null) {
            return request;
        } else {
            player.sendMessage(Color.ERROR + "You have no pending requests.");
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected Region getHome() {
        Region home;
        if((home = UDSPlugin.getHomes().get(player.getName() + "home")) != null) {
            return home;
        } else {
            player.sendMessage(Color.ERROR + "You do not have a home.");
            return null;
        }
    }

    /**
     *
     * @param dir
     * @return
     */
    protected Direction getDirection(final String dir) {
        Direction direction;
        if((direction = Direction.getByName(dir)) != null) {
            return direction;
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid direction.");
            return null;
        }
    }

    /**
     *
     * @param dir
     * @return
     */
    protected Direction getCardinalDirection(final String dir) {
        Direction direction;
        if((direction = getDirection(dir)) != null) {
            if(direction.isCardinal()) {
                return direction;
            } else {
                player.sendMessage(Color.ERROR + "You must choose a cardinal direction.");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected boolean noHome() {
        if(!UDSPlugin.getHomes().containsKey(player.getName() + "home")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You already have a home.");
            return false;
        }
    }

    /**
     *
     * @param clan
     * @return
     */
    protected boolean noBase(final Clan clan) {
        if(!UDSPlugin.getBases().containsKey(clan.getName() + "base")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "Your clan already has a base.");
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected Clan getClan(final String name) {
        Clan clan;
        if((clan = UDSPlugin.getClans().get(name)) != null) {
            return clan;
        } else {
            player.sendMessage(Color.ERROR + "That clan does not exist.");
            return null;
        }
    }

    /**
     *
     * @return
     */
    protected boolean isClanless() {
        if(player.getClan() == null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are already in a clan.");
            return false;
        }
    }

    /**
     *
     * @param player
     * @param clan
     * @return
     */
    protected boolean isInClan(final SaveablePlayer player, final Clan clan) {
        if(player.getClan().equals(clan)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not in your clan.");
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected boolean notClan(final String name) {
        if(!UDSPlugin.getClans().containsKey(name)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "A clan already exists with that name.");
            return false;
        }
    }

    /**
     *
     * @param clan
     * @return
     */
    protected boolean isLeader(final Clan clan) {
        if(clan.getLeader().equals(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You must be clan leader to do this.");
            return false;
        }
    }

    /**
     * Check that a player is in a clan.
     * @return The player's clan or <code>null</code> if the player is clanless.
     */
    protected Clan getClan() {
        Clan clan;
        if((clan = player.getClan()) != null) {
            return clan;
        } else {
            player.sendMessage(Color.ERROR + "You are not in a clan.");
            return null;
        }
    }

    /**
     * Check if the players clan has a base.
     * @param clan The players clan.
     * @return The clan's base or <code>null</code> if the clan does not have a base.
     */
    protected Region getBase(final Clan clan) {
        Region region;
        if((region = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
            return region;
        } else {
            player.sendMessage(Color.ERROR + "Your clan does not have a base.");
            return null;
        }
    }

    /**
     * Check if a string has bad words in it.
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    protected boolean noCensor(final String string) {
        if(Censor.noCensor(string)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You can't use bad words here.");
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected SaveablePlayer getMatchingOtherPlayer(final String name) {
        SaveablePlayer target;
        if((target = getMatchingPlayer(name)) != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * Check that no warp by this name exists.
     * @param warpName Warp name to check.
     * @return <code>true</code> if no warp exists by this name, <code>false</code> otherwise.
     */
    protected boolean notWarp(final String warpName) {
        if(UDSPlugin.getWarps().get(warpName) == null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "A warp already exists called " + warpName + ".");
            return false;
        }
    }

    /**
     * Check that the player is in jail.
     * @return <code>true</code> if the player is in jail, <code>false</code> otherwise.
     */
    protected boolean isJailed() {
        if(player.isJailed()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not in jail.");
            return false;
        }
    }

    /**
     * Check that the player is in jail.
     * @param target Player to check.
     * @return <code>true</code> if the player is in jail, <code>false</code> otherwise.
     */
    protected boolean isJailed(final SaveablePlayer target) {
        if(target.isJailed()) {
            return true;
        } else {
            target.sendMessage(Color.ERROR + target.getNick() + " is not in jail.");
            return false;
        }
    }

    /**
     * Check that a material exists.
     * @param string Material to check.
     * @return The material if it exists, <code>null</code> otherwise.
     */
    protected ItemStack getItem(final String string) {
        Material material;
        String matString = string.toUpperCase();
        byte data = 0;
        if(string.contains(":")) {
            if(string.split(":")[1].matches("[0-9][0-9]*")) {
                data = Byte.parseByte(string.split(":")[1]);
            } else {
                player.sendMessage(Color.ERROR + "That is not a valid data value.");
                return null;
            }
            matString = string.split(":")[0].toUpperCase();
        }
        if(matString.matches("[0-9][0-9]*")) {
            material = Material.getMaterial(Integer.parseInt(matString));
        } else {
            material = Material.getMaterial(matString);
        }
        if(material != null) {
            return new ItemStack(material, 1, (short)0, data);
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid item.");
            return null;
        }
    }

    /**
     * Check if an enchantment exists.
     * @param enchant Enchantment name.
     * @return The enchantment if it exists, <code>null</code> otherwise.
     */
    protected Enchantment getEnchantment(final String enchant) {
        Enchantment enchantment = Enchantment.getByName(enchant.toUpperCase());
        if(enchantment != null) {
            return enchantment;
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid enchantment.");
            return null;
        }
    }

    /**
     * Check if the player has the required rank.
     * @param rank Rank required.
     * @return <code>true</code> if the player has the required rank, <code>false</code> otherwise.
     */
    protected boolean hasRank(final PlayerRank rank) {
        if(player.getRank().compareTo(rank) >= 0) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You don't have the rank required to do that.");
            return false;
        }
    }

    /**
     * Get the first region the player is currently in.
     * @return The first region the player is in or <code>null</code> if none found.
     */
    protected Region getCurrentRegion() {
        Region region;
        if((region = player.getCurrentRegion(Region.RegionType.CITY)) != null) {
            return region;
        } else {
            player.sendMessage(Color.ERROR + "You are not in a city.");
            return null;
        }
    }

    /**
     *
     * @param home
     * @return
     */
    protected boolean isRoomie(final Region home) {
        if(home.hasMember(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not that players room mate.");
            return false;
        }
    }

    /**
     *
     * @param shop
     * @return
     */
    protected boolean isWorker(final Region shop) {
        if(shop.hasMember(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not that players worker.");
            return false;
        }
    }

    /**
     *
     * @param target
     * @param shop
     * @return
     */
    protected boolean isWorker(final SaveablePlayer target, final Region shop) {
        if(shop.hasMember(target)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not your worker.");
            return false;
        }
    }

    /**
     *
     * @param shop
     * @return
     */
    protected boolean isEmptyShop(final Region shop) {
        if(shop.getOwner() == null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "Somebody already owns this shop.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    protected Region getContainingShop() {
        Region shop;
        if((shop = player.getCurrentRegion(Region.RegionType.SHOP)) != null) {
            return shop;
        } else {
            player.sendMessage(Color.ERROR + "You must be stood inside a shop to buy it.");
            return null;
        }
    }

    /**
     *
     * @param target
     * @param home
     * @return
     */
    protected boolean isRoomie(final SaveablePlayer target, final Region home) {
        if(home.hasMember(target)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not your room mate.");
            return false;
        }
    }

    /**
     *
     * @param target
     * @return
     */
    protected Region getHome(final SaveablePlayer target) {
        Region home;
        if((home = UDSPlugin.getHomes().get(target.getName() + "home")) != null) {
            return home;
        } else {
            player.sendMessage(Color.ERROR + "That player does not have a home.");
            return null;
        }
    }

    /**
     *
     * @param target
     * @param home
     * @return
     */
    protected boolean isInHome(final SaveablePlayer target, final Region home) {
        if(target.getLocation().toVector().isInAABB(home.getV1(), home.getV2())) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not in your home.");
            return false;
        }
    }

    /**
     * Checks that no region already exists with a given name.
     * @param name Name to check.
     * @return <code>true</code> if no region already exists with the given name, <code>false</code> otherwise.
     */
    protected boolean notRegion(final String name) {
        if(!UDSPlugin.getRegions().containsKey(name)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "A protected area already exists with that name.");
            return false;
        }
    }

    /**
     * Checks if a city exists by this name.
     * @param cityName City to check.
     * @return The city if it exists, <code>null</code> otherwise.
     */
    protected Region getMatchingCity(final String cityName) {
        Region city;
        if((city = UDSPlugin.getCities().get(cityName)) != null) {
            return city;
        } else {
            if((city = UDSPlugin.getCities().matchKey(cityName)) != null) {
                return city;
            } else {
                player.sendMessage(Color.ERROR + "No city exists by that name.");
                return null;
            }
        }
    }

    /**
     *
     * @param city
     * @return
     */
    protected boolean notMayor(final Region city) {
        if(!city.isOwner(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You cannot do that while you are the mayor.");
            return false;
        }
    }

    /**
     * Checks first if the city exists then that the player is the mayor of the city.
     * @param cityName City to check.
     * @return The city if both the city exists and the player is the mayor, <code>null</code> otherwise.
     */
    protected Region getMunicipality(final String cityName) {
        Region city;
        if((city = getMatchingCity(cityName)) != null) {
            if(city.getOwner().equals(player)) {
                return city;
            } else {
                player.sendMessage(Color.ERROR + "You are not the mayor of that city.");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Checks that a region has no overlaps with any other regions.
     * @param region Region to check for overlaps.
     * @return <code>true</code> if there are no overlaps with other regions, <code>false</code> otherwise.
     */
    protected boolean noOverlaps(final Region region) {
        for(Region test : UDSPlugin.getRegions().values()) {
            if(test.hasOverlap(region)) {
                player.sendMessage(Color.ERROR + "You cannot do that here, you are too close to another protected area.");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that a player is not already engaged in a duel with another player.
     * @param target Player to check.
     * @return <code>true</code> if the player is not duelling, <code>false</code> otherwise.
     */
    protected boolean notDueling(final SaveablePlayer target) {
        if(!target.isDuelling()) {
            return true;
        } else {
            player.sendMessage(Color.MESSAGE + "That player is already dueling someone else.");
            return false;
        }
    }

    /**
     * Check that the player is not targeting themselves.
     * @param target Target to check.
     * @return <code>true</code> if target and this player are distinct, <code>false</code> otherwise.
     */
    protected boolean notSelf(final SaveablePlayer target) {
        if(!target.equals(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You cannot use that command on yourself.");
            return false;
        }
    }

    /**
     * Check if a player is in clan.
     * @return <code>true</code> if player is in a clan, <code>false</code> otherwise.
     */
    protected boolean isInClan() {
        if(player.isInClan()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You must be in a clan to do that.");
            return false;
        }
    }

    /**
     * Check that the player is not pinned due to attacking another player recently.
     * @return <code>true</code> if the player is not pinned, <code>false</code> otherwise.
     */
    protected boolean notPinned() {
        if(player.getLastDamageCaused() + Config.pvpTime < System.currentTimeMillis()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You can't do that at this time.");
            return false;
        }
    }

    /**
     * Check that this player is not in jail.
     * @return <code>true</code> if the player is not in jail, <code>false</code> otherwise.
     */
    protected boolean notJailed() {
        if(!player.isJailed()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You cannot do this while you are in jail.");
            return false;
        }
    }

    /**
     * Check that the target player is not in jail.
     * @param target
     * @return <code>true</code> if the target player is not in jail, <code>false</code> otherwise.
     */
    protected boolean notJailed(final SaveablePlayer target) {
        if(!target.isJailed()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You can't do this while that player is in jail.");
            return false;
        }
    }

    /**
     * Check that the target player has no requests pending.
     * @param target The player to check.
     * @return <code>true</code> if the target player has no requests pending, <code>false</code> otherwise.
     */
    protected boolean noRequests(final SaveablePlayer target) {
        if(!UDSPlugin.getRequests().containsKey(target.getName())) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player already has a request pending.");
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    protected SaveablePlayer getMatchingOtherOnlinePlayer(final String name) {
        SaveablePlayer target;
        if((target = getMatchingOnlinePlayer(name)) != null && notSelf(target)) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * Check that the target player is not being ignored by the command sender.
     * @param target Player to check.
     * @return <code>true</code> if the target player is not ignoring the command sender, <code>false</code> otherwise.
     */
    protected boolean notIgnored(final SaveablePlayer target) {
        if(!target.isIgnoringPlayer(player)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "This player can't be reached at this time.");
            return false;
        }
    }

    /**
     * Checks to see if a string is a positive number.
     * @param number String to check.
     * @return The number if it was one, -1 otherwise.
     */
    protected int parseInt(final String number) {
        if(number.matches("[0-9][0-9]*")) {
            return Integer.parseInt(number);
        } else {
            player.sendMessage(Color.ERROR + "The number you entered was invalid.");
            return -1;
        }
    }

    /**
     * Checks if a player is online.
     * @param target Player to check.
     * @return <code>true</code> if the player is online, <code>false</code> otherwise.
     */
    protected boolean isOnline(final SaveablePlayer target) {
        if(target.isOnline()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not online.");
            return false;
        }
    }

    /**
     * Matches a partial player name to the first player it finds.
     * @param partial Partial player name.
     * @return The first player matched or <code>null</code> if no players matched.
     */
    protected SaveablePlayer getMatchingPlayer(final String partial) {
        String lowPartial = partial.toLowerCase();
        SaveablePlayer target = UDSPlugin.getOnlinePlayers().get(lowPartial);
        if(target != null) {
            return target;
        } else {
            for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = UDSPlugin.getOnlinePlayers().matchKey(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                    if(test.getNick().toLowerCase().contains(lowPartial)) {
                        return test;
                    }
                }
                target = UDSPlugin.getPlayers().get(lowPartial);
                if(target != null) {
                    return target;
                } else {
                    for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                        if(test.getNick().equalsIgnoreCase(lowPartial)) {
                            return test;
                        }
                    }
                    target = UDSPlugin.getPlayers().matchKey(lowPartial);
                    if(target != null) {
                        return target;
                    } else {
                        for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                            if(test.getNick().toLowerCase().contains(lowPartial)) {
                                return test;
                            }
                        }
                    }
                    player.sendMessage(Color.ERROR + "Cannot find a player by that name.");
                    return null;
                }
            }
        }
    }

    /**
     *
     * @param partial
     * @return
     */
    protected SaveablePlayer getMatchingOnlinePlayer(final String partial) {
        String lowPartial = partial.toLowerCase();
        SaveablePlayer target = UDSPlugin.getOnlinePlayers().get(lowPartial);
        if(target!= null) {
            return target;
        } else {
            for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                if(test.getNick().equalsIgnoreCase(lowPartial)) {
                    return test;
                }
            }
            target = UDSPlugin.getOnlinePlayers().matchKey(lowPartial);
            if(target != null) {
                return target;
            } else {
                for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                    if(test.getNick().contains(lowPartial)) {
                        return test;
                    }
                }
                player.sendMessage(Color.ERROR + "Cannot find that player.");
                return null;
            }
        }
    }

    /**
     * Matches a warp name from a partial string.
     * @param partial Partial warp name.
     * @return The warp matched or <code>null</code> if there were no matches.
     */
    protected Warp getWarp(final String partial) {
        Warp warp = UDSPlugin.getWarps().get(partial);
        if(warp != null) {
            return warp;
        } else {
            warp = UDSPlugin.getWarps().matchKey(partial);
            if(warp != null) {
                return warp;
            } else {
                player.sendMessage(Color.ERROR + "That warp point does not exist.");
                return null;
            }
        }
    }

    /**
     * Checks if the player can afford a certain cost.
     * @param cost The cost to check.
     * @return <code>true</code> if the player can afford the cost, <code>false</code> otherwise.
     */
    protected boolean canAfford(final int cost) {
        if(player.canAfford(cost)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You do not have enough money to do that.");
            return false;
        }
    }

    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    protected boolean hasPerm(final Perm perm) {
        if(player.hasPermission(perm) || player.isOp()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You do not have permission to do that.");
            return false;
        }
    }

    /**
     * Used when a player on the server executes a command.
     * @param player Player who ran the command.
     * @param args Arguments of the command.
     */
    public abstract void playerExecute(final SaveablePlayer player, final String[] args);
}
