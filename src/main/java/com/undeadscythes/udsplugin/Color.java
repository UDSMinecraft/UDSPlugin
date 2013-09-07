package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Chat colors used in various plugin messages.
 * 
 * @author UndeadScythes
 */
public enum Color {
    LINK(ChatColor.BLUE),
    SIGN(ChatColor.GREEN),
    WHISPER(ChatColor.GRAY),
    PRIVATE(ChatColor.RED),
    CLAN(ChatColor.BLUE),
    TEXT(ChatColor.WHITE),
    ITEM(ChatColor.GREEN),
    MESSAGE(ChatColor.YELLOW),
    ERROR(ChatColor.LIGHT_PURPLE),
    BROADCAST(ChatColor.DARK_RED),
    CONNECTION(ChatColor.GRAY);

    private final ChatColor color;

    private Color(final ChatColor color) {
        this.color = color;
    }

    @Override
    public final String toString() {
        return color.toString();
    }
}
