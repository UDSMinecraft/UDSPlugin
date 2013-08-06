package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare regions by member size.
 * 
 * @author UndeadScythe
 */
public class SortByPop implements Comparator<Region> {
    @Override
    public final int compare(final Region region1, final Region region2) {
        return region2.getMemberNo() - region1.getMemberNo();
    }
}