package com.undeadscythes.udsplugin1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A LinkedHashMap whose contents can be matched with partials.
 * @param <Object>
 * @author UndeadScythes
 */
public class MatchableHashMap<Object> extends HashMap<String, Object> {
    /**
     * Find all matches for a given partial key.
     * @param partialKey Partial key to search the map with.
     * @return A list of objects corresponding to matches of the partial key.
     */
    public ArrayList<Object> matchesKey(String partialKey) {
        String lowPartialKey = partialKey.toLowerCase();
        ArrayList<Object> returnValue = new ArrayList<Object>();
        for(Map.Entry<String, Object> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartialKey)) {
                returnValue.add(entry.getValue());
            }
        }
        return returnValue;
    }

    /**
     * Add an object to the map, converting the key to lower case.
     */
    @Override
    public Object put(String key, Object object) {
        return super.put(key.toLowerCase(), object);
    }

    /**
     * Get an object from the map, using the lower case key.
     * @param key The key to search for.
     * @return The object to which the key relates.
     */
    public Object get(String key) {
        return super.get(key.toLowerCase());
    }

    /**
     * A shortcut to get a sorted list of the map values.
     * @param comp Comparator to define sort priorities.
     * @return Sorted array of objects.
     */
    public ArrayList<Object> getSortedValues(Comparator comp) {
        ArrayList<Object> values = new ArrayList<Object>(this.values());
        Collections.sort(values, comp);
        return values;
    }
}
