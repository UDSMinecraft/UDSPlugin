package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Send another player a gift.
 * @author UndeadScythes
 */
public class GiftCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            if(isHoldingItem()) {
                final ItemStack gift = player.getItemInHand().clone();
                String message = "[Gifting Service] You have recieved a free gift!";
                if(args.length > 1) {
                    message = "[Gifting Service] ".concat(StringUtils.join(args, " ", 1, args.length - 1));
                }
                target.sendNormal(message);
                target.giveAndDrop(gift);
                player.setItemInHand(new ItemStack(Material.AIR));
                player.sendNormal("Gift sent.");
            }
        }
    }
}
