package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoPlayerFoundException extends PlayerException {
    public NoPlayerFoundException(String name) {
        super("No player found by the name " + name + ".");
    }
}
