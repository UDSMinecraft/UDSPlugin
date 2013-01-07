package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;

/**
 * Region type.
 * @author UndeadScythes
 */
public enum RegionType {
    GENERIC,
    SHOP,
    BASE,
    QUARRY,
    HOME,
    ARENA,
    CITY;

    /**
        * Get a region type by name.
        * @param string Name of region type.
        * @return The region type or <code>null</code> if there was no match.
        */
    public static RegionType getByName(final String string) {
        if(string.equalsIgnoreCase("normal")) {     //
            return GENERIC;                         // Update hack fix
        }                                           //
        for(RegionType type : values()) {
            if(type.name().equals(string.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
