package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Allows a player to promote themselves from {@link PlayerRank#NEWBIE} to
 * {@link PlayerRank#MEMBER} using in game currency.
 * 
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(canAfford(Config.BUILD_COST)) {
            player().setRank(PlayerRank.MEMBER);
            player().debit(Config.BUILD_COST);
            UDSPlugin.sendBroadcast(player().getNick() + " has accepted the rules.");
            player().sendNormal("Thanks for accepting the rules, enjoy your stay on " + Bukkit.getServerName() + ".");
        }
    }
}
