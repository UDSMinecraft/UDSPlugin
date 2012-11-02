package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Messages commonly used in the plugin.
 * @author UndeadScythes
 */
public class Message {
    /**
     * That is not a valid data value.
     */
    public static final String BAD_DATA_VALUE = Color.ERROR + "That is not a valid data value.";
    /**
     * That is not a valid item.
     */
    public static final String NOT_AN_ITEM = Color.ERROR + "That is not a valid item.";
    /**
     * That is not a valid direction.
     */
    public static final String NOT_A_DIRECTION = Color.ERROR + "That is not a valid direction.";
    /**
     * That is not a valid enchantment.
     */
    public static final String NOT_AN_ENCHANTMENT = Color.ERROR + "That is not a valid enchantment.";
    /**
     * There are no enchantments do display.
     */
    public static final String NO_ENCHANTMENTS = Color.ERROR + "There are no enchantments do display.";
    /**
     * You don't have the rank required to do that.
     */
    public static final String DONT_HAVE_RANK = Color.ERROR + "You don't have the rank required to do that.";
    /**
     * You are not in a city.
     */
    public static final String NOT_IN_CITY = Color.ERROR + "You are not in a city.";
    /**
     * You are not in a protected area.
     */
    public static final String NOT_IN_REGION = Color.ERROR + "You are not in a protected area.";
    /**
     * No city exists by that name.
     */
    public static final String NOT_A_CITY = Color.ERROR + "No city exists by that name.";
    /**
     * You are not the mayor of that city.
     */
    public static final String NOT_MAYOR = Color.ERROR + "You are not the mayor of that city.";
    /**
     * City founded.
     */
    public static final String NEW_CITY = Color.MESSAGE + "City founded.";
    /**
     * A protected area already exists with that name.
     */
    public static final String REGION_EXISTS = Color.ERROR + "A protected area already exists with that name.";
    /**
     * You cannot do that here, you are too close to another protected area.
     */
    public static final String REGION_HAS_OVERLAP = Color.ERROR + "You cannot do that here, you are too close to another protected area.";
    /**
     * That player is already dueling someone else.
     */
    public static final String PLAYER_DUELING = Color.MESSAGE + "That player is already dueling someone else.";
    /**
     * The Ender Dragon has regained his strength and awaits brave warriors in The End.
     */
    public static final String DRAGON_RESPAWN = Color.BROADCAST + "The Ender Dragon has regained his strength and awaits brave warriors in The End.";
    /**
     * You have not entered a valid rank.
     */
    public static final String NOT_A_RANK = Color.ERROR + "You have not entered a valid rank.";
    /**
     * Warp point set.
     */
    public static final String WARP_SET = Color.MESSAGE + "Warp point set.";
    /**
     * You can't use bad words here.
     */
    public static final String CANT_USE_BAD_WORDS = Color.ERROR + "You can't use bad words here.";
    /**
     * You cannot use that command on yourself.
     */
    public static final String NOT_SELF = Color.ERROR + "You cannot use that command on yourself.";
    /**
     * You must be in a clan to do that.
     */
    public static final String NOT_IN_CLAN = Color.ERROR + "You must be in a clan to do that.";
    /**
     * Welcome to your new server, I hope everything goes well.
     */
    public static final String OWNER_FIRST_LOG = ChatColor.GOLD + "Welcome to your new server, I hope everything goes well.";
    /**
     * Sneak while punching if you want to break this block.
     */
    public static final String SNEAK_TO_BREAK = Color.ERROR + "Sneak while punching if you want to break this block.";
    /**
     * You can't build here.
     */
    public static final String CANT_BUILD_HERE = Color.ERROR + "You can't build here.";
    /**
     * You can't do this while that player is in jail.
     */
    public static final String PLAYER_IN_JAIL = Color.ERROR + "You can't do this while that player is in jail.";
    /**
     * You can't do that at this time.
     */
    public static final String CANT_DO_THAT_NOW = Color.ERROR + "You can't do that at this time.";
    /**
     * You cannot do this while you are in jail.
     */
    public static final String NOT_WHILE_IN_JAIL = Color.ERROR + "You cannot do this while you are in jail.";
    /**
     * Type /y to accepts this request or /n to deny it.
     */
    public static final String REQUEST_Y_N = Color.MESSAGE + "Type /y to accepts this request or /n to deny it.";
    /**
     * That player already has a request pending.
     */
    public static final String PLAYER_HAS_REQUEST = Color.ERROR + "That player already has a request pending.";
    /**
     * Your request has been sent.
     */
    public static final String REQUEST_SENT = Color.MESSAGE + "Your request has been sent.";
    /**
     * A friendly denial message when a player requests interaction with a player who is ignoring them.
     */
    public static final String PLAYER_CANT_BE_REACHED = Color.ERROR + "This player can't be reached at this time.";
    /**
     * The number you entered was invalid.
     */
    public static final String BAD_NUMBER = Color.ERROR + "The number you entered was invalid.";
    /**
     * That page number is out of range.
     */
    public static final String NO_PAGE = Color.ERROR + "That page number is out of range.";
    /**
     * There are no bounties to collect.
     */
    public static final String NO_BOUNTIES = Color.MESSAGE + "There are no bounties to collect.";
    /**
     * That warp point does not exist.
     */
    public static final String NO_WARP = Color.ERROR + "That warp point does not exist.";
    /**
     * As a member of staff be polite helpful and lead by example.
     */
    public static final String ADMIN_WELCOME = Color.MESSAGE + "As a member of staff be polite helpful and lead by example.";
    /**
     * The server is currently in lockdown please check back later.
     */
    public static final String SERVER_LOCKDOWN = "The server is currently in lockdown please check back later.";
    /**
     * No jail out warp point has been placed. Use '/setwarp jailout' to do this.
     */
    public static final String NO_JAIL_OUT = "No jail out warp point has been placed. Use '/setwarp jailout' to do this.";
    /**
     * A new player, free gifts for everyone!
     */
    public static final String NEW_PLAYER = Color.BROADCAST + "A new player, free gifts for everyone!";
    /**
     * You are now talking in clan chat.
     */
    public static final String CLAN_CHAT = Color.MESSAGE + "You are now talking in clan chat.";
    /**
     * Your request has timed out.
     */
    public static final String REQUEST_TIMEOUT = Color.MESSAGE + "Your request has timed out.";
    /**
     * You have reached the edge of the currently explorable world.
     */
    public static final String WORLD_BORDER = Color.MESSAGE + "You have reached the edge of the currently explorable world.";
    /**
     * Your time as a VIP has come to an end.
     */
    public static final String VIP_END = Color.MESSAGE + "Your time as a VIP has come to an end.";
    /**
     * Your daily item spawns have been refilled.
     */
    public static final String SPAWNS_REFILLED = Color.MESSAGE + "Your daily item spawns have been refilled.";
    /**
     * The quarries have been refilled.
     */
    public static final String QUARRIES_FILLED = Color.BROADCAST + "The quarries have been refilled.";
    /**
     * You have been banned for breaking the rules.
     */
    public static final String BAN = "You have been banned for breaking the rules.";
    /**
     * Cannot find that player.
     */
    public static final String NO_PLAYER = Color.ERROR + "Cannot find that player.";
    /**
     * You have used the wrong number of arguments.
     */
    public static final String WRONG_NUM_ARGS = Color.ERROR + "You have used the wrong number of arguments.";
    /**
     * That player is not online.
     */
    public static final String PLAYER_NOT_ONLINE = Color.ERROR + "That player is not online.";
    /**
     * You can't teleport back at this time.
     */
    public static final String BACK_FAIL = Color.ERROR + "You can't teleport back at this time.";
    /**
     * You do not have enough money to do that.
     */
    public static final String CANT_AFFORD = Color.ERROR + "You do not have enough money to do that.";
    /**
     * Thanks for accepting the rules, enjoy your stay in Minecraftopia.
     */
    public static final String ACCEPT_RULES = Color.MESSAGE + "Thanks for accepting the rules, enjoy your stay in Minecraftopia.";
    /**
     * You do not have permission to do that.
     */
    public static final String NO_PERM = Color.ERROR + "You do not have permission to do that.";
    /**
     * You are now talking in public chat.
     */
    public static final String PUBLIC_CHAT = Color.MESSAGE + "You are now talking in public chat.";
    /**
     * You are now talking in admin chat.
     */
    public static final String ADMIN_CHAT = Color.MESSAGE + "You are now talking in admin chat.";
    /**
     * Warp removed.
     */
    public static final String WARP_REMOVED = Color.MESSAGE + "Warp removed.";
    /**
     * You have been healed.
     */
    public static final String HEALED = Color.MESSAGE + "You have been healed.";
}
