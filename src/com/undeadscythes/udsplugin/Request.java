package com.undeadscythes.udsplugin;

/**
 * @author UndeadScythes
 */
public class Request {
    private final RequestType type;
    private final Member sender;
    private final Member recipient;
    private final String data;
    private final long time = System.currentTimeMillis();

    public Request(final Member sender, final RequestType type, final String data, final Member recipient) {
        this.type = type;
        this.sender = sender;
        this.data = data;
        this.recipient = recipient;
    }

    public Request(final Member sender, final RequestType type, final int data, final Member recipient) {
        this.type = type;
        this.sender = sender;
        this.data = Integer.toString(data);
        this.recipient = recipient;
    }

    public RequestType getType() {
        return type;
    }

    public Member getSender() {
        return sender;
    }

    public String getData() {
        return data;
    }

    public long getTime() {
        return time;
    }

    public Member getRecipient() {
        return recipient;
    }
}
