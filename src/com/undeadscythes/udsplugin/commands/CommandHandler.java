package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.utilities.*;
import org.apache.commons.lang.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * A command that is designed to be run by a player or console.
 * Methods allow various checks to be made and messages to be sent to the players on errors.
 * 
 * @author UndeadScythes
 */
public abstract class CommandHandler extends Validator implements CommandExecutor {
    private ConsoleCommandSender console;
    private String commandName;
    private String[] args;
    private String subCmd;
    
    protected final ConsoleCommandSender getConsole() {
        return console;
    }
    
    protected final String subCmd() {
        return subCmd;
    }
    
    protected final boolean subCmdEquals(final String test) {
        return subCmd.equalsIgnoreCase(test);
    }
    
    protected final String arg(final int index) {
        return args[index];
    }
    
    protected final int argsLength() {
        return args.length;
    }
    
    protected final String argsToMessage() {
        return StringUtils.join(args, " ");
    }

    protected final String argsToMessage(final int skip) {
        return StringUtils.join(args, " ", skip, args.length - skip);
    }

    /**
     * Checks player permission then passes arguments to executor.
     * 
     * @param sender Source of the command.
     * @param command The command sent.
     * @param label Command alias.
     * @param args Arguments to the command.
     * @return <code>true</code> if the commands has been handled fully.
     */
    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(sender instanceof Player) {
            commandName = command.getName();
            setPlayer(PlayerUtils.getOnlinePlayer(sender.getName()));
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

    /**
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean numArgsHelp(final int num) {
        if(args.length == num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean minArgsHelp(final int num) {
        if(args.length >= num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Check the number of arguments and send help if there are the wrong number.
     * 
     * @param num Number of arguments required.
     * @return <code>true</code> if there are the correct number of arguments.
     */
    protected final boolean maxArgsHelp(final int num) {
        if(args.length <= num) return true;
        numArgsHelp();
        return false;
    }

    /**
     * Send the player help relating to the number of arguments used.
     */
    private void numArgsHelp() {
        player().sendError("You have made an error using this command.");
        player().sendNormal("Use /help " + commandName + " to check the correct usage.");
    }

    /**
     * If the arguments are asking for help, send help otherwise advise about bad arguments.
     */
    protected final void subCmdHelp() {
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

    /**
     * Send the player a help file for the command.
     * 
     * @param page The page to display.
     */
    protected final void sendHelp(final int page) {
        player().performCommand("help " + commandName + " " + page);
    }

    /**
     * Used when a player on the server executes a command.
     */
    public abstract void playerExecute();
    
    public void consoleExecute() {
        console.sendMessage("You cannot run this command from the console.");
    }
}
