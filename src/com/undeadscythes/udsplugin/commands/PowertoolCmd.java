package com.undeadscythes.udsplugin.commands;

/**
 * Set up a powertool.
 * @author UndeadScythes
 */
public class PowertoolCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(argsLength() >= 1 && isHoldingItem() != null) {
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
