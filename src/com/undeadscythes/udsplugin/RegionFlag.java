package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;

/**
 * Region flags.
 * @author UndeadScythes
 */
public enum RegionFlag {
    PROTECTION(true),
    MOBS(false),
    PVE(true),
    LOCK(true),
    VINES(true),
    FOOD(true),
    FIRE(false),
    SNOW(false),
    PVP(false);

    private boolean defaultValue;

    RegionFlag(final boolean value) {
        this.defaultValue = value;
    }

    /**
        * Get this flag's default value.
        * @return
        */
    public boolean isDefault() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }

    /**
        * Get a flag by name.
        * @param string The name of the flag.
        * @return The flag or <code>null</code> if there was no match.
        */
    public static RegionFlag getByName(final String string) {
        for(RegionFlag flag : values()) {
            if(flag.name().equals(string.toUpperCase())) {
                return flag;
            }
        }
        return null;
    }
}
