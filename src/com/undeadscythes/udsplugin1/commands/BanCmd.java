package com.undeadscythes.udsplugin1.commands;

import com.undeadscythes.udsplugin1.Color;
import com.undeadscythes.udsplugin1.ExtendedPlayer;
import com.undeadscythes.udsplugin1.Message;
import com.undeadscythes.udsplugin1.PlayerCommandExecutor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

/**
 * Ban a player from the server.
 * @author UndeadScythes
 */
public class BanCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(ExtendedPlayer player, String[] args) {
        if(args.length > 0) {
            ExtendedPlayer target;
            if(hasPerm("ban") && (target = matchesPlayer(args[0])) != null) {
                String message = Message.BAN + "";
                if(args.length > 2) {
                    message = StringUtils.join(args, " ", 2, args.length - 1);
                }
                if(isOnline(target)) {
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    target.kickPlayer(message);
                    target.setBanned(true);
                } else {
                    target.setBanned(true);
                }
                Bukkit.broadcastMessage(Color.BROADCAST + target.getDisplayName() + " has been banned for breaking the rules.");
            }
        }
    }

}
