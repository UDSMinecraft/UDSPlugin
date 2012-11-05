package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Request.Type;
import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * Accept a request.
 * @author UndeadScythes
 */
public class YCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        Request request;
        if(argsEq(0) && (request = hasRequest()) != null) {
            SaveablePlayer sender = request.getSender();
            int price;
            if(sender.isOnline()) {
                if(request.getType().equals(Type.TP)) {
                    sender.teleport(player);
                } else if(request.getType().equals(Type.CLAN)) {
                    Clan clan = UDSPlugin.getClans().get(request.getData());
                    clan.addMember(player.getName());
                    clan.sendMessage(player.getDisplayName() + " has joined the clan.");
                    Region base;
                    if((base = UDSPlugin.getBases().get(clan.getName() + "base")) != null) {
                        base.addMember(player.getName());
                    }
                } else if(request.getType().equals(Type.HOME) && canAfford(price = Integer.parseInt(request.getData())) && noHome()) {
                    Region home = UDSPlugin.getHomes().get(sender.getName() + "home");
                    home.clearMembers();
                    home.changeOwner(player.getName());
                    player.debit(price);
                    sender.credit(price);
                } else if(request.getType().equals(Type.PET) && canAfford(price = Integer.parseInt(request.getData()))) {
                    for(Entity entity : sender.getWorld().getEntities()) {
                        if(entity.getUniqueId().equals(sender.getSelectedPet())) {
                            player.debit(Integer.parseInt(request.getData()));
                            sender.credit(Integer.parseInt(request.getData()));
                            ((Tameable)entity).setOwner(player);
                            entity.teleport(player);
                            player.sendMessage(Color.MESSAGE + "Your bought " + sender.getDisplayName() + "'s pet.");
                            sender.sendMessage(Color.MESSAGE + player.getDisplayName() + " bought your pet.");
                        }
                    }
                } else if(request.getType().equals(Type.PVP) && canAfford(price = Integer.parseInt(request.getData()))) {
                    sender.sendMessage(Color.MESSAGE + player.getDisplayName() + " accepted your duel, may the best player win.");
                    player.sendMessage(Color.MESSAGE + "Duel accepted, may the best player win.");
                    player.setChallenger(sender);
                    sender.setChallenger(sender);
                    player.setWager(price);
                    sender.setWager(price);
                } else if(request.getType().equals(Type.SHOP) && canAfford(price = Integer.parseInt(request.getData())) && noShop()) {
                    Region shop = UDSPlugin.getShops().get(sender.getName() + "shop");
                    shop.clearMembers();
                    shop.changeOwner(player.getName());
                    player.debit(price);
                    sender.credit(price);
                } else {
                    sender.sendMessage(Color.MESSAGE + player.getDisplayName() + " was unable to accept your request.");
                }
            } else {
                player.sendMessage(Color.MESSAGE + "The player who sent this request is no longer online.");
            }
            UDSPlugin.getRequests().remove(player.getName());
        }
    }
}
