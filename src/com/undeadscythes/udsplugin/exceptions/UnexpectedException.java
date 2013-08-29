package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super("You found a bug! Show Dave this: '" + message + "'.");
    }
}
