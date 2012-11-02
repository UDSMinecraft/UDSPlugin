package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.SaveablePlayer.Rank;
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
        player = UDSPlugin.getPlayers().get(sender.getName());
        if(sender instanceof Player && hasPerm(Perm.valueOf(command.getName().toUpperCase()))) {
            if(args.length == 1 && args[0].equals("help")) {
                player.performCommand("help " + command.getName());
                return true;
            }
            argsLength = args.length;
            playerExecute(player, args);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a string corresponds to a valid rank.
     * @param string String to check.
     * @return <code>true</code> if the rank is valid, <code>false</code> otherwise.
     */
    public Rank matchesRank(String string) {
        Rank rank = Rank.get(string);
        if(rank != null) {
            return rank;
        } else {
            player.sendMessage(Message.NOT_A_RANK);
            return null;
        }
    }

    /**
     * Check if a string has bad words in it.
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    public boolean censor(String string) {
        if(Censor.censor(string)) {
            return true;
        } else {
            player.sendMessage(Message.CANT_USE_BAD_WORDS);
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
                player.sendMessage(Message.BAD_DATA_VALUE);
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
            player.sendMessage(Message.NOT_AN_ITEM);
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
            player.sendMessage(Message.NOT_AN_ENCHANTMENT);
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
                if(test.getDisplayName().equals(partial)) {
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
                        if(test.getDisplayName().matches(partial)) {
                            return test;
                        }
                    }
                    target = UDSPlugin.getPlayers().get(partial);
                    if(target != null) {
                        return target;
                    } else {
                        for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                            if(test.getDisplayName().equals(partial)) {
                                return test;
                            }
                        }
                        target = UDSPlugin.getPlayers().matchKey(partial);
                        if(target != null) {
                            return target;
                        } else {
                            for(SaveablePlayer test : UDSPlugin.getPlayers().values()) {
                                if(test.getDisplayName().matches(partial)) {
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
