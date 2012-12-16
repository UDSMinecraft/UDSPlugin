package com.undeadscythes.udsplugin;

/**
 * A request from one player to another.
 * @author UndeadScythes
 */
public class Request {
    private final transient RequestType type;
    private final transient SaveablePlayer sender;
    private final transient SaveablePlayer recipient;
    private final transient String data;
    private final transient long time = System.currentTimeMillis();

    /**
     * Initialises a brand new request.
     * @param sender The sender of the request.
     * @param type The request type.
     * @param recipient The recipient of this request.
     * @param data The request data, if any.
     */
    public Request(final SaveablePlayer sender, final RequestType type, final String data, final SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = data;
        this.recipient = recipient;
    }

    /**
     * Initialises a brand new request.
     * @param sender The sender of the request.
     * @param type The request type.
     * @param recipient The recipient of this request.
     * @param data The request data, if any.
     */
    public Request(final SaveablePlayer sender, final RequestType type, final int data, final SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = Integer.toString(data);
        this.recipient = recipient;
    }

    /**
     * Get the type of this request.
     * @return Request type.
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Get the player this request was sent to.
     * @return Request receiver.
     */
    public SaveablePlayer getSender() {
        return sender;
    }

    /**
     * Get the request data, if any.
     * @return Request data.
     */
    public String getData() {
        return data;
    }

    /**
     * Get the time that this request was sent.
     * @return Request time.
     */
    public long getTime() {
        return time;
    }

    /**
     * Get the recipient of this request.
     * @return Request recipient.
     */
    public SaveablePlayer getRecipient() {
        return recipient;
    }
}
