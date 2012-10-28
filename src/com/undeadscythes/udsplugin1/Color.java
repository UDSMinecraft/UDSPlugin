package com.undeadscythes.udsplugin1;

import org.bukkit.ChatColor;

/**
 * Chat colors used in messages.
 * @author UndeadScythes
 */
public enum Color {
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
