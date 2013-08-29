package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class PasswordRetrievalException extends Exception {
    public PasswordRetrievalException(String name) {
        super("Cannot retrieve a password for " + name + ".");
    }

}
