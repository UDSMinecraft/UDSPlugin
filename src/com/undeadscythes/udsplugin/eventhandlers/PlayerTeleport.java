package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.io.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

/**
 * Triggered when a player teleports.
 * @author UndeadScythes
 */
public class PlayerTeleport extends ListenerWrapper implements Listener {
    private static String PATH = "plugins/UDSPlugin/inventories";
    
    @EventHandler
    public void onEvent(final PlayerTeleportEvent event) {
        final World from = event.getFrom().getWorld();
        final World to = event.getTo().getWorld();
        if(!from.equals(to) && !PlayerUtils.getOnlinePlayer(event.getPlayer().getName()).hasPermission(Perm.SHAREDINV)) {
            for(String string : UDSPlugin.getConfigStringList(ConfigRef.SHARES)) {
                List<String> shares = Arrays.asList(string.split(","));
                if(!shares.contains(from.getName()) || !shares.contains(to.getName())) {
                    try {
                        saveInventory(event.getPlayer(), from);
                        loadInventory(event.getPlayer(), to);
                    } catch (IOException ex) {
                        Bukkit.getLogger().severe("Inventory I/O failed. " + ex.getLocalizedMessage());
                    } catch (ClassNotFoundException ex) {
                        Bukkit.getLogger().severe("Inventory class failure. " + ex.getLocalizedMessage());
                    }
                }
            }
            GameMode mode = UDSPlugin.getWorldMode(to);
            if(mode != null && !PlayerUtils.getOnlinePlayer(event.getPlayer().getName()).hasPermission(Perm.ANYMODE)) {
                event.getPlayer().setGameMode(mode);
            }
        }
    }
    
    private void saveInventory(final Player player, final World world) throws IOException {
        final File file = new File(PATH + File.separator + world.getName() + ".yml");
        file.getParentFile().mkdirs();
        file.createNewFile();
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(player.getName(), player.getInventory().getContents());
        config.set(player.getName() + "-armor", player.getInventory().getArmorContents());
        config.save(file);
    }
    
    @SuppressWarnings("unchecked")
    private void loadInventory(final Player player, final World world) throws FileNotFoundException, IOException, ClassNotFoundException {
        final File file = new File(PATH + File.separator + world.getName() + ".yml");
        if(file.exists()) {
            final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            if(config.contains(player.getName()) && config.contains(player.getName() + "-armor")) {
                player.getInventory().setContents((ItemStack[])((ArrayList)config.get(player.getName())).toArray(new ItemStack[0]));
                player.getInventory().setArmorContents((ItemStack[])((ArrayList)config.get(player.getName() + "-armor")).toArray(new ItemStack[0]));
            } else {
                player.getInventory().clear();
                player.getInventory().setBoots(new ItemStack(Material.AIR));
                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                player.getInventory().setLeggings(new ItemStack(Material.AIR));
            }
        }
    }
}
