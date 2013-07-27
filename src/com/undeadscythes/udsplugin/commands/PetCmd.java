package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.entity.*;

/**
 * Perform actions with pets.
 * @author UndeadScythes
 */
public class PetCmd extends CommandWrapper {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        UUID pet;
        int price;
        if(args.length == 2) {
            if(args[0].equals("give") && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && (pet = getSelectedPet()) != null) {
                for(Entity entity : player.getWorld().getEntities()) {
                    if(entity.getUniqueId().equals(pet)) {
                        target.setPet((Tameable)entity);
                        target.teleportHere(entity);
                        player.sendNormal("Your pet has been sent to " + target.getNick() + ".");
                        target.sendNormal(player.getNick() + " has just sent you a pet.");
                        break;
                    }
                }
            }
        } else if(numArgsHelp(3) && args[0].equals("sell") && (target = getMatchingPlayer(args[1])) != null && isOnline(target) && canRequest(target) && getSelectedPet() != null && (price = parseInt(args[2])) != -1) {
            UDSPlugin.getRequests().put(target.getName(), new Request(player, RequestType.PET, price, target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendNormal(player.getNick() + " wants to sell their pet to you for " + price + " " + UDSPlugin.getConfigString(ConfigRef.CURRENCIES) + ".");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
