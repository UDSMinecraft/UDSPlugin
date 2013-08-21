package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare players by bounty.
 * 
 * @author UndeadScythes
 */
public class SortByBounty implements Comparator<Member> {
    @Override
    public final int compare(final Member player1, final Member player2) {
        return player2.getBounty() - player1.getBounty();
    }
}