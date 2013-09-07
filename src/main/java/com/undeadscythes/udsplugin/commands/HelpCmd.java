package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.Usage;
import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class HelpCmd extends CommandHandler {
    public static void sendPage(final int page, final MessageReciever player, final Set<Usage> list, final String title) {
        final int pages = (list.size() + 8) / 9;
        if(pages == 0) {
            player.sendNormal("There is no help available.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- " + title + " " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Usage usage : list) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendListItem(usage.getUsage(), " - " + usage.getDescription());
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }

    @Override
    public void playerExecute() {
        if(maxArgsHelp(2)) {
            if(args.length == 0 || (args.length == 1 && args[0].matches("[0-9][0-9]*"))) {
                sendHelpFiles();
            } else if(args.length == 1 || (args.length == 2 && args[1].matches("[0-9][0-9]*"))) {
                final Usage usage = Usage.getByName(args[0]);
                if(usage == null) {
                    player.sendError("No command exists by that name.");
                } else {
                    if(usage.isExtended()) {
                        sendCommandHelp(usage);
                    } else {
                        player.sendListItem(usage.getUsage(), " - " + usage.getDescription());
                    }
                }
            }
        }
    }

    private void sendHelpFiles() {
        final EnumSet<Usage> usages = EnumSet.noneOf(Usage.class);
        for(Usage usage : Usage.values()) {
            if(player.hasPerm(usage.getPerm()) && !usage.isExtension() && (usage.getPerm().getMode() == null || UDSPlugin.getWorldMode(player.getWorld()).equals(usage.getPerm().getMode()))) {
                usages.add(usage);
            }
        }
        if(args.length == 1) {
            sendPage(Integer.parseInt(args[0]), player, usages, "Help");
        } else {
            sendPage(1, player, usages, "Help");
        }
    }

    private void sendCommandHelp(final Usage usage) {
        final EnumSet<Usage> extensions = EnumSet.noneOf(Usage.class);
        for(Usage extension : Usage.values()) {
            if(player.hasPerm(extension.getPerm()) && extension.isExtension(usage)) {
                extensions.add(extension);
            }
        }
        if(args.length == 2) {
            sendPage(Integer.parseInt(args[1]), player, extensions, usage.cmd().replaceFirst("[a-z]", usage.cmd().substring(0, 1).toUpperCase()) + " Help");
        } else {
            sendPage(1, player, extensions, usage.cmd().replaceFirst("[a-z]", usage.cmd().substring(0, 1).toUpperCase()) + " Help");
        }
    }
}
