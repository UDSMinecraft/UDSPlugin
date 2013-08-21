package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * @author UndeadScythes
 */
public class GiftCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        final Member target;
        final ItemStack gift;
        if(minArgsHelp(1) && (target = matchOnlinePlayer(args[0])) != null && canGift(target) && (gift = getItemInHand().clone()) != null) {
            String message = "[Gifting Service] You have recieved a free gift!";
            if(args.length > 1) {
                message = "[Gifting Service] ".concat(argsToMessage(1));
            }
            target.sendNormal(message);
            target.giveAndDrop(gift);
            player().setItemInHand(new ItemStack(Material.AIR));
            player().sendNormal("Gift sent.");
        }
    }
}
