package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A WorldEdit session belonging to a player.
 * @author UndeadScythes
 */
public class EditSession extends Cuboid {
    private BlockBox clipboard = null;
    private final Stack<BlockBox> history = new Stack<BlockBox>();

    /**
     * Check if the session has anything saved to the clipboard.
     * @return <code>true</code> if the clipboard is not empty, <code>false</code> otherwise.
     */
    public final boolean hasClipboard() {
        return clipboard != null;
    }

    /**
     * Get the clipboard contents.
     * @return Clipboard contents.
     */
    public final BlockBox getClipboard() {
        return clipboard;
    }

    /**
     * Set the clipboard contents.
     * @param blocks Clipboard contents.
     */
    public final void setClipboard(final BlockBox blocks) {
        clipboard = blocks;
    }

    /**
     * Check if this session has anything on the undo stack.
     * @return <code>true</code> if the undo stack is not empty, <code>false</code> otherwise.
     */
    public final boolean hasUndo() {
        return !history.empty();
    }

    /**
     * Get the next item on the undo stack.
     * @return Next undo item.
     */
    public final BlockBox load() {
        return history.pop();
    }

    /**
     * Add an item to the undo stack.
     * When the stack is full, as defined in the config.yml, the first element of the stack is dropped.
     * @param blocks Item to add to the undo stack.
     * @return <code>true</code> if the undo stack is full, <code>false</code> otherwise.
     */
    public final boolean save(final BlockBox blocks) {
        history.push(blocks);
        if(history.size() > Config.UNDO_COUNT) {
            history.remove(0);
            return true;
        }
        return false;
    }
}
