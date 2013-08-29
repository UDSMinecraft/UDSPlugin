package com.undeadscythes.udsplugin.members;

import com.undeadscythes.udsmeta.*;
import com.undeadscythes.udsmeta.exceptions.*;
import com.undeadscythes.udsplugin.*;
import com.undeadscythes.udsplugin.clans.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;

/**
 * @author UndeadScythes
 */
public class MemberMetaUtils extends MetaUtils<OfflinePlayer> {
    public MemberMetaUtils(OfflinePlayer player) throws NotMetadatableTypeException {
        super(player);
    }

    public Vector getVector(MemberKey key) throws NoMetadataSetException {
        return (Vector)MetaCore.get(getMeta(), key);
    }

    public MemberRank getPlayerRank(MemberKey key) throws NoMetadataSetException {
        return MemberRank.valueOf(getString(key));
    }

    public Clan getClan(MemberKey key) throws NoMetadataSetException {
        return (Clan)MetaCore.get(getMeta(), key);
    }

    public Location getLocation(MemberKey key) throws NoMetadataSetException {
        return (Location)MetaCore.get(getMeta(), key);
    }

    public OfflineMember getSaveablePlayer(MemberKey key) throws NoMetadataSetException {
        return (OfflineMember)MetaCore.get(getMeta(), key);
    }

    public ChatRoom getChatRoom(MemberKey key) throws NoMetadataSetException {
        return (ChatRoom)MetaCore.get(getMeta(), key);
    }

    public ChatChannel getChatChannel(MemberKey key) throws NoMetadataSetException {
        return (ChatChannel)MetaCore.get(getMeta(), key);
    }

    public ItemStack[] popItemStack(MemberKey key) throws NoMetadataSetException {
        try {
            return (ItemStack[])MetaCore.pop(getMeta(), key);
        } catch(NotMetadatableTypeException ex) {}
        throw new NoMetadataSetException(key);
    }
}
