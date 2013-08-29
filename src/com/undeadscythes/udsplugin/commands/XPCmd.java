package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.exceptions.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class XPCmd extends CommandHandler {
    @Override
    public void playerExecute() throws PlayerNotOnlineException {
        if(numArgsHelp(2)) {
            Member target = matchOnlinePlayer(args[0]);
            if(args[1].matches("[0-9][0-9]*")) {
                for(int i = 0; i < 10; i++) {
                    target.getWorld().spawnEntity(target.getLocation(), EntityType.EXPERIENCE_ORB);
                }
                target.giveExpLevels(Integer.parseInt(args[1]));
                player.sendNormal(target.getNick() + " has been granted experience.");
            } else if(args[1].matches("reset")) {
                target.setExp(0);
                target.setLevel(0);
                player.sendNormal(target.getNick() + "'s experience has been reset.");
            } else {
                subCmdHelp();
            }
        }
    }
}
