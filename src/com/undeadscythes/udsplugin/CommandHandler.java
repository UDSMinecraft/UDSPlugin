package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.utilities.*;
import org.apache.commons.lang.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public abstract class CommandHandler extends Validator implements CommandExecutor {
    protected ConsoleCommandSender console;
    private String commandName;
    protected String[] args;
    protected String subCmd;

    protected boolean subCmdEquals(final String test) {
        return subCmd.equalsIgnoreCase(test);
    }

    protected String argsToMessage() {
        return StringUtils.join(args, " ");
    }

    protected String argsToMessage(final int skip) {
        return StringUtils.join(args, " ", skip, args.length - skip);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender instanceof Player) {
            commandName = command.getName();
            setPlayer(PlayerUtils.getOnlinePlayer((Player)sender));
            if(hasPerm(Perm.valueOf(commandName.toUpperCase()))) {
                this.args = args.clone();
                if(args.length > 0) subCmd = args[0].toLowerCase();
                playerExecute();
            }
            return true;
        } else if(sender instanceof ConsoleCommandSender) {
            this.args = args.clone();
            if(args.length > 0) subCmd = args[0].toLowerCase();
            console = (ConsoleCommandSender)sender;
            consoleExecute();
        }
        return false;
    }

    protected boolean numArgsHelp(final int num) {
        if(args.length == num) return true;
        numArgsHelp();
        return false;
    }

    protected boolean minArgsHelp(final int num) {
        if(args.length >= num) return true;
        numArgsHelp();
        return false;
    }

    protected boolean maxArgsHelp(final int num) {
        if(args.length <= num) return true;
        numArgsHelp();
        return false;
    }

    private void numArgsHelp() {
        player().sendError("You have made an error using this command.");
        player().sendNormal("Use /help " + commandName + " to check the correct usage.");
    }

    protected void subCmdHelp() {
        if(args[0].equalsIgnoreCase("help")) {
            if(args.length == 2 && args[1].matches(UDSPlugin.INT_REGEX)) {
                sendHelp(Integer.parseInt(args[1]));
            } else {
                sendHelp(1);
            }
        } else {
            player().sendError("That is not a valid sub command.");
            player().sendNormal("Use /" + commandName + " help to check the available sub commands.");
        }
    }

    protected void sendHelp(final int page) {
        player().performCommand("help " + commandName + " " + page);
    }

    public abstract void playerExecute();

    public void consoleExecute() {
        console.sendMessage("You cannot run this command from the console.");
    }
}
