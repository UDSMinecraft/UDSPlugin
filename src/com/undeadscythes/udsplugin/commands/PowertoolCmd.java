package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;

/**
 * Set up a powertool.
 * 
 * @author UndeadScythes
 */
public class PowertoolCmd extends CommandHandler {
    @Override
    public final void playerExecute() {
        if(args.length >= 1 && getItemInHand() != null) {
            player().setPowertoolID(player().getItemInHand().getTypeId());
            player().setPowertool(argsToMessage().replaceFirst("/", ""));
            player().sendNormal("Powertool set.");
        } else {
            player().setPowertoolID(0);
            player().setPowertool("");
            player().sendNormal("Powertool removed.");
        }
    }
}
