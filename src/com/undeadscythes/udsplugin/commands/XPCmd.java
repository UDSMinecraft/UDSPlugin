package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.CommandHandler;
import com.undeadscythes.udsplugin.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public class XPCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        Member target;
        if(numArgsHelp(2) && (target = matchOnlinePlayer(args[0])) != null) {
            if(args[1].matches("[0-9][0-9]*")) {
                for(int i = 0; i < 10; i++) {
                    target.getWorld().spawnEntity(target.getLocation(), EntityType.EXPERIENCE_ORB);
                }
                target.giveExpLevels(Integer.parseInt(args[1]));
                player().sendNormal(target.getNick() + " has been granted experience.");
            } else if(args[1].matches("reset")) {
                target.setExp(0);
                target.setLevel(0);
                player().sendNormal(target.getNick() + "'s experience has been reset.");
            } else {
                subCmdHelp();
            }
        }
    }
}
