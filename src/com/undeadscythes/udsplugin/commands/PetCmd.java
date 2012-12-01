package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.entity.*;

/**
 * Perform actions with pets.
 * @author UndeadScythes
 */
public class PetCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        SaveablePlayer target;
        UUID pet;
        int price;
        if(args.length == 2 && args[0].equals("give") && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && (pet = getSelectedPet()) != null) {
            for(Entity entity : player.getWorld().getEntities()) {
                if(entity.getUniqueId().equals(pet)) {
                    ((Tameable)entity).setOwner(target);
                    entity.teleport(target);
                    player.sendMessage(Color.MESSAGE + "Your pet has been sent to " + target.getDisplayName() + ".");
                    target.sendMessage(Color.MESSAGE + player.getDisplayName() + " has just sent you a pet.");
                    break;
                }
            }
        } else if(args.length == 3 && args[0].equals("sell") && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && getSelectedPet() != null && (price = parseInt(args[2])) != -1) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, Request.RequestType.PET, price, target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendMessage(Color.MESSAGE + player.getDisplayName() + " wants to sell their pet to you for " + price + " " + Config.CURRENCIES + ".");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
