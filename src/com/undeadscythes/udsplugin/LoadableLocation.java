package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public class LoadableLocation extends Location {
    public static enum Direction {
        NORTH(-22.5, 22.5),
        NORTH_EAST(22.5, 67.5),
        EAST(67.5, 112.5),
        SOUTH_EAST(112.5, 157.5),
        SOUTH(157.5, 202.5),
        SOUTH_WEST(202.5, 247.5),
        WEST(247.5, 292.5),
        NORTH_WEST(292.5, 337.5);

        private double min;
        private double max;

        private Direction(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public float getYaw() {
            return (float)((this.max + this.min) / 2 + 180) % 360;
        }

        public static Direction get(String string) {
            for(Direction test : values()) {
                if(test.name().equals(string.toUpperCase())) {
                    return test;
                }
            }
            return null;
        }

        public static Direction valueOf(Location location) {
            float yaw = (location.getYaw() + 180) % 360;
            yaw = yaw < -22.5 ? yaw + 360 : yaw;
            for(Direction test : values()) {
                if(yaw >= test.min && yaw < test.max) {
                    return test;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }
    }

    /**
     * Initialises a brand new location.
     * @param string The location in the format "world,x,y,z,pitch,yaw".
     */
    public LoadableLocation(String string) {
        super(Bukkit.getWorld(string.split(",")[0]), Double.parseDouble(string.split(",")[1]), Double.parseDouble(string.split(",")[2]), Double.parseDouble(string.split(",")[3]));
        setPitch(Float.parseFloat(string.split(",")[4]));
        setPitch(Float.parseFloat(string.split(",")[5]));
    }
}
