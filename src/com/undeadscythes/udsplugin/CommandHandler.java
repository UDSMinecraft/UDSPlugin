package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.commands.*;
import com.undeadscythes.udsplugin.exceptions.*;
import com.undeadscythes.udsplugin.utilities.*;
import java.util.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public abstract class CommandHandler extends Validator implements CommandExecutor {
    private String commandName;
    protected String[] args;
    protected String subCmd;
    protected MessageReciever sender;

    protected boolean subCmdEquals(final String test) {
        return subCmd.equalsIgnoreCase(test);
    }

    protected String argsToMessage() {
        return argsToMessage(0);
    }

    protected String argsToMessage(final int skip) {
        return StringUtils.join(args, " ", skip, args.length - skip);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        commandName = command.getName();
        this.args = args.clone();
        if(args.length > 0) subCmd = args[0].toLowerCase();
        if(sender instanceof Player) {
            player = MemberUtils.getOnlineMember((Player)sender);
            this.sender = player;
            if(hasPerm(Perm.valueOf(commandName.toUpperCase()))) {
                try {
                    if(executeCheck()) {
                        playerExecute();
                    }
                } catch(PlayerException ex) {
                    player.sendError(ex.getMessage());
                    Bukkit.getLogger().info("No action taken.");
                    return false;
                }
            }
            return true;
        } else if(sender instanceof ConsoleCommandSender) {
            this.sender = new ConsoleWrapper((ConsoleCommandSender)sender);
            try {
                if(executeCheck()) {
                    consoleExecute();
                }
            } catch(PlayerException ex) {
                this.sender.sendError(ex.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    protected boolean numArgsHelp(final int num) {
        if(args.length == num) return true;
        return numArgsHelp();
    }

    protected boolean minArgsHelp(final int num) {
        if(args.length >= num) return true;
        return numArgsHelp();
    }

    protected boolean maxArgsHelp(final int num) {
        if(args.length <= num) return true;
        return numArgsHelp();
    }

    private boolean numArgsHelp() {
        sender.sendError("You have made an error using this command.");
        sender.sendNormal("Use /help " + commandName + " to check the correct usage.");
        return false;
    }

    protected void subCmdHelp() {
        if(args[0].equalsIgnoreCase("help")) {
            if(args.length == 2 && args[1].matches(UDSPlugin.INT_REGEX)) {
                sendHelp(Integer.parseInt(args[1]));
            } else {
                sendHelp(1);
            }
        } else {
            sender.sendError("That is not a valid sub command.");
            sender.sendNormal("Use /" + commandName + " help to check the available sub commands.");
        }
    }

    protected void sendHelp(final int page) {
        final Usage usage = Usage.getByName(commandName);
        if(usage.isExtended()) {
            final EnumSet<Usage> extensions = EnumSet.noneOf(Usage.class);
            for(Usage extension : Usage.values()) {
                if(extension.isExtension(usage)) {
                    extensions.add(extension);
                }
            }
            HelpCmd.sendPage(page, sender, extensions, usage.cmd().replaceFirst("[a-z]", usage.cmd().substring(0, 1).toUpperCase()) + " Help");
        } else {
            sender.sendListItem(usage.getUsage(), " - " + usage.getDescription());
        }
    }

    public boolean executeCheck() throws PlayerException {
        return true;
    }

    public abstract void playerExecute() throws PlayerException;

    public void consoleExecute() throws PlayerException {
        sender.sendError("You cannot run this command from the console.");
    }
}
