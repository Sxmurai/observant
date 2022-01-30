package me.sxmurai.observant.util.math;

import org.bukkit.Location;

/**
 * Calculates things that have to do with distancing
 */
public class DistanceUtil {
    /**
     * Gets the squared distance from one location to another
     * @param from A location
     * @param to Another location
     * @return The squared distance between these two locations
     */
    public static double getDistanceSq(Location from, Location to) {
        double x = from.getX() - to.getX();
        double y = from.getY() - to.getY();
        double z = from.getZ() - to.getZ();

        return x * x + y * y + z * z;
    }

    /**
     * Gets the square root of the distance between two locations
     * @param from A location
     * @param to Another location
     * @return The distance using square root
     */
    public static double getDistance(Location from, Location to) {
        return Math.sqrt(getDistanceSq(from, to));
    }
}
