package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.requests.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class PetCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        Member target;
        UUID pet;
        int price;
        if(args.length == 2) {
            if(args[0].equals("give") && (target = matchOnlinePlayer(args[1])) != null && (pet = getPetId()) != null) {
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
        } else if(numArgsHelp(3) && args[0].equals("sell") && (target = matchOnlinePlayer(args[1])) != null && canRequest(target) && getPetId() != null && (price = getInteger(args[2])) != -1) {
            UDSPlugin.addRequest(target.getName(), new Request(player, RequestType.PET, price, target));
            player.sendMessage(Message.REQUEST_SENT);
            target.sendNormal(player.getNick() + " wants to sell their pet to you for " + price + " " + Config.CURRENCIES + ".");
            target.sendMessage(Message.REQUEST_Y_N);
        }
    }
}
