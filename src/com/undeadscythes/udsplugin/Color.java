package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * Chat colors used in various plugin messages.
 * @author UndeadScythes
 */
public enum Color {
    /*
     * Color for hypertext messages.
     */
    LINK(ChatColor.BLUE),
    /*
     * Color for special signs.
     */
    SIGN(ChatColor.GREEN),
    /*
     * Color for private messages.
     */
    WHISPER(ChatColor.GRAY),
    /*
     * Color for chat room messages.
     */
    PRIVATE(ChatColor.RED),
    /*
     * Color for clan messages.
     */
    CLAN(ChatColor.BLUE),
    /*
     * Color for regular text.
     */
    TEXT(ChatColor.WHITE),
    /*
     * Color for bullet point style lists.
     */
    ITEM(ChatColor.GREEN),
    /*
     * Color for messages to players.
     */
    MESSAGE(ChatColor.YELLOW),
    /*
     * Color for error messages to players.
     */
    ERROR(ChatColor.LIGHT_PURPLE),
    /*
     * Color for server wide broadcasts.
     */
    BROADCAST(ChatColor.DARK_RED),
    /*
     * Color for player connection related messages.
     */
    CONNECTION(ChatColor.GRAY);

    private final ChatColor color;

    private Color(final ChatColor color) {
        this.color = color;
    }

    /*
     * Implicitly called when a Color is used in a string builder.
     */
    @Override
    public final String toString() {
        return color.toString();
    }
}
