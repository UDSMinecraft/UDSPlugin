package com.undeadscythes.udsplugin;

import com.undeadscythes.udsmeta.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;

/**
 * @author UndeadScythes
 */
public class PlayerMetaUtils extends MetaUtils<OfflinePlayer> {
    public PlayerMetaUtils(OfflinePlayer player) throws NotMetadatableTypeException {
        super(player);
    }

    public Vector getVector(PlayerKey key) throws NoMetadataSetException {
        return (Vector)MetaCore.get(getMeta(), key);
    }

    public PlayerRank getPlayerRank(PlayerKey key) throws NoMetadataSetException {
        return PlayerRank.valueOf(getString(key));
    }

    public Clan getClan(PlayerKey key) throws NoMetadataSetException {
        return (Clan)MetaCore.get(getMeta(), key);
    }

    public Location getLocation(PlayerKey key) throws NoMetadataSetException {
        return (Location)MetaCore.get(getMeta(), key);
    }

    public Member getSaveablePlayer(PlayerKey key) throws NoMetadataSetException {
        return (Member)MetaCore.get(getMeta(), key);
    }

    public ChatRoom getChatRoom(PlayerKey key) throws NoMetadataSetException {
        return (ChatRoom)MetaCore.get(getMeta(), key);
    }

    public ChatChannel getChatChannel(PlayerKey key) throws NoMetadataSetException {
        return (ChatChannel)MetaCore.get(getMeta(), key);
    }

    public ItemStack[] popItemStack(PlayerKey key) throws NoMetadataSetException {
        try {
            return (ItemStack[])MetaCore.pop(getMeta(), key);
        } catch (NotMetadatableTypeException ex) {}
        throw new NoMetadataSetException(key);
    }
}
