package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

/**
 * Fired when an entity dies.
 * @author UndeadScythes
 */
public class EntityDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public final void onEvent(final EntityDeathEvent event) {
        final Entity victim = event.getEntity();
        final EntityDamageEvent cause = victim.getLastDamageCause();
        if(!(cause instanceof EntityDamageByEntityEvent)) return;
        final Entity killer = getAbsoluteEntity(((EntityDamageByEntityEvent)cause).getDamager());
        if(victim instanceof EnderDragon) {
            enderKill(killer, event.getEntity().getLocation());
        } else if(victim instanceof Wither) {
            witherKill(killer, event.getEntity().getLocation());
        } else if(killer instanceof Player) {
            payReward(PlayerUtils.getOnlinePlayer(((Player)killer).getName()), victim);
        } else if(killer instanceof Tameable) {
            final Tameable pet = (Tameable)killer;
            if(pet.isTamed()) {
                payReward(PlayerUtils.getOnlinePlayer(((Player)pet.getOwner()).getName()), victim);
            }
        }
    }

    private void payReward(final SaveablePlayer player, final Entity victim) {
        if(player.getGameMode() == GameMode.CREATIVE || player.hasGodMode()) return;
        final Random rng = new Random();
        final int base = (UDSPlugin.getMobReward(victim.getType()) / 2) + 1;
        final int reward = rng.nextInt(base) + base;
        player.credit(reward);
        player.sendNormal("You picked up " + reward + " " + (reward == 1 ? UDSPlugin.getConfigString(ConfigRef.CURRENCY) : UDSPlugin.getConfigString(ConfigRef.CURRENCIES)) + ".");
        if(rng.nextDouble() < UDSPlugin.getConfigDouble(ConfigRef.SKULL)) {
            ItemStack skull;
            if(victim.getType().equals(EntityType.ZOMBIE)) {
                skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.ZOMBIE.ordinal());
            } else if(victim.getType().equals(EntityType.ZOMBIE)) {
                skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.SKELETON.ordinal());
            } else if(victim.getType().equals(EntityType.ZOMBIE)) {
                skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.CREEPER.ordinal());
            } else {
                return;
            }
            player.getWorld().dropItemNaturally(victim.getLocation(), skull);
        }
    }

    private void enderKill(final Entity killer, final Location location) {
        UDSPlugin.getData().setLastEnderDeath(System.currentTimeMillis());
        if(killer instanceof Player) {
            final SaveablePlayer player = PlayerUtils.getPlayer(((Player)killer).getName());
            UDSPlugin.sendBroadcast(player.getNick() + " has dealt the killing blow to the Ender Dragon!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<SaveablePlayer> killers = new ArrayList<SaveablePlayer>();
        for(Player endPlayer : players) {
            PlayerUtils.getPlayer(endPlayer.getName()).sendNormal("You can use commands such as /spawn to return to the overworld.");
            if(endPlayer.getLocation().distance(location) < 100) {
                SaveablePlayer endKiller = PlayerUtils.getPlayer((endPlayer).getName());
                if(endKiller.getGameMode().equals(GameMode.SURVIVAL) && !endKiller.hasGodMode()) {
                    killers.add(endKiller);
                }
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((UDSPlugin.getMobRewards().get(EntityType.ENDER_DRAGON) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(SaveablePlayer endKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                endKiller.credit(reward);
                PlayerUtils.getPlayer(endKiller.getName()).sendNormal("You picked up " + reward + " " + (reward == 1 ? UDSPlugin.getConfigString(ConfigRef.CURRENCY) : UDSPlugin.getConfigString(ConfigRef.CURRENCIES)) + ".");
            }
        }
    }

    private void witherKill(final Entity killer, final Location location) {
        if(killer instanceof Player) {
            final SaveablePlayer player = PlayerUtils.getPlayer(((Player)killer).getName());
            UDSPlugin.sendBroadcast(player.getNick() + " has dealt the killing blow to the Wither!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<SaveablePlayer> killers = new ArrayList<SaveablePlayer>();
        for(Player witherPlayer : players) {
            if(witherPlayer.getLocation().distance(location) < 100) {
                SaveablePlayer witherKiller = PlayerUtils.getPlayer((witherPlayer).getName());
                if(witherKiller.getGameMode().equals(GameMode.SURVIVAL) && !witherKiller.hasGodMode()) {
                    killers.add(witherKiller);
                }
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((UDSPlugin.getMobRewards().get(EntityType.WITHER) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(SaveablePlayer witherKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                witherKiller.credit(reward);
                PlayerUtils.getPlayer(witherKiller.getName()).sendNormal("You picked up " + reward + " " + (reward == 1 ? UDSPlugin.getConfigString(ConfigRef.CURRENCY) : UDSPlugin.getConfigString(ConfigRef.CURRENCIES)) + ".");
            }
        }
    }
}
