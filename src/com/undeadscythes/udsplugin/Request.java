package com.undeadscythes.udsplugin;

/**
 * A request from one player to another.
 * @author UndeadScythes
 */
public class Request {
    /**
     * A request type.
     */
    public enum Type {
        /**
         * A clan invite.
         */
        CLAN,
        /**
         * A shop trade invite.
         */
        SHOP,
        /**
         * A home trade invite.
         */
        HOME,
        /**
         * A PVP challenge.
         */
        PVP,
        /**
         * A pet trade invite.
         */
        PET,
        /**
         * A teleport request.
         */
        TP;
    }

    private Type type;
    private SaveablePlayer sender;
    private SaveablePlayer recipient;
    private String data;
    private long time = System.currentTimeMillis();

    /**
     * Initialises a brand new request.
     * @param sender The sender of the request.
     * @param sender The receiver of the request.
     * @param type The request type.
     * @param data The request data, if any.
     */
    public Request(SaveablePlayer sender, Type type, String data, SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = data;
        this.recipient = recipient;
    }

    /**
     * Initialises a brand new request.
     * @param sender The sender of the request.
     * @param sender The receiver of the request.
     * @param type The request type.
     * @param data The request data, if any.
     */
    public Request(SaveablePlayer sender, Type type, int data, SaveablePlayer recipient) {
        this.type = type;
        this.sender = sender;
        this.data = Integer.toString(data);
        this.recipient = recipient;
    }

    /**
     * Get the type of this request.
     * @return Request type.
     */
    public Type getType() {
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

    public SaveablePlayer getRecipient() {
        return recipient;
    }
}
