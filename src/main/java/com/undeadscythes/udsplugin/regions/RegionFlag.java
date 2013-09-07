package com.undeadscythes.udsplugin.regions;

import com.undeadscythes.udsplugin.Flag;
import com.undeadscythes.udsplugin.Flag;

/**
 * Region flags.
 * 
 * @author UndeadScythes
 */
public enum RegionFlag implements Flag {
    PROTECTION(true),
    MOBS(false),
    PVE(true),
    LOCK(true),
    POWER(true),
    VINES(true),
    FOOD(true),
    FIRE(false),
    SNOW(false),
    PVP(false),
    DISPENSER(false),
    PISTON(false),
    LAMP(false);

    private final boolean defaulted;

    private RegionFlag(final boolean defaulted) {
        this.defaulted = defaulted;
    }

    public final boolean isDefaulted() {
        return defaulted;
    }

    @Override
    public final String toString() {
        return name().toLowerCase();
    }

    public final static RegionFlag getByName(final String name) {
        final String uName = name.toUpperCase();
        for(RegionFlag flag : values()) {
            if(flag.name().equals(uName)) {
                return flag;
            }
        }
        return null;
    }
}
