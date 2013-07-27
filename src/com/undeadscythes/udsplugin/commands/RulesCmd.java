package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Display server rules.
 * @author UndeadScythes
 */
public class RulesCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        player.sendNormal("--- Server Rules ---");
        for(String rules : UDSPlugin.getConfigStringList(ConfigRef.SERVER_RULES)) {
            player.sendText("- " + rules);
        }
    }
}
