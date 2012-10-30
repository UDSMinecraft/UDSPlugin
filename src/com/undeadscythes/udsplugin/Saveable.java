package com.undeadscythes.udsplugin;

/**
 * A saveable object. Like Java's Serializable.
 * @author UndeadScythes
 */
public interface Saveable {
    /**
     * Get a concatenated string representation of the object ready for saving.
     * @return A string ready for saving.
     */
    String getRecord();
}
