package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * @author UndeadScythes
 */
public class AcceptRulesCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(canAfford(Config.BUILD_COST)) {
            player.setRank(MemberRank.MEMBER);
            player.debit(Config.BUILD_COST);
            UDSPlugin.sendBroadcast(player.getNick() + " has accepted the rules.");
            player.sendNormal("Thanks for accepting the rules, enjoy your stay on " + Bukkit.getServerName() + ".");
            MemberUtils.saveMember(player);
        }
    }
}
