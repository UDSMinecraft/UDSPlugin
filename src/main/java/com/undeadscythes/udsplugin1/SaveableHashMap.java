package com.undeadscythes.udsplugin1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A MatchableHashMap extended to provide methods to save and load.
 * @author UndeadScythes
 */
public class SaveableHashMap extends MatchableHashMap<Saveable> {
    /**
     * Save each value of the map to disk.
     * @param path The filename to save the map to.
     * @throws IOException Thrown when the file could not be opened.
     */
    public void save(File path) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        for(Saveable value : this.values()) {
            file.write(value.getRecord());
        }
        file.close();
    }
}
