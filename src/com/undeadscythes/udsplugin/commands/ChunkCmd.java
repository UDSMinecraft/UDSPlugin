package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.text.*;
import java.util.*;
import org.bukkit.*;

/**
 * Handles various chunk related commands.
 * @author UndeadScythes
 */
public class ChunkCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(numArgsHelp(1)) {
            if(arg(0).equalsIgnoreCase("info")) {
                final Chunk chunk = player().getLocation().getChunk();
                final EnumMap<Material, Integer> blockDistro = new EnumMap<Material, Integer>(Material.class);
                int blockCount = 0;
                for(int x = 0; x < 15; x++) {
                    for(int y = 0; y < UDSPlugin.BUILD_LIMIT; y++) {
                        for(int z = 0; z < 15; z++) {
                            final Material material = chunk.getBlock(x, y, z).getType();
                            if(!material.equals(Material.AIR)) {
                                if(blockDistro.containsKey(material)) {
                                    blockDistro.put(material, blockDistro.get(material) + 1);
                                } else {
                                    blockDistro.put(material, 1);
                                }
                                blockCount++;
                            }
                        }
                    }
                }
                player().sendNormal("Chunk contents:");
                DecimalFormat df = new DecimalFormat("###.##");
                for(Map.Entry<Material, Integer> entry : blockDistro.entrySet()) {
                    player().sendListItem(entry.getKey().toString() + ": " + entry.getValue() + ", " + df.format(((double)entry.getValue() * 100) / (double)blockCount) + "%", "");
                }
            }
        }
    }
}
