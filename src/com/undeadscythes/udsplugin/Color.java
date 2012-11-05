package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Chat colors used in messages.
 * @author UndeadScythes
 */
public enum Color {
    WHISPER(ChatColor.GRAY),
    /**
     * Chat room chat color.
     */
    PRIVATE(ChatColor.RED),
    /**
     * Chat color for clan chat.
     */
    CLAN(ChatColor.BLUE),
    /**
     * Chat color of normal text.
     */
    TEXT(ChatColor.WHITE),
    /**
     * Chat color of items in lists.
     */
    ITEM(ChatColor.GREEN),
    /**
     * Chat color for command success messages.
     */
    MESSAGE(ChatColor.YELLOW),
    /**
     * Chat color for error messages.
     */
    ERROR(ChatColor.LIGHT_PURPLE),
    /**
     * Chat color for server broadcasts.
     */
    BROADCAST(ChatColor.DARK_RED);

    ChatColor color;

    Color(ChatColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
