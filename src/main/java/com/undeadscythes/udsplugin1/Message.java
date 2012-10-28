package com.undeadscythes.udsplugin1;

/**
 * Messages commonly used in the plugin.
 * @author UndeadScythes
 */
public enum Message {
    /**
     * You do not have permission to do that.
     */
    NO_PERM(Color.ERROR + "You do not have permission to do that.");

    String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
