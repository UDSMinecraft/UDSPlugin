package com.undeadscythes.udsplugin;

/**
 * @author UndeadScythes
 */
public abstract class MessageReciever {
    public abstract void sendMessage(final String message);

    public void sendWhisper(final String message) {
        sendMessage(Color.WHISPER + message);
    }

    public void sendPrivate(final String message) {
        sendMessage(Color.PRIVATE + message);
    }

    public void sendNormal(final String message) {
        sendMessage(Color.MESSAGE + message);
    }

    public void sendError(final String message) {
        sendMessage(Color.ERROR + message);
    }

    public void sendClan(final String message) {
        sendMessage(Color.CLAN + message);
    }

    public void sendBroadcast(final String message) {
        sendMessage(Color.BROADCAST + message);
    }

    public void sendListItem(final String item, final String message) {
        sendMessage(Color.ITEM + item + Color.TEXT + message);
    }

    public void sendText(final String message) {
        sendMessage(Color.TEXT + message);
    }
}
