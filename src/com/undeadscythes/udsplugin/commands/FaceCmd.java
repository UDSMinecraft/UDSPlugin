package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import org.bukkit.*;

/**
 * Face a player in a certain direction or get the direction a player is facing.
 * @author UndeadScythes
 */
public class FaceCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Direction direction;
        if(argsLength() == 0) {
            player().sendNormal("You are facing " + Direction.valueOf(player().getLocation()).toString() + ".");
        } else if(numArgsHelp(1) && (direction = directionExists(arg(0))) != null) {
            final Location location = player().getLocation();
            location.setYaw(direction.getYaw());
            player().teleport(location);
        }
    }
}
