package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.enchantments.*;

/**
 * Enchant an item.
 * 
 * @author UndeadScythes
 */
public class EnchantCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        int level;
        Enchantment enchantment;
        if(argsLength() == 0) {
            sendPage(1, player());
        } else if(argsLength() == 1) {
            if(arg(0).matches("[0-9][0-9]*")) {
                sendPage(Integer.parseInt(arg(0)), player());
            } else {
                if((enchantment = getEnchantment(arg(0))) != null) {
                    player().getItemInHand().addEnchantment(enchantment, enchantment.getMaxLevel());
                }
            }
        } else if(numArgsHelp(2) && (enchantment = getEnchantment(arg(0))) != null && (level = getInteger(arg(1))) != 0 && goodEnchantLevel(enchantment, level) && isEnchantable(enchantment, player().getItemInHand())) {
            player().getItemInHand().addEnchantment(enchantment, level);
        }
    }

    /**
     * Sends a full page of enchantments to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(final int page, final SaveablePlayer player) {
        final Enchantment[] enchantments = Enchantment.values();
        final int pages = (enchantments.length + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Message.NO_ENCHANTMENTS);
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- Available Enchantments " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Enchantment enchantment : enchantments) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendListItem("- " + enchantment.getName(), "");
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
