package com.undeadscythes.udsplugin1;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A command that is designed to be run by a player.
 * @author UndeadScythes
 */
public abstract class PlayerCommandExecutor implements CommandExecutor {
    ExtendedPlayer player;

    /**
     * @inheritDoc
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            player = UDSPlugin.getPlayers().get(sender.getName());
            playerExecute(player, args);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the player has a permission or is opped. The permission is appended to the string "udsplugin.".
     * @param perm The permission suffix.
     * @return <code>true</code> if player has the permission or is opped, <code>false</code> otherwise.
     */
    public boolean hasPerm(String perm) {
        if(player.hasPermission("udsplugin." + perm) || player.isOp()) {
            return true;
        } else {
            player.sendMessage(Message.NO_PERM);
            return false;
        }
    }

    /**
     * Used when a player on the server executes a command.
     * @param player Player who ran the command.
     * @param args Arguements of the command.
     */
    public abstract void playerExecute(ExtendedPlayer player, String[] args);
}
