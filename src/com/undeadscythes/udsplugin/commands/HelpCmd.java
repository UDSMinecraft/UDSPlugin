package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Get help on certain commands.
 * @author UndeadScythes
 */
public class HelpCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(maxArgsHelp(2)) {
            if(args.length == 0 || (args.length == 1 && args[0].matches("[0-9][0-9]*"))) {
                sendHelpFiles();
            } else if(args.length == 1 || (args.length == 2 && args[1].matches("[0-9][0-9]*"))) {
                final Usage usage = Usage.getByName(args[0]);
                if(usage == null) {
                    player.sendMessage(Color.ERROR + "No command exists by that name.");
                } else {
                    if(usage.isExtended()) {
                        sendCommandHelp(usage);
                    } else {
                        player.sendMessage(Color.ITEM + usage.getUsage() + Color.TEXT + " - " + usage.getDescription());
                    }
                }
            }
        }
    }

    private void sendHelpFiles() {
        final Set<Usage> usages = new TreeSet<Usage>();
        for(Usage usage : Usage.values()) {
            if(player.hasPermission(usage.getPerm()) && !usage.isExtension()) {
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
        final Set<Usage> extensions = new TreeSet<Usage>();
        for(Usage extension : Usage.values()) {
            if(extension.cmd().contains(usage.cmd() + "_") && player.hasPermission(extension.getPerm()) && extension.isExtension()) {
                extensions.add(extension);
            }
        }
        if(args.length == 2) {
            sendPage(Integer.parseInt(args[1]), player, extensions, usage.cmd().replaceFirst("[a-z]", usage.cmd().substring(0, 1).toUpperCase()) + " Help");
        } else {
            sendPage(1, player, extensions, usage.cmd().replaceFirst("[a-z]", usage.cmd().substring(0, 1).toUpperCase()) + " Help");
        }
    }

    private void sendPage(final int page, final SaveablePlayer player, final Set<Usage> list, final String title) {
        final int pages = (list.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There is no help available.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- " + title + " " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(Usage usage : list) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + usage.getUsage() + Color.TEXT + " - " + usage.getDescription());
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
