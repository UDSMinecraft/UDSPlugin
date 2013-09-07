package com.undeadscythes.udsplugin;

import org.bukkit.block.*;

/**
 * Low memory impact block.
 * 
 * @author UndeadScythes
 */
public class MiniBlock {
    private final int type;
    private final byte data;

    public MiniBlock(final Block block) {
        type = block.getTypeId();
        data = block.getData();
    }

    public void replace(final Block block) {
        block.setTypeIdAndData(type, data, true);
    }  
}
