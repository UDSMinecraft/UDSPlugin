package com.undeadscythes.udsplugin.requests;

import com.undeadscythes.udsplugin.members.*;

/**
 * @author UndeadScythes
 */
public class Request {
    private final RequestType type;
    private final OfflineMember sender;
    private final OfflineMember recipient;
    private final String data;
    private final long time = System.currentTimeMillis();

    public Request(final Member sender, final RequestType type, final String data, final Member recipient) {
        this.type = type;
        this.sender = sender.getOfflineMember();
        this.data = data;
        this.recipient = recipient.getOfflineMember();
    }

    public Request(final Member sender, final RequestType type, final int data, final Member recipient) {
        this.type = type;
        this.sender = sender.getOfflineMember();
        this.data = Integer.toString(data);
        this.recipient = recipient.getOfflineMember();
    }

    public RequestType getType() {
        return type;
    }

    public OfflineMember getSender() {
        return sender;
    }

    public String getData() {
        return data;
    }

    public long getTime() {
        return time;
    }

    public OfflineMember getRecipient() {
        return recipient;
    }
}
