package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.clans.*;
import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare clans by K/D ratio.
 * 
 * @author UndeadScythes
 */
public class SortByKDR implements Comparator<Clan> {
    @Override
    public final int compare(final Clan clan1, final Clan clan2) {
        return (int)((clan2.getRatio() - clan1.getRatio()) * 100);
    }
}
