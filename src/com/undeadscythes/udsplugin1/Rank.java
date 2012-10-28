package com.undeadscythes.udsplugin1;

import org.bukkit.ChatColor;

/**
 * A player rank granting permission.
 * @author UndeadScythes
 */
public enum Rank {
    /**
     * Basic player rank.
     */
    DEFAULT(ChatColor.WHITE),
    /**
     * Player with build rights.
     */
    MEMBER(ChatColor.GREEN),
    /**
     * Donating or long term player.
     */
    VIP(ChatColor.DARK_PURPLE),
    /**
     * Trustee.
     */
    WARDEN(ChatColor.AQUA),
    /**
     * Player moderator.
     */
    MOD(ChatColor.DARK_AQUA),
    /**
     * Server administrator.
     */
    ADMIN(ChatColor.YELLOW),
    /**
     * Server owner.
     */
    OWNER(ChatColor.GOLD);

    ChatColor color;

    Rank(ChatColor color) {
        this.color = color;
    }

    public ChatColor color() {
        return color;
    }
}
