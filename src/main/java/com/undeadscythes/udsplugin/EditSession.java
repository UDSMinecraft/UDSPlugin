package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * A WorldEdit session belonging to a player.
 * 
 * @author UndeadScythes
 */
public class EditSession extends Cuboid {
    private BlockBox clipboard = null;
    private final Stack<BlockBox> history = new Stack<BlockBox>();

    public final boolean hasClipboard() {
        return clipboard != null;
    }

    public final BlockBox getClipboard() {
        return clipboard;
    }

    public final void setClipboard(final BlockBox blocks) {
        clipboard = blocks;
    }

    public final boolean hasUndo() {
        return !history.empty();
    }

    public final BlockBox load() {
        return history.pop();
    }

    public final boolean save(final BlockBox blocks) {
        history.push(blocks);
        if(history.size() > Config.UNDO_COUNT) {
            history.remove(0);
            return true;
        }
        return false;
    }
}
