package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Display server rules.
 * @author UndeadScythes
 */
public class RulesCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        player.sendNormal("--- Server Rules ---");
        for(String rules : Config.SERVER_RULES) {
            player.sendText("- " + rules);
        }
    }
}
