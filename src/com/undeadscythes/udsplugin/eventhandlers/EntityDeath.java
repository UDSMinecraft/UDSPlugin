package com.undeadscythes.udsplugin.eventhandlers;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class EntityDeath extends ListenerWrapper implements Listener {
    @EventHandler
    public void onEvent(final EntityDeathEvent event) {
        final Entity victim = event.getEntity();
        final EntityDamageEvent cause = victim.getLastDamageCause();
        if(!(cause instanceof EntityDamageByEntityEvent)) {
            return;
        }
        final Entity killer = getAbsoluteEntity(((EntityDamageByEntityEvent)cause).getDamager());
        if(victim instanceof EnderDragon) {
            enderKill(killer, event.getEntity().getLocation());
        } else if(victim instanceof Wither) {
            witherKill(killer, event.getEntity().getLocation());
        } else if(killer instanceof Player) {
            payReward(PlayerUtils.getOnlinePlayer((Player)killer), victim);
        } else if(killer instanceof Tameable) {
            final Tameable pet = (Tameable)killer;
            if(pet.isTamed()) {
                payReward(PlayerUtils.getOnlinePlayer((Player)(pet.getOwner())), victim);
            }
        }
    }

    private void payReward(final Member player, final Entity victim) {
        if(player.getGameMode() == GameMode.CREATIVE || player.hasGodMode()) {
            return;
        }
        final Random rng = new Random();
        final int base = (Config.MOB_REWARDS.get(victim.getType()) / 2) + 1;
        final int reward = rng.nextInt(base) + base;
        player.credit(reward);
        player.sendNormal("You picked up " + reward + " " + (reward == 1 ? Config.CURRENCY : Config.CURRENCIES) + ".");
        if(rng.nextDouble() < Config.SKULL) {
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
            final Member player = PlayerUtils.getOnlinePlayer((Player)killer);
            UDSPlugin.sendBroadcast(player.getNick() + " has dealt the killing blow to the Ender Dragon!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<Member> killers = new ArrayList<Member>(1);
        for(Player endPlayer : players) {
            PlayerUtils.getOnlinePlayer(endPlayer).sendNormal("You can use commands such as /spawn to return to the overworld.");
            if(endPlayer.getLocation().distance(location) < 100) {
                Member endKiller = PlayerUtils.getOnlinePlayer(endPlayer);
                if(endKiller.getGameMode().equals(GameMode.SURVIVAL) && !endKiller.hasGodMode()) {
                    killers.add(endKiller);
                }
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((Config.MOB_REWARDS.get(EntityType.ENDER_DRAGON) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(Member endKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                endKiller.credit(reward);
                endKiller.sendNormal("You picked up " + reward + " " + (reward == 1 ? Config.CURRENCY : Config.CURRENCIES) + ".");
            }
        }
    }

    private void witherKill(final Entity killer, final Location location) {
        if(killer instanceof Player) {
            final Member player = PlayerUtils.getOnlinePlayer((Player)killer);
            UDSPlugin.sendBroadcast(player.getNick() + " has dealt the killing blow to the Wither!");
        }
        final Collection<Player> players = location.getWorld().getEntitiesByClass(Player.class);
        final List<Member> killers = new ArrayList<Member>(1);
        for(Player witherPlayer : players) {
            if(witherPlayer.getLocation().distance(location) < 100) {
                Member witherKiller = PlayerUtils.getOnlinePlayer(witherPlayer);
                if(witherKiller.getGameMode().equals(GameMode.SURVIVAL) && !witherKiller.hasGodMode()) {
                    killers.add(witherKiller);
                }
            }
        }
        if(!killers.isEmpty()) {
            final int split = ((Config.MOB_REWARDS.get(EntityType.WITHER) / killers.size()) / 2) + 1;
            final Random generator = new Random();
            for(Member witherKiller : killers) {
                final int reward = generator.nextInt(split) + split;
                witherKiller.credit(reward);
                witherKiller.sendNormal("You picked up " + reward + " " + (reward == 1 ? Config.CURRENCY : Config.CURRENCIES) + ".");
            }
        }
    }
}
