package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

/**
 * A command that is designed to be run by a player or console.
 * Methods allow various checks to be made and messages to be sent to the players on errors.
 * 
 * @author UndeadScythes
 */
public abstract class CommandHandler extends Validator implements CommandExecutor {
    private ConsoleCommandSender console;
    private String commandName;
    private String[] args;
    private String subCmd;
    
    protected final ConsoleCommandSender getConsole() {
        return console;
    }
    
    protected final String getSubCmd() {
        return subCmd;
    }
    
    protected final boolean subCmdEquals(final String test) {
        return subCmd.equalsIgnoreCase(test);
    }
    
    protected final String getArg(final int index) {
        return args[index];
    }

    /**
     * Checks player permission then passes arguments to executor.
     * 
     * @param sender Source of the command.
     * @param command The command sent.
     * @param label Command alias.
     * @param args Arguments to the command.
     * @return <code>true</code> if the commands has been handled fully.
     */
    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender instanceof Player) {
            commandName = command.getName();
            setPlayer(PlayerUtils.getOnlinePlayer(sender.getName()));
            if(hasPerm(Perm.valueOf(commandName.toUpperCase()))) {
                this.args = args.clone();
                if(args.length > 0) subCmd = args[0].toLowerCase();
                playerExecute();
            }
            return true;
        } else if(sender instanceof ConsoleCommandSender) {
            this.args = args.clone();
            if(args.length > 0) subCmd = args[0].toLowerCase();
            console = (ConsoleCommandSender)sender;
            consoleExecute();
        }
        return false;
    }

    /**
     * Checks the player has an item in their hand.
     * 
     * @return <code>true</code> if the player is holding an item.
     */
    protected final boolean isHoldingItem() {
        if(!player.getItemInHand().getType().equals(Material.AIR)) return true;
        player.sendError("You need an item in your hand.");
        return false;
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
    protected final boolean isNotNearMobs() {
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
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean numArgsHelp(final int num) {
        if(args.length == num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean minArgsHelp(final int num) {
        if(args.length >= num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean maxArgsHelp(final int num) {
        if(args.length <= num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Send the player help relating to the number of arguments used.
     */
    private void numArgsHelp() {
        player.sendError("You have made an error using this command.");
        player.sendNormal("Use /help " + commandName + " to check the correct usage.");
    }

    /**
     * If the arguments are asking for help, send help otherwise advise about bad arguments.
     */
    protected final void subCmdHelp() {
        if(args[0].equalsIgnoreCase("help")) {
            if(args.length == 2 && args[1].matches(UDSPlugin.INT_REGEX)) {
                sendHelp(Integer.parseInt(args[1]));
            } else {
                sendHelp(1);
            }
        } else {
            player.sendError("That is not a valid sub command.");
            player.sendNormal("Use /" + commandName + " help to check the available sub commands.");
        }
    }

    /**
     * Send the player a help file for the command.
     * 
     * @param page The page to display.
     */
    protected final void sendHelp(final int page) {
        player.performCommand("help " + commandName + " " + page);
    }

    /**
     * Check if the target player is marked as AFK.
     * 
     * @param target Target player.
     * @return Is target player marked as AFK.
     */
    protected final boolean isNotAfk(final SaveablePlayer target) {
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
        return noRequests(target) && notIgnored(target) && isNotAfk(target);
    }

    /**
     * Check that the player can teleport.
     * 
     * @return <code>true</code> if the player is free to teleport.
     */
    protected final boolean canTP() {
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
     * Check that a price is both a valid price and that the player can afford it.
     * 
     * @param amount The price.
     * @return The price if the player can afford it, <code>-1</code> otherwise.
     */
    protected final int getAffordablePrice(final String amount) {
        final int cash = parseInt(amount);
        if(cash > -1 && !canAfford(cash)) return -1;
        return cash;
    }

    /**
     * Check if the player is in a chat room.
     * 
     * @return <code>true</code> if the player is in a chat room, <code>false</code> otherwise.
     */
    protected final boolean inChatRoom() {
        if(player.getChatRoom() != null) return true;
        player.sendError("You are not in any private chat rooms.");
        return false;
    }

    /**
     * Get the players WorldEdit session.
     * 
     * @return The players WE session, if the player does not currently have a session one is created.
     */
    protected final EditSession getSession() {
        return player.forceSession();
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
    protected final boolean isClanless() {
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
     * Check this player is leader of the clan.
     * 
     * @param clan Clan to check.
     * @return <code>true</code> if this player is clan leader, <code>false</code> otherwise.
     */
    protected final boolean isLeader(final Clan clan) {
        if(clan.getLeader().equals(player)) {
            return true;
        } else {
            player.sendError("You must be clan leader to do this.");
            return false;
        }
    }

    /**
     * Check if a string has bad words in it.
     * 
     * @param string String to check.
     * @return <code>true</code> if the word was clean, <code>false</code> otherwise.
     */
    protected final boolean noCensor(final String string) {
        if(Censor.noCensor(string)) {
            return true;
        } else {
            player.sendError("You can't use bad words here.");
            return false;
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
    protected final boolean notRegion(final String name) {
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

    /**
     * Check that the player is not targeting themselves.
     * 
     * @param target Target to check.
     * @return <code>true</code> if target and this player are distinct, <code>false</code> otherwise.
     */
    protected final boolean notSelf(final SaveablePlayer target) {
        if(target.equals(player)) {
            player.sendError("You cannot use that command on yourself.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if a player is in clan.
     * 
     * @return <code>true</code> if player is in a clan, <code>false</code> otherwise.
     */
    protected final boolean isInClan() {
        if(player.isInClan()) {
            return true;
        } else {
            player.sendError("You must be in a clan to do that.");
            return false;
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
     * Checks to see if a string is a positive number.
     * 
     * @param number String to check.
     * @return The number if it was one, -1 otherwise.
     */
    protected final int parseInt(final String number) {
        if(number.matches(UDSPlugin.INT_REGEX)) {
            return Integer.parseInt(number);
        } else {
            player.sendError("The number you entered was invalid.");
            return -1;
        }
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
    
    protected final boolean inLine(final EditSession session) {
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
    
    protected final boolean notPortal(final String name) {
        if(PortalUtils.portalExists(name)) {
            player.sendError("A portal already exists with that name.");
            return false;
        }
        return true;
    }
    
    protected final boolean outRanks(final SaveablePlayer target) {
        if(!player.outRanks(target)) {
            player.sendMessage("You do not out rank this player.");
            return false;
        }
        return true;
    }

    /**
     * Used when a player on the server executes a command.
     */
    public abstract void playerExecute();
    
    public void consoleExecute() {
        console.sendMessage("You cannot run this command from the console.");
    }
}
