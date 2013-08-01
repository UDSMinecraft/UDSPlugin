package com.undeadscythes.udsplugin;

import org.apache.commons.lang.*;

/**
 * Region flags.
 * @author UndeadScythes
 */
public enum RegionFlag {
    /**
     * Protects region from non members.
     */
    PROTECTION(true),
    /**
     * Allows mobs to spawn.
     */
    MOBS(false),
    /**
     * Allows PvE.
     */
    PVE(true),
    /**
     * Locks objects that players can 'open'.
     */
    LOCK(true),
    /**
     * Enables redstone 'switches'.
     */
    POWER(true),
    /**
     * Allows vines to grow.
     */
    VINES(true),
    /**
     * Allows food to grow.
     */
    FOOD(true),
    /**
     * Allows fire to spread and burn.
     */
    FIRE(false),
    /**
     * Allows snow to form and ice to spread.
     */
    SNOW(false),
    /**
     * Allows PvP.
     */
    PVP(false),
    /**
     * Dispensers have infinite items.
     */
    DISPENSER(false),
    /**
     * Allows pistons to ignore this regions boundaries.
     */
    PISTON(false),
    /**
     * 
     */
    LAMP(false);

    private boolean defaulted;

    private RegionFlag(final boolean defaulted) {
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
    public static RegionFlag getByName(final String name) {
        final String uName = name.toUpperCase();
        for(RegionFlag flag : values()) {
            if(flag.name().equals(uName)) {
                return flag;
            }
        }
        return null;
    }
}
