package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import org.bukkit.*;

/**
 * Place and check bounties on players.
 * @author UndeadScythes
 */
public class BountyCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute(final SaveablePlayer player, final String[] args) {
        int bounty;
        int page;
        SaveablePlayer target;
        if(args.length == 0) {
            sendPage(1, player);
        } else if(args.length == 1) {
            if((page = parseInt(args[0])) != -1) {
                sendPage(page, player);
            }
        } else if(numArgsHelp(2) && (target = getMatchingOtherPlayer(args[0])) != null && (bounty = getAffordablePrice(args[1])) != -1) {
            player.debit(bounty);
            target.addBounty(bounty);
            Bukkit.broadcastMessage(Color.BROADCAST + player.getNick() + " placed a bounty on " + target.getNick() + ".");
        }
    }

    /**
     * Sends a full page of bounties to the player.
     * @param page Page to send.
     * @param player Player to send page to.
     */
    private void sendPage(int page, SaveablePlayer player) {
        ArrayList<SaveablePlayer> bounties = new ArrayList<SaveablePlayer>();
        for(SaveablePlayer test : UDSPlugin.getPlayers().getSortedValues(new SortByBounty())) {
            if(test.getBounty() > 0) {
                bounties.add(test);
            }
        }
        int pages = (bounties.size() + 8) / 9;
        if(pages == 0) {
            player.sendMessage(Color.MESSAGE + "There are no bounties to collect.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendMessage(Color.MESSAGE + "--- Current Bounties " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(SaveablePlayer bounty : bounties) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendMessage(Color.ITEM + "- " + bounty.getNick() + "'s reward: " + Color.TEXT + bounty.getBounty() + " " + Config.currencies);
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}

class SortByBounty implements Comparator<SaveablePlayer> {
    @Override
    public int compare(SaveablePlayer player1, SaveablePlayer player2) {
        return player2.getBounty() - player1.getBounty();
    }
}
