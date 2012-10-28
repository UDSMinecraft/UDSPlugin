package com.undeadscythes.udsplugin1;

/**
 * Messages commonly used in the plugin.
 * @author UndeadScythes
 */
public enum Message {
    /**
     * That warp point does not exist.
     */
    NO_WARP("That warp point does not exist."),
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
