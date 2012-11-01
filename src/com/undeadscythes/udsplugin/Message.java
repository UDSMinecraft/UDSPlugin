package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Messages commonly used in the plugin.
 * @author UndeadScythes
 */
public enum Message {
    /**
     * You don't have the rank required to do that.
     */
    DONT_HAVE_RANK(Color.ERROR + "You don't have the rank required to do that."),
    /**
     * You can't promote this player any further.
     */
    CANT_PROMOTE(Color.ERROR + "You can't promote this player any further."),
    /**
     * You can't demote this player any further.
     */
    CANT_DEMOTE(Color.ERROR + "You can't demote this player any further."),
    /**
     * There are no cities yet.
     */
    NO_CITIES(Color.MESSAGE + "There are no cities yet."),
    /**
     * You have no money to put in the swear jar, spend a minute in jail instead.
     */
    CANT_PAY_SWEARJAR(Color.ERROR + "You have no money to put in the swear jar."),
    /**
     * Please do not use bad language.
     */
    BAD_LANGUAGE(Color.ERROR + "Please do not use bad language."),
    /**
     * Gift sent.
     */
    GIFT_SENT(Color.MESSAGE + "Gift sent."),
    /**
     * [Gifting Service] You have recieved a free gift!
     */
    GIFT(Color.MESSAGE + "[Gifting Service] You have recieved a free gift!"),
    /**
     * You have no gift selected.
     */
    CANT_SEND_AIR(Color.ERROR + "You have no gift selected."),
    /**
     * You are not in a city.
     */
    NOT_IN_CITY(Color.ERROR + "You are not in a city."),
    /**
     * You are not in a protected area.
     */
    NOT_IN_REGION(Color.ERROR + "You are not in a protected area."),
    /**
     * No city exists by that name.
     */
    NOT_A_CITY(Color.ERROR + "No city exists by that name."),
    /**
     * You are not the mayor of that city.
     */
    NOT_MAYOR(Color.ERROR + "You are not the mayor of that city."),
    /**
     * City founded.
     */
    NEW_CITY(Color.MESSAGE + "City founded."),
    /**
     * A protected area already exists with that name.
     */
    REGION_EXISTS(Color.ERROR + "A protected area already exists with that name."),
    /**
     * You cannot do that here, you are too close to another protected area.
     */
    REGION_HAS_OVERLAP(Color.ERROR + "You cannot do that here, you are too close to another protected area."),
    /**
     * Inventory cleared.
     */
    CI(Color.MESSAGE + "Inventory cleared."),
    /**
     * That player is already dueling someone else.
     */
    PLAYER_DUELING(Color.MESSAGE + "That player is already dueling someone else."),
    /**
     * The Ender Dragon has regained his strength and awaits brave warriors in The End.
     */
    DRAGON_RESPAWN(Color.BROADCAST + "The Ender Dragon has regained his strength and awaits brave warriors in The End."),
    /**
     * You have not entered a valid rank.
     */
    NOT_A_RANK(Color.ERROR + "You have not entered a valid rank."),
    /**
     * Warp point set.
     */
    WARP_SET(Color.MESSAGE + "Warp point set."),
    /**
     * You can't use bad words here.
     */
    CANT_USE_BAD_WORDS(Color.ERROR + "You can't use bad words here."),
    /**
     * You cannot use that command on yourself.
     */
    NOT_SELF(Color.ERROR + "You cannot use that command on yourself."),
    /**
     * You must be in a clan to do that.
     */
    NOT_IN_CLAN(Color.ERROR + "You must be in a clan to do that."),
    /**
     * Welcome to your new server, I hope everything goes well.
     */
    OWNER_FIRST_LOG(ChatColor.GOLD + "Welcome to your new server, I hope everything goes well."),
    /**
     * No clan file exists yet.
     */
    NO_CLAN_FILE("No clan file exists yet."),
    /**
     * No region file exists yet.
     */
    NO_REGION_FILE("No region file exists yet."),
    /**
     * No player file exists yet.
     */
    NO_PLAYER_FILE("No player file exists yet."),
    /**
     * No warp file exists yet.
     */
    NO_WARP_FILE("No warp file exists yet."),
    /**
     * Sneak while punching if you want to break this block.
     */
    SNEAK_TO_BREAK(Color.ERROR + "Sneak while punching if you want to break this block."),
    /**
     * You can't build here.
     */
    CANT_BUILD_HERE(Color.ERROR + "You can't build here."),
    /**
     * You can't do this while that player is in jail.
     */
    PLAYER_IN_JAIL(Color.ERROR + "You can't do this while that player is in jail."),
    /**
     * You can't do that at this time.
     */
    CANT_DO_THAT_NOW(Color.ERROR + "You can't do that at this time."),
    /**
     * You cannot do this while you are in jail.
     */
    NOT_WHILE_IN_JAIL(Color.ERROR + "You cannot do this while you are in jail."),
    /**
     * Type /y to accepts this request or /n to deny it.
     */
    REQUEST_Y_N(Color.MESSAGE + "Type /y to accepts this request or /n to deny it."),
    /**
     * That player already has a request pending.
     */
    PLAYER_HAS_REQUEST(Color.ERROR + "That player already has a request pending."),
    /**
     * Your request has been sent.
     */
    REQUEST_SENT(Color.MESSAGE + "Your request has been sent."),
    /**
     * A friendly denial message when a player requests interaction with a player who is ignoring them.
     */
    PLAYER_CANT_BE_REACHED(Color.ERROR + "This player can't be reached at this time."),
    /**
     * The number you entered was invalid.
     */
    BAD_NUMBER(Color.ERROR + "The number you entered was invalid."),
    /**
     * That page number is out of range.
     */
    NO_PAGE(Color.ERROR + "That page number is out of range."),
    /**
     * There are no bounties to collect.
     */
    NO_BOUNTIES(Color.MESSAGE + "There are no bounties to collect."),
    /**
     * That warp point does not exist.
     */
    NO_WARP(Color.ERROR + "That warp point does not exist."),
    /**
     * Config loaded.
     */
    CONFIG_LOADED("Config loaded."),
    /**
     * As a member of staff be polite helpful and lead by example.
     */
    ADMIN_WELCOME(Color.MESSAGE + "As a member of staff be polite helpful and lead by example."),
    /**
     * The server is currently in lockdown please check back later.
     */
    SERVER_LOCKDOWN("The server is currently in lockdown please check back later."),
    /**
     * No jail out warp point has been placed. Use '/setwarp jailout' to do this.
     */
    NO_JAIL_OUT("No jail out warp point has been placed. Use '/setwarp jailout' to do this."),
    /**
     * A new player, free gifts for everyone!
     */
    NEW_PLAYER(Color.BROADCAST + "A new player, free gifts for everyone!"),
    /**
     * You are now talking in clan chat.
     */
    CLAN_CHAT(Color.MESSAGE + "You are now talking in clan chat."),
    /**
     * Your request has timed out.
     */
    REQUEST_TIMEOUT(Color.MESSAGE + "Your request has timed out."),
    /**
     * You have reached the edge of the currently explorable world.
     */
    WORLD_BORDER(Color.MESSAGE + "You have reached the edge of the currently explorable world."),
    /**
     * You have served your time.
     */
    JAIL_OUT(Color.MESSAGE + "You have served your time."),
    /**
     * Your time as a VIP has come to an end.
     */
    VIP_END(Color.MESSAGE + "Your time as a VIP has come to an end."),
    /**
     * Your daily item spawns have been refilled.
     */
    SPAWNS_REFILLED(Color.MESSAGE + "Your daily item spawns have been refilled."),
    /**
     * The quarries have been refilled.
     */
    QUARRIES_FILLED(Color.BROADCAST + "The quarries have been refilled."),
    /**
     * You have been banned for breaking the rules.
     */
    BAN("You have been banned for breaking the rules."),
    /**
     * Cannot find that player.
     */
    NO_PLAYER(Color.ERROR + "Cannot find that player."),
    /**
     * You have used the wrong number of arguments.
     */
    WRONG_NUM_ARGS(Color.ERROR + "You have used the wrong number of arguments."),
    /**
     * That player is not online.
     */
    PLAYER_NOT_ONLINE(Color.ERROR + "That player is not online."),
    /**
     * You can't teleport back at this time.
     */
    BACK_FAIL(Color.ERROR + "You can't teleport back at this time."),
    /**
     * You do not have enough money to do that.
     */
    CANT_AFFORD(Color.ERROR + "You do not have enough money to do that."),
    /**
     * Thanks for accepting the rules, enjoy your stay in Minecraftopia.
     */
    ACCEPT_RULES(Color.MESSAGE + "Thanks for accepting the rules, enjoy your stay in Minecraftopia."),
    /**
     * You do not have permission to do that.
     */
    NO_PERM(Color.ERROR + "You do not have permission to do that."),
    /**
     * You are now talking in public chat.
     */
    PUBLIC_CHAT(Color.MESSAGE + "You are now talking in public chat."),
    /**
     * You are now talking in admin chat.
     */
    ADMIN_CHAT(Color.MESSAGE + "You are now talking in admin chat."),
    /**
     * Timer started.
     */
    TIMER_STARTED("Timer started."),
    /**
     * Events registered.
     */
    EVENTS_REGISTERED("Events registered."),
    /**
     * Commands registered.
     */
    COMMANDS_REGISTERED("Commands registered."),
    /**
     * Recipes added.
     */
    RECIPES_ADDED("Recipes added."),
    /**
     * Warp removed.
     */
    WARP_REMOVED(Color.MESSAGE + "Warp removed."),
    /**
     * You have been healed.
     */
    HEALED(Color.MESSAGE + "You have been healed.");
    String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
