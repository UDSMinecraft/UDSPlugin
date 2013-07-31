package Comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 *
 * @author UndeadScythes
 */
public class SortByKDR implements Comparator<Clan> {
    @Override
    public int compare(final Clan clan1, final Clan clan2) {
        return (int)((clan2.getRatio() - clan1.getRatio()) * 100);
    }
}
