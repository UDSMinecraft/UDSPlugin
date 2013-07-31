package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Threaded class to run scheduled functions for maintenance.
 * @author UndeadScythes
 */
public class RequestTimeOut implements Runnable {
    /**
     * Initiates the timer.
     */
    public RequestTimeOut() {}

    /**
     * The function that will be used on each schedule.
     */
    @Override
    public void run() {
        for(final Iterator<Map.Entry<String, Request>> i = UDSPlugin.getRequestIterator(); i.hasNext();) {
            final Request request = i.next().getValue();
            if(request.getTime() + UDSPlugin.getConfigLong(ConfigRef.REQUEST_TTL) < System.currentTimeMillis()) {
                request.getSender().sendNormal("Your request has timed out.");
                i.remove();
            }
        }
    }
}
