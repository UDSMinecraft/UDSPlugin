package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;

/**
 *
 * @author UndeadsScythes
 */
public enum WorldFlag {
    /**
     * 
     */
    WEATHER(true),
    /**
     * 
     */
    TIME(true),
    /**
     * 
     */
    BUILD(true);
    private boolean defaulted;

    private WorldFlag(final boolean defaulted) {
        this.defaulted = defaulted;
    }

    /**
     * Get this flag's default value.
     * @return Default flag value.
     */
    public boolean isDefaulted() {
        return defaulted;
    }

    /**
     * Get the name of the enum capitalized.
     * @return Capitalized enum name.
     */
    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }

    /**
     * Get a flag by name.
     * @param name Flag name.
     * @return Flag or <code>null</code> if there was no match.
     */
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
