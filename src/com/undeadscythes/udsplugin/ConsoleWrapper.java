package com.undeadscythes.udsplugin;

import org.bukkit.command.*;

/**
 * @author UndeadScythes
 */
public class ConsoleWrapper extends MessageReciever {
    protected ConsoleCommandSender console;

    public ConsoleWrapper(final ConsoleCommandSender console) {
        this.console = console;
    }

    public String getName() {
        return console.getName();
    }

    @Override
    public void sendMessage(String message) {
        console.sendMessage(message);
    }
}
