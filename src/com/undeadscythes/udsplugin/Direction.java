package com.undeadscythes.udsplugin;

import org.bukkit.*;
import org.bukkit.util.*;

/**
 * Geometrical directions.
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

    private double min;
    private double max;
    private boolean cardinal;
    private Vector vector;

    private Direction(final double min, final double max, final boolean cardinal, final Vector vector) {
        this.min = min;
        this.max = max;
        this.cardinal = cardinal;
        this.vector = vector;
    }

    /**
     * Get the camera yaw associated with this direction.
     * @return Camera yaw
     */
    public float getYaw() {
        return (float)((this.max + this.min) / 2 + 180) % 360;
    }

    /**
     * Get a compass direction by name.
     * @param string The name of the direction.
     * @return The direction or <code>null</code> if there are no matches.
     */
    public static Direction getByName(final String string) {
        for(Direction test : values()) {
            if(test.name().equals(string.toUpperCase())) {
                return test;
            }
        }
        return null;
    }

    /**
     * Get the direction of the camera.
     * @param location The location of the camera.
     * @return The direction of the camera or <code>null</code> if there is an error.
     */
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
        return null; //This should never happen.
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }

    /**
     * Check if the direction is a cardinal compass bearing.
     * @return <code>true</code> if the direction is a cardinal, <code>false</code> otherwise.
     */
    public boolean isCardinal() {
        return cardinal;
    }

    /**
     *
     * @return
     */
    public Vector toVector() {
        return vector;
    }
}
