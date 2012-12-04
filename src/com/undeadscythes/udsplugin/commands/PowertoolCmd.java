package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.apache.commons.lang.*;

/**
 * Set up a powertool.
 * @author UndeadScythes
 */
public class PowertoolCmd extends AbstractPlayerCommand {
    @Override
    public void playerExecute() {
        if(args.length >= 1 && notAirHand()) {
            player.setPowertoolID(player.getItemInHand().getTypeId());
            player.setPowertool(StringUtils.join(args, " ").replaceFirst("/", ""));
            player.sendMessage(Color.MESSAGE + "Powertool set.");
        } else {
            player.setPowertoolID(0);
            player.setPowertool("");
            player.sendMessage(Color.MESSAGE + "Powertool removed.");
        }
    }
}
