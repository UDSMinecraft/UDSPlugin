package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare players by bounty.
 * 
 * @author UndeadScythes
 */
public class SortByBounty implements Comparator<SaveablePlayer> {
    @Override
    public final int compare(final SaveablePlayer player1, final SaveablePlayer player2) {
        return player2.getBounty() - player1.getBounty();
    }
}