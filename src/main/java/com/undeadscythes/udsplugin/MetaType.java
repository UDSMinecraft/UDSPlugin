package com.undeadscythes.udsplugin;

import com.undeadscythes.udsmeta.*;
import org.bukkit.*;
import org.bukkit.entity.*;

/**
 * @author UndeadScythes
 */
public enum MetaType implements MetadatableType {
    PLAYER(OfflinePlayer.class);

    private Class<? extends Object> clazz;

    MetaType(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<? extends Object> getObjectType() {
        return clazz;
    }

    @Override
    public String getID(Object obj) {
        if(obj instanceof OfflinePlayer) {
            return ((OfflinePlayer)obj).getName();
        } else {
            return ((Entity)obj).getUniqueId().toString();
        }
    }
}
