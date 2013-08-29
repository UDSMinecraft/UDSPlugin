package com.undeadscythes.udsplugin;

import com.undeadscythes.udsplugin.exceptions.*;
import java.util.*;

/**
 * @param <V>
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class MatchableHashMap<V extends Object> extends HashMap<String, V> {
    public List<V> getKeyMatches(final String partial) throws NoKeyFoundException {
        final String lowPartial = partial.toLowerCase();
        final ArrayList<V> matches = new ArrayList<V>(1);
        for(Map.Entry<String, V> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                matches.add(entry.getValue());
            }
        }
        if(matches.isEmpty()) throw new NoKeyFoundException(partial);
        return matches;
    }

    public V matchKey(final String partial) throws NoKeyFoundException {
        final String lowPartial = partial.toLowerCase();
        for(Map.Entry<String, V> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                return entry.getValue();
            }
        }
        throw new NoKeyFoundException(partial);
    }

    @Override
    public V put(final String key, final V object) {
        return super.put(key.toLowerCase(), object);
    }

    public V get(final String key) throws NoKeyFoundException {
        if(!super.containsKey(key.toLowerCase())) throw new NoKeyFoundException(key);
        return super.get(key.toLowerCase());
    }

    public V remove(final String key) {
        return super.remove(key.toLowerCase());
    }

    public boolean containsKey(final String key) {
        return super.containsKey(key.toLowerCase());
    }

    public List<V> getSortedValues(final Comparator<V> comp) {
        final ArrayList<V> values = new ArrayList<V>(this.values());
        Collections.sort(values, comp);
        return values;
    }

    public void replace(final String oldKey, final String newKey, final V object) {
        remove(oldKey);
        put(newKey, object);
    }
}
