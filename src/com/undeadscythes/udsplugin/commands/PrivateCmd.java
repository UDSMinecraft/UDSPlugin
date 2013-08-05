package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set up a private chat room.
 * @author UndeadScythes
 */
public class PrivateCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            final ChatRoom chatRoom = UDSPlugin.getChatRoom(arg(0));
            if(chatRoom == null) {
                UDSPlugin.addChatRoom(arg(0), new ChatRoom(player(), arg(0)));
                player().sendNormal("Private chat room created.");
            } else {
                if(chatRoom.isMember(player())) {
                    chatRoom.delMember(player());
                    chatRoom.sendMessage(player().getNick() + " has left.");
                } else {
                    chatRoom.addMember(player());
                    chatRoom.sendMessage(player().getNick() + " has joined.");
                }
            }
        }
    }
}
