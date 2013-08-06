package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.*;

/**
 * A LinkedHashMap whose contents can be matched with partials.
 * 
 * @param <T> 
 * @author UndeadScythes
 */
public class SaveableHashMap<T extends Saveable> extends HashMap<String, T> {
    private static final long serialVersionUID = 1L;

    public final List<T> getKeyMatches(final String partial) {
        final String lowPartial = partial.toLowerCase();
        final ArrayList<T> matches = new ArrayList<T>(1);
        for(Map.Entry<String, T> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                matches.add(entry.getValue());
            }
        }
        return matches;
    }

    public final T matchKey(final String partial) {
        final String lowPartial = partial.toLowerCase();
        for(Map.Entry<String, T> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                return entry.getValue();
            }
        }
        return null;

    }

    @Override
    public final T put(final String key, final T object) {
        return super.put(key.toLowerCase(), object);
    }

    public final T get(final String key) {
        return super.get(key.toLowerCase());
    }

    public final T remove(final String key) {
        return super.remove(key.toLowerCase());
    }

    public final boolean containsKey(final String key) {
        return super.containsKey(key.toLowerCase());
    }

    public final List<T> getSortedValues(final Comparator<T> comp) {
        final ArrayList<T> values = new ArrayList<T>(this.values());
        Collections.sort(values, comp);
        return values;
    }

    public final void replace(final String oldKey, final String newKey, final T object) {
        remove(oldKey);
        put(newKey, object);
    }
    
    public final void save(final String path) throws IOException {
        final BufferedWriter file = new BufferedWriter(new FileWriter(path));
        for(Saveable value : this.values()) {
            file.write(value.getRecord());
            file.newLine();
        }
        file.close();
    }
}
