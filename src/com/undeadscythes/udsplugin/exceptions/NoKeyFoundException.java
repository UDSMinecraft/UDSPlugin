package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoKeyFoundException extends Exception {
    public NoKeyFoundException(String key) {
        super("No key found matching " + key + ".");
    }
}
