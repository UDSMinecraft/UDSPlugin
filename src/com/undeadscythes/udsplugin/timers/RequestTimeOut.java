package com.undeadscythes.udsplugin.timers;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
public class RequestTimeOut implements Runnable {
    public RequestTimeOut() {}

    @Override
    public void run() {
        for(final Iterator<Map.Entry<String, Request>> i = UDSPlugin.getRequestIterator(); i.hasNext();) {
            final Request request = i.next().getValue();
            if(request.getTime() + Config.REQUEST_TTL < System.currentTimeMillis()) {
                request.getSender().sendNormal("Your request has timed out.");
                i.remove();
            }
        }
    }
}
