package com.undeadscythes.udsplugin.commands;


import com.undeadscythes.udsplugin.*;

/**
 * @author UndeadScythes
 */
public class PrivateCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(numArgsHelp(1)) {
            final ChatRoom chatRoom = UDSPlugin.getChatRoom(args[0]);
            if(chatRoom == null) {
                UDSPlugin.addChatRoom(args[0], new ChatRoom(player.getOfflineMember(), args[0]));
                player.sendNormal("Private chat room created.");
            } else {
                if(chatRoom.isMember(player.getOfflineMember())) {
                    chatRoom.delMember(player.getOfflineMember());
                    chatRoom.sendMessage(player.getNick() + " has left.");
                } else {
                    chatRoom.addMember(player.getOfflineMember());
                    chatRoom.sendMessage(player.getNick() + " has joined.");
                }
            }
        }
    }
}
