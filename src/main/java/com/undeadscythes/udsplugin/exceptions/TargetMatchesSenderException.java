package com.undeadscythes.udsplugin.exceptions;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class TargetMatchesSenderException extends PlayerException {
    public TargetMatchesSenderException(OfflineMember player) {
        super("You cannot perform this action on yourself.");
    }
}
