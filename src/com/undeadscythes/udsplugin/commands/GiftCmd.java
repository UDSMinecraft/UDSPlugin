package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Send another player a gift.
 * 
 * @author UndeadScythes
 */
public class GiftCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        final SaveablePlayer target;
        final ItemStack gift;
        if(minArgsHelp(1) && (target = matchPlayer(arg(0))) != null && canGift(target) && (gift = getItemInHand().clone()) != null) {
            String message = "[Gifting Service] You have recieved a free gift!";
            if(argsLength() > 1) {
                message = "[Gifting Service] ".concat(argsToMessage(1));
            }
            target.sendNormal(message);
            target.giveAndDrop(gift);
            player().setItemInHand(new ItemStack(Material.AIR));
            player().sendNormal("Gift sent.");
        }
    }
}
