package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.comparators.*;
import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class BountyCmd extends CommandHandler {
    @Override
    public void playerExecute() throws TargetMatchesSenderException, NoPlayerFoundException {
        int bounty;

        OfflineMember target;
        if(args.length == 0) {
            sendPage(1, player);
        } else if(args.length == 1) {
            final int page = getInteger(args[0]);
            if(page > -1) {
                sendPage(page, player);
            }
        } else if(numArgsHelp(2) && (target = matchOtherPlayer(args[0])) != null && (bounty = canAfford(args[1])) != -1) {
            player.debit(bounty);
            target.addBounty(bounty);
            UDSPlugin.sendBroadcast(player.getNick() + " placed a bounty on " + target.getNick() + ".");
        }
    }

    private void sendPage(final int page, final Member player) {
        final List<OfflineMember> bounties = new ArrayList<OfflineMember>(0);
        final List<OfflineMember> sortedPlayers = MemberUtils.getSortedMembers(new SortByBounty());
        for(OfflineMember test : sortedPlayers) {
            if(test.getBounty() > 0) {
                bounties.add(test);
            }
        }
        final int pages = (bounties.size() + 8) / 9;
        if(pages == 0) {
            player.sendNormal("There are no bounties to collect.");
        } else if(page > pages) {
            player.sendMessage(Message.NO_PAGE);
        } else {
            player.sendNormal("--- Current Bounties " + (pages > 1 ? "Page " + page + "/" + pages + " " : "") + "---");
            int posted = 0;
            int skipped = 1;
            for(OfflineMember bounty : bounties) {
                if(skipped > (page - 1) * 9 && posted < 9) {
                    player.sendListItem(bounty.getNick() + "'s reward: ", bounty.getBounty() + " " + Config.CURRENCIES);
                    posted++;
                } else {
                    skipped++;
                }
            }
        }
    }
}
