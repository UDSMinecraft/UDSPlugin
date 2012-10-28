package com.undeadscythes.udsplugin1;

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
        PET;
    }

    private Type type;
    private String sender;
    private String receiver;
    private String data;
    private long time;

    /**
     * Initialises a brand new request.
     * @param sender The sender of the request.
     * @param receiver The receiver of the request.
     * @param type The request type.
     * @param data The request data, if any.
     */
    public Request(String sender, String receiver, Type type, String data) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    /**
     * Get the type of this request.
     * @return Request type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Get who sent this request.
     * @return Request sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Get the player this request was sent to.
     * @return Request receiver.
     */
    public String getReceiver() {
        return receiver;
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
}
