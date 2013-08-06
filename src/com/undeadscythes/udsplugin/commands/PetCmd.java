package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.entity.*;

/**
 * Perform actions with pets.
 * 
 * @author UndeadScythes
 */
public class PetCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        SaveablePlayer target;
        UUID pet;
        int price;
        if(argsLength() == 2) {
            if(arg(0).equals("give") && (target = matchOnlinePlayer(arg(1))) != null && (pet = getPetId()) != null) {
                for(Entity entity : player().getWorld().getEntities()) {
                    if(entity.getUniqueId().equals(pet)) {
                        target.setPet((Tameable)entity);
                        target.teleportHere(entity);
                        player().sendNormal("Your pet has been sent to " + target.getNick() + ".");
                        target.sendNormal(player().getNick() + " has just sent you a pet.");
                        break;
                    }
                }
            }
        } else if(numArgsHelp(3) && arg(0).equals("sell") && (target = matchOnlinePlayer(arg(1))) != null && canRequest(target) && getPetId() != null && (price = getInteger(arg(2))) != -1) {
            UDSPlugin.addRequest(target.getName(), new Request(player(), RequestType.PET, price, target));
            player().sendMessage(Message.REQUEST_SENT);
            target.sendNormal(player().getNick() + " wants to sell their pet to you for " + price + " " + Config.CURRENCIES + ".");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
