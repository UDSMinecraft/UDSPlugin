package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.LoadableLocation.Direction;
import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
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
public abstract class PlayerCommandExecutor implements CommandExecutor {
    private SaveablePlayer player;
    private int argsLength;

    /**
     * @inheritDoc
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            player = UDSPlugin.getOnlinePlayers().get(sender.getName());
            if(hasPerm(Perm.valueOf(command.getName().toUpperCase()))) {
                if(args.length == 1 && args[0].equals("help")) {
                    player.performCommand("help " + command.getName());
                } else {
                    argsLength = args.length;
                    playerExecute(player, args);
                }
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
    public Rank matchesRank(String string) {
        Rank rank = Rank.get(string);
        if(rank != null) {
            return rank;
        } else {
            player.sendMessage(Color.ERROR + "You have not entered a valid rank.");
            return null;
        }
    }

    public boolean notAirHand() {
        if(!player.getItemInHand().getType().equals(Material.AIR)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You need an item in your hand.");
            return false;
        }
    }

    public UUID petSelected() {
        UUID pet;
        if((pet = player.getSelectedPet()) != null) {
            return pet;
        } else {
            player.sendMessage(Color.ERROR + "You have no pet selected.");
            return null;
        }
    }

    public SaveablePlayer hasWhisper() {
        SaveablePlayer target;
        if((target = player.getWhisperer()) != null) {
            return target;
        } else {
            player.sendMessage(Color.ERROR + "There is no one to send this message to.");
            return null;
        }
    }

    public boolean inChatRoom() {
        if(player.getChatRoom() != null) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not in any private chat rooms.");
            return false;
        }
    }

    public boolean noShop() {
        if(!UDSPlugin.getShops().containsKey(player.getName() + "shop")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You already own a shop.");
            return false;
        }
    }

    public boolean isBanned(SaveablePlayer target) {
        if(target.isBanned()) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not banned.");
            return false;
        }
    }

    public Request hasRequest() {
        Request request;
        if((request = UDSPlugin.getRequests().get(player.getName())) != null) {
            return request;
        } else {
            player.sendMessage(Color.ERROR + "You have no pending requests.");
            return null;
        }
    }

    public Region hasHome() {
        Region home;
        if((home = UDSPlugin.getHomes().get(player.getName() + "home")) != null) {
            return home;
        } else {
            player.sendMessage(Color.ERROR + "You do not have a home.");
            return null;
        }
    }

    public Direction matchesDirection(String dir) {
        Direction direction;
        if((direction = Direction.get(dir)) != null) {
            return direction;
        } else {
            player.sendMessage(Color.ERROR + "That is not a valid direction.");
            return null;
        }
    }

    public Direction matchesCardinalDirection(String dir) {
        Direction direction;
        if((direction = matchesDirection(dir)) != null) {
            if(direction.cardinal()) {
                return direction;
            } else {
                player.sendMessage(Color.ERROR + "You must choose a cardinal direction.");
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean noHome() {
        if(!UDSPlugin.getHomes().containsKey(player.getName() + "home")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You already have a home.");
            return false;
        }
    }

    public boolean noBase(Clan clan) {
        if(!UDSPlugin.getBases().containsKey(clan.getName() + "base")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "Your clan already has a base.");
            return false;
        }
    }

    public Clan matchesClan(String name) {
        Clan clan;
        if((clan = UDSPlugin.getClans().get(name)) != null) {
            return clan;
        } else {
            player.sendMessage(Color.ERROR + "That clan does not exist.");
            return null;
        }
    }

    public boolean isClanless() {
        if(player.getClan().equals("")) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are already in a clan.");
            return false;
        }
    }

    public boolean isInClan(SaveablePlayer player, Clan clan) {
        if(player.getClan().equals(clan.getName())) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not in your clan.");
            return false;
        }
    }

    public boolean noClan(String name) {
        if(!UDSPlugin.getClans().containsKey(name)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "A clan already exists with that name.");
            return false;
        }
    }

    public boolean isLeader(Clan clan) {
        if(clan.getLeader().equals(player.getName())) {
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
    public Clan hasClan() {
        Clan clan;
        if((clan = UDSPlugin.getClans().get(player.getClan())) != null) {
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
    public Region hasBase(Clan clan) {
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
    public boolean noCensor(String string) {
        if(Censor.noCensor(string)) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You can't use bad words here.");
            return false;
        }
    }

    /**
     * Check that no warp by this name exists.
     * @param warpName Warp name to check.
     * @return <code>true</code> if no warp exists by this name, <code>false</code> otherwise.
     */
    public boolean noWarp(String warpName) {
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
    public boolean isJailed() {
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
    public boolean isJailed(SaveablePlayer target) {
        if(target.isJailed()) {
            return true;
        } else {
            target.sendMessage(Color.ERROR + target.getDisplayName() + " is not in jail.");
            return false;
        }
    }

    /**
     * Check that a material exists.
     * @param string Material to check.
     * @return The material if it exists, <code>null</code> otherwise.
     */
    public ItemStack matchesItem(String string) {
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
    public Enchantment matchesEnchantment(String enchant) {
        Enchantment enchantment = Enchantment.getByName(enchant);
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
    public boolean hasRank(Rank rank) {
        if(player.getRank().compareTo(rank) >= 0) {
            return true;
        } else {
            player.sendMessage(Message.DONT_HAVE_RANK);
            return false;
        }
    }

    /**
     * Get the first region the player is currently in.
     * @return The first region the player is in or <code>null</code> if none found.
     */
    public Region inRegion() {
        Region region;
        if((region = player.getCurrentRegion(Region.Type.CITY)) != null) {
            return region;
        } else {
            player.sendMessage(Message.NOT_IN_CITY);
            return null;
        }
    }

    public boolean isRoomie(Region home) {
        if(home.hasMember(player.getName())) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "You are not that players room mate.");
            return false;
        }
    }

    public boolean isRoomie(SaveablePlayer target, Region home) {
        if(home.hasMember(target.getName())) {
            return true;
        } else {
            player.sendMessage(Color.ERROR + "That player is not your room mate.");
            return false;
        }
    }

    public Region hasHome(SaveablePlayer target) {
        Region home;
        if((home = UDSPlugin.getHomes().get(target.getName() + "home")) != null) {
            return home;
        } else {
            player.sendMessage(Color.ERROR + "That player does not have a home.");
            return null;
        }
    }

    public boolean isInHome(SaveablePlayer target, Region home) {
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
    public boolean noRegion(String name) {
        if(!UDSPlugin.getRegions().containsKey(name)) {
            return true;
        } else {
            player.sendMessage(Message.REGION_EXISTS);
            return false;
        }
    }

    /**
     * Checks if a city exists by this name.
     * @param cityName City to check.
     * @return The city if it exists, <code>null</code> otherwise.
     */
    public Region matchesCity(String cityName) {
        Region city;
        if((city = UDSPlugin.getCities().matchKey(cityName)) != null) {
            return city;
        } else {
            player.sendMessage(Message.NOT_A_CITY);
            return null;
        }

    }

    /**
     * Checks first if the city exists then that the player is the mayor of the city.
     * @param cityName City to check.
     * @return The city if both the city exists and the player is the mayor, <code>null</code> otherwise.
     */
    public Region mayor(String cityName) {
        Region city;
        if((city = matchesCity(cityName)) != null) {
            if(city.getOwner().equals(player.getName())) {
                return city;
            } else {
                player.sendMessage(Message.NOT_MAYOR);
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
    public boolean noOverlaps(Region region) {
        for(Region test : UDSPlugin.getRegions().values()) {
            if(test.hasOverlap(region)) {
                player.sendMessage(Message.REGION_HAS_OVERLAP);
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
    public boolean notDueling(SaveablePlayer target) {
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
    public boolean notSelf(SaveablePlayer target) {
        if(!target.equals(player)) {
            return true;
        } else {
            player.sendMessage(Message.NOT_SELF);
            return false;
        }
    }

    /**
     * Check if a player is in clan.
     * @return <code>true</code> if player is in a clan, <code>false</code> otherwise.
     */
    public boolean isInClan() {
        if(player.isInClan()) {
            return true;
        } else {
            player.sendMessage(Message.NOT_IN_CLAN);
            return false;
        }
    }

    /**
     * Check that the player is not pinned due to attacking another player recently.
     * @return <code>true</code> if the player is not pinned, <code>false</code> otherwise.
     */
    public boolean notPinned() {
        if(player.getLastDamageCaused() + Config.PVP_TIME < System.currentTimeMillis()) {
            return true;
        } else {
            player.sendMessage(Message.CANT_DO_THAT_NOW);
            return false;
        }
    }

    /**
     * Check that this player is not in jail.
     * @return <code>true</code> if the player is not in jail, <code>false</code> otherwise.
     */
    public boolean notJailed() {
        if(!player.isJailed()) {
            return true;
        } else {
            player.sendMessage(Message.NOT_WHILE_IN_JAIL);
            return false;
        }
    }

    /**
     * Check that the target player is not in jail.
     * @return <code>true</code> if the target player is not in jail, <code>false</code> otherwise.
     */
    public boolean notJailed(SaveablePlayer target) {
        if(!target.isJailed()) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_IN_JAIL);
            return false;
        }
    }

    /**
     * Check that the target player has no requests pending.
     * @param target The player to check.
     * @return <code>true</code> if the target player has no requests pending, <code>false</code> otherwise.
     */
    public boolean notBusy(SaveablePlayer target) {
        if(!UDSPlugin.getRequests().containsKey(target.getName())) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_HAS_REQUEST);
            return false;
        }
    }

    /**
     * Check that the target player is not being ignored by the command sender.
     * @param target Player to check.
     * @return <code>true</code> if the target player is not ignoring the command sender, <code>false</code> otherwise.
     */
    public boolean notIgnored(SaveablePlayer target) {
        if(!target.isIgnoringPlayer(player)) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_CANT_BE_REACHED);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param length Number of arguments required.
     * @return <code>true</code> if there are sufficient arguments, <code>false</code> if there are too few or too many.
     */
    public boolean argsEq(int length) {
        if(argsLength == length) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param max Maximum number of arguments.
     * @return <code>true</code> if there aren't too many arguments, <code>false</code> otherwise.
     */
    public boolean argsLessEq(int max) {
        if(argsLength <= max) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param min Minimum number of arguments.
     * @return <code>true</code> if there aren't enough arguments, <code>false</code> otherwise.
     */
    public boolean argsMoreEq(int min) {
        if(argsLength >= min) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks that the command has the correct number of arguments.
     * @param min Minimum number of arguments.
     * @param max Maximum number of arguments.
     * @return <code>true</code> if there the right number of arguments, <code>false</code> otherwise.
     */
    public boolean argsMoreLessInc(int min, int max) {
        if(argsLength >= min && argsLength <= max) {
            return true;
        } else {
            player.sendMessage(Message.WRONG_NUM_ARGS);
            return false;
        }
    }

    /**
     * Checks to see if a string is a positive number.
     * @param number String to check.
     * @return The number if it was one, -1 otherwise.
     */
    public int parseInt(String number) {
        if(number.matches("[0-9][0-9]*")) {
            return Integer.parseInt(number);
        } else {
            player.sendMessage(Message.BAD_NUMBER);
            return -1;
        }
    }

    /**
     * Checks if a player is online.
     * @param target Player to check.
     * @return <code>true</code> if the player is online, <code>false</code> otherwise.
     */
    public boolean isOnline(SaveablePlayer target) {
        if(target.isOnline()) {
            return true;
        } else {
            player.sendMessage(Message.PLAYER_NOT_ONLINE);
            return false;
        }
    }

    /**
     * Matches a partial player name to the first player it finds.
     * @param partial Partial player name.
     * @return The first player matched or <code>null</code> if no players matched.
     */
    public SaveablePlayer matchesPlayer(String partial) {
        SaveablePlayer target = UDSPlugin.getOnlinePlayers().get(partial);
        if(target!= null) {
            return target;
        } else {
            for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                if(test.getDisplayName().equalsIgnoreCase(partial)) {
                    return test;
                }
            }
            if(target!= null) {
                return target;
            } else {
                target = UDSPlugin.getOnlinePlayers().matchKey(partial);
                if(target != null) {
                    return target;
                } else {
                    for(SaveablePlayer test : UDSPlugin.getOnlinePlayers().values()) {
                        if(test.getDisplayName().matches("(?i)" + partial)) {
                            return test;
                        }
                    }
                    target = UDSPlugin.getPlayers().get(partial);
                    if(target != null) {
                        return target;
                    } else {
                        for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                            if(test.getDisplayName().equalsIgnoreCase(partial)) {
                                return test;
                            }
                        }
                        target = UDSPlugin.getPlayers().matchKey("(?i)" + partial);
                        if(target != null) {
                            return target;
                        } else {
                            for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                                if(test.getDisplayName().matches("(?i)" + partial)) {
                                    return test;
                                }
                            }
                        }
                        player.sendMessage(Message.NO_PLAYER);
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Matches a warp name from a partial string.
     * @param partial Partial warp name.
     * @return The warp matched or <code>null</code> if there were no matches.
     */
    public Warp matchesWarp(String partial) {
        Warp warp = UDSPlugin.getWarps().get(partial);
        if(warp != null) {
            return warp;
        } else {
            warp = UDSPlugin.getWarps().matchKey(partial);
            if(warp != null) {
                return warp;
            } else {
                player.sendMessage(Message.NO_WARP);
                return null;
            }
        }
    }

    /**
     * Checks if the player can afford a certain cost.
     * @param cost The cost to check.
     * @return <code>true</code> if the player can afford the cost, <code>false</code> otherwise.
     */
    public boolean canAfford(int cost) {
        if(player.canAfford(cost)) {
            return true;
        } else {
            player.sendMessage(Message.CANT_AFFORD);
            return false;
        }
    }

    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    public boolean hasPerm(Perm perm) {
        if(player.hasPermission(perm) || player.isOp()) {
            return true;
        } else {
            player.sendMessage(Message.NO_PERM);
            return false;
        }
    }

    /**
     * Used when a player on the server executes a command.
     * @param player Player who ran the command.
     * @param args Arguements of the command.
     */
    public abstract void playerExecute(SaveablePlayer player, String[] args);
}
