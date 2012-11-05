package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;

/**
 * Set up a private chat room.
 * @author UndeadScythes
 */
public class PrivateCmd extends PlayerCommandExecutor {
    /**
     * @inheritDocs
     */
    @Override
    public void playerExecute(SaveablePlayer player, String[] args) {
        if(argsEq(1)) {
            ChatRoom chatRoom = UDSPlugin.getChatRooms().get(args[0]);
            if(chatRoom == null) {
                UDSPlugin.getChatRooms().put(args[0], new ChatRoom(player, args[0]));
                player.sendMessage(Color.MESSAGE + "Private chat room created.");
            } else {
                if(chatRoom.isMember(player)) {
                    chatRoom.delMember(player);
                    chatRoom.sendMessage(player.getDisplayName() + " has left.");
                } else {
                    chatRoom.addMember(player);
                    chatRoom.sendMessage(player.getDisplayName() + " has joined.");
                }
            }
        }
    }
}
