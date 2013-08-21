package com.undeadscythes.udsplugin;

import java.io.*;
import java.util.*;

/**
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class SaveableHashMap<V extends Saveable> extends HashMap<String, V> {
    public final List<V> getKeyMatches(final String partial) {
        final String lowPartial = partial.toLowerCase();
        final ArrayList<V> matches = new ArrayList<V>(1);
        for(Map.Entry<String, V> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                matches.add(entry.getValue());
            }
        }
        return matches;
    }

    public final V matchKey(final String partial) {
        final String lowPartial = partial.toLowerCase();
        for(Map.Entry<String, V> entry : super.entrySet()) {
            if(entry.getKey().toLowerCase().contains(lowPartial)) {
                return entry.getValue();
            }
        }
        return null;

    }

    @Override
    public final V put(final String key, final V object) {
        return super.put(key.toLowerCase(), object);
    }

    public final V get(final String key) {
        return super.get(key.toLowerCase());
    }

    public final V remove(final String key) {
        return super.remove(key.toLowerCase());
    }

    public final boolean containsKey(final String key) {
        return super.containsKey(key.toLowerCase());
    }

    public final List<V> getSortedValues(final Comparator<V> comp) {
        final ArrayList<V> values = new ArrayList<V>(this.values());
        Collections.sort(values, comp);
        return values;
    }

    public final void replace(final String oldKey, final String newKey, final V object) {
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
