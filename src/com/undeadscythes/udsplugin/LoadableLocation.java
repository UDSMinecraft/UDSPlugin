package com.undeadscythes.udsplugin;

import org.bukkit.*;

/**
 * A location that can be constructed by parsing a string.
 * @author UndeadScythes
 */
public class LoadableLocation extends Location {
    public static enum Direction {
        NORTH(-22.5, 22.5, true),
        NORTH_EAST(22.5, 67.5, false),
        EAST(67.5, 112.5, true),
        SOUTH_EAST(112.5, 157.5, false),
        SOUTH(157.5, 202.5, true),
        SOUTH_WEST(202.5, 247.5, false),
        WEST(247.5, 292.5, true),
        NORTH_WEST(292.5, 337.5, false),
        UP(0, 0, true),
        DOWN(0, 0, true);

        private double min;
        private double max;
        private boolean cardinal;

        private Direction(double min, double max, boolean cardinal) {
            this.min = min;
            this.max = max;
            this.cardinal = cardinal;
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

        public boolean cardinal() {
            return cardinal;
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

    public LoadableLocation(Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    @Override
    public String toString() {
        return super.getWorld().getName() + "," + super.getX() + "," + super.getY() + "," + super.getZ() + "," + super.getPitch() + "," + super.getYaw();
    }
}
