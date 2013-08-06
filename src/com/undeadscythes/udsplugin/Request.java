package com.undeadscythes.udsplugin;

/**
 * A request from one player to another.
 * 
 * @author UndeadScythes
 */
public class Request {
    private final RequestType type;
    private final SaveablePlayer sender;
    private final SaveablePlayer recipient;
    private final String data;
    private final long time = System.currentTimeMillis();

    public Request(final SaveablePlayer sender, final RequestType type, final String data, final SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = data;
        this.recipient = recipient;
    }

    public Request(final SaveablePlayer sender, final RequestType type, final int data, final SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = Integer.toString(data);
        this.recipient = recipient;
    }

    public final RequestType getType() {
        return type;
    }

    public final SaveablePlayer getSender() {
        return sender;
    }

    public final String getData() {
        return data;
    }

    public final long getTime() {
        return time;
    }

    public final SaveablePlayer getRecipient() {
        return recipient;
    }
}
