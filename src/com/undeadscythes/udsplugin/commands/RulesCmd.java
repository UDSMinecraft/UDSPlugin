package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Display server rules.
 * @author UndeadScythes
 */
public class RulesCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        player.sendMessage(Color.MESSAGE + "--- Server Rules ---");
        for(String rules : Config.SERVER_RULES) {
            player.sendMessage(Color.TEXT + "- " + rules);
        }
    }
}
