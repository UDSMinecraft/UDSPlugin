package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoPlayerMatchingException extends Exception {
    public NoPlayerMatchingException(String partial) {
        super("No player could be found matching " + partial + ".");
    }
}
