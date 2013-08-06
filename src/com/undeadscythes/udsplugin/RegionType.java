package com.undeadscythes.udsplugin;

/**
 * Region type.
 * 
 * @author UndeadScythes
 */
public enum RegionType {
    GENERIC,
    SHOP,
    BASE,
    QUARRY,
    HOME,
    ARENA,
    CITY,
    PLOT;

    public static RegionType getByName(final String string) {
        if(string.equalsIgnoreCase("normal")) {     //
            return GENERIC;                         // TODO: Fix me - Update hack fix
        }                                           //
        for(RegionType type : values()) {
            if(type.name().equals(string.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public final String toString() {
        return name().toLowerCase();
    }
}
