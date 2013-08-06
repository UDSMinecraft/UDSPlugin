package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.util.*;

/**
 * Geometrical directions.
 * 
 * @author UndeadScythes
 */
public enum Direction {
    NORTH(-22.5, 22.5, true, new Vector(0, 0 , -1)),
    NORTH_EAST(22.5, 67.5, false, null),
    EAST(67.5, 112.5, true, new Vector(1, 0, 0)),
    SOUTH_EAST(112.5, 157.5, false, null),
    SOUTH(157.5, 202.5, true, new Vector(0, 0, 1)),
    SOUTH_WEST(202.5, 247.5, false, null),
    WEST(247.5, 292.5, true, new Vector(-1, 0, 0)),
    NORTH_WEST(292.5, 337.5, false, null),
    UP(0, 0, true, new Vector(0, 1, 0)),
    DOWN(0, 0, true, new Vector(0, -1, 0));

    protected final double min;
    protected final double max;
    private final boolean cardinal;
    private final Vector vector;

    private Direction(final double min, final double max, final boolean cardinal, final Vector vector) {
        this.min = min;
        this.max = max;
        this.cardinal = cardinal;
        this.vector = vector;
    }

    public final float getYaw() {
        return (float)((this.max + this.min) / 2 + 180) % 360;
    }

    public static Direction getByName(final String string) {
        for(Direction test : values()) {
            if(test.name().equals(string.toUpperCase())) {
                return test;
            }
        }
        return null;
    }

    public static Direction valueOf(final Location location) {
        float yaw = (location.getYaw() + 180) % 360;
        if(yaw < -22.5) {
            yaw += 360;
        }
        for(Direction test : values()) {
            if(yaw >= test.min && yaw < test.max) {
                return test;
            }
        }
        return null; //TODO: Check meh! This should never happen.
    }

    @Override
    public final String toString() {
        return name().toLowerCase().replace("_", " ");
    }

    public final boolean isCardinal() {
        return cardinal;
    }

    public final Vector toVector() {
        return vector;
    }
}
