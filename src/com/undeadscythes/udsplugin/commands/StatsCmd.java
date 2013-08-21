package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.logging.*;

/**
 * @author UndeadScythes
 */
public class StatsCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(args.length == 0) {
            sendStats(player());
        } else if(numArgsHelp(1) && (target = matchPlayer(args[0])) != null) {
            sendStats(target);
        }
    }

    private void sendStats(final Member target) {
        player().sendNormal(target.getNick() + "'s stats:");
        player().sendText("Minecraft name: " + target.getName());
        player().sendText("Rank: " + target.getRankColor() + target.getRankName());
        try {
            player().sendText("Clan: " + target.getClan().getName());
        } catch (NoMetadataSetException ex) {
            player().sendText("Clan: not in clan");
        }
        player().sendText("Current bounty: " + target.getBounty());
        try {
            player().sendText("Current level: " + PlayerUtils.getOnlinePlayer(target).getLevel());
        } catch (PlayerNotOnlineException ex) {
            Logger.getLogger(StatsCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        player().sendText("Time logged: " + target.getTimeLogged());
        try {
            player().sendText("Last seen: " + (!target.isHidden() ? "online now in world " + PlayerUtils.getOnlinePlayer(target).getWorld().getName() : target.getLastSeen()));
        } catch (PlayerNotOnlineException ex) {
            player().sendText("Last seen: " + target.getLastSeen());
        }
    }
}
