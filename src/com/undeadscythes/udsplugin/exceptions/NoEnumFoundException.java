package com.undeadscythes.udsplugin.exceptions;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class NoEnumFoundException extends PlayerException {
    public NoEnumFoundException(String enumName, String name) {
        super("No " + enumName + " found with the name " + name + ".");
    }
}
