package com.undeadscythes.udsplugin;

/**
 * Various flags to denote various features of individual worlds.
 * 
 * @author UndeadsScythes
 */
public enum WorldFlag implements Flag {
    WEATHER(true),
    TIME(true),
    BUILD(true);
    
    private final boolean defaulted;

    private WorldFlag(final boolean defaulted) {
        this.defaulted = defaulted;
    }

    public final boolean isDefaulted() {
        return defaulted;
    }

    @Override
    public final String toString() {
        return name().toLowerCase();
    }

    public static WorldFlag getByName(final String name) {
        final String uName = name.toUpperCase();
        for(WorldFlag flag : values()) {
            if(flag.name().equals(uName)) {
                return flag;
            }
        }
        return null;
    }
}
