package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

/**
 * When an entity dies.
 * @author UndeadScythes
 */
public class EntityDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityDeathEvent event) {
        final Entity victim = event.getEntity();
        final EntityDamageEvent damageEvent = victim.getLastDamageCause();
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            final Entity killer = getAbsoluteEntity(((EntityDamageByEntityEvent)damageEvent).getDamager());
            if(victim instanceof EnderDragon) {
                enderKill(killer, event.getEntity().getLocation());
            } else if(victim instanceof Wither) {
                witherKill(killer, event.getEntity().getLocation());
            } else if(killer instanceof Player) {
                payReward(UDSPlugin.getPlayers().get(((Player)killer).getName()), victim);
            } else if(killer instanceof Tameable) {
                final Tameable pet = (Tameable)killer;
                if(pet.isTamed()) {
                    payReward(UDSPlugin.getPlayers().get(((Player)pet.getOwner()).getName()), victim);
                }
            }
        }
    }

    private void payReward(final SaveablePlayer player, final Entity victim) {
        if(player.getGameMode() == GameMode.SURVIVAL && !player.hasGodMode()) {
            final Random generator = new Random();
            final int standard = (Config.mobRewards.get(victim.getType()) / 2) + 1;
            final int reward = generator.nextInt(standard) + standard;
            player.credit(reward);
            player.sendMessage(Color.MESSAGE + "You picked up " + reward + " " + (reward == 1 ? Config.currency : Config.currencies) + ".");
        }
    }

    private void enderKill(final Entity killer, final Location location) {
        UDSPlugin.getData().setLastEnderDeath(System.currentTimeMillis());
        if(killer instanceof Player) {
            final SaveablePlayer player = UDSPlugin.getPlayers().get(((Player)killer).getName());
            Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " has dealt the killing blow to the Ender Dragon!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<SaveablePlayer> killers = new ArrayList<SaveablePlayer>();
        for(Player endPlayer : players) {
            if(endPlayer.getLocation().distance(location) < 100) {
                killers.add(UDSPlugin.getPlayers().get((endPlayer).getName()));
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((Config.mobRewards.get(EntityType.ENDER_DRAGON) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(SaveablePlayer endKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                endKiller.credit(reward);
                endKiller.sendMessage(Color.MESSAGE + "You picked up " + reward + " " + (reward == 1 ? Config.currency : Config.currencies) + ".");
            }
        }
    }

    private void witherKill(final Entity killer, final Location location) {
        if(killer instanceof Player) {
            final SaveablePlayer player = UDSPlugin.getPlayers().get(((Player)killer).getName());
            Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " has dealt the killing blow to the Wither!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<SaveablePlayer> killers = new ArrayList<SaveablePlayer>();
        for(Player witherPlayer : players) {
            if(witherPlayer.getLocation().distance(location) < 100) {
                killers.add(UDSPlugin.getPlayers().get((witherPlayer).getName()));
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((Config.mobRewards.get(EntityType.WITHER) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(SaveablePlayer witherKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                witherKiller.credit(reward);
                witherKiller.sendMessage(Color.MESSAGE + "You picked up " + reward + " " + (reward == 1 ? Config.currency : Config.currencies) + ".");
            }
        }
    }
}
