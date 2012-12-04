package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

/**
 * Send another player a gift.
 * @author UndeadScythes
 */
public class GiftCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        SaveablePlayer target;
        if(minArgsHelp(1) && (target = getMatchingPlayer(args[0])) != null) {
            if(notAirHand()) {
                final ItemStack gift = player.getItemInHand().clone();
                String message = Color.MESSAGE + "[Gifting Service] You have recieved a free gift!";
                if(args.length > 1) {
                    message = Color.MESSAGE + "[Gifting Service] ".concat(StringUtils.join(args, " ", 1, args.length - 1));
                }
                target.sendMessage(message);
                target.giveAndDrop(gift);
                player.setItemInHand(new ItemStack(Material.AIR));
                player.sendMessage(Color.MESSAGE + "Gift sent.");
            }
        }
    }
}
