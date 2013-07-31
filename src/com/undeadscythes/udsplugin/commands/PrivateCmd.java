package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set up a private chat room.
 * @author UndeadScythes
 */
public class PrivateCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            final ChatRoom chatRoom = UDSPlugin.getChatRoom(args[0]);
            if(chatRoom == null) {
                UDSPlugin.addChatRoom(args[0], new ChatRoom(player, args[0]));
                player.sendNormal("Private chat room created.");
            } else {
                if(chatRoom.isMember(player)) {
                    chatRoom.delMember(player);
                    chatRoom.sendMessage(player.getNick() + " has left.");
                } else {
                    chatRoom.addMember(player);
                    chatRoom.sendMessage(player.getNick() + " has joined.");
                }
            }
        }
    }
}
