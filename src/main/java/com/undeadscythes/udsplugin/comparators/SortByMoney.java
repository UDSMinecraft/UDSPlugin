package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.members.*;
import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare players by money.
 * 
 * @author UndeadScythe
 */
public class SortByMoney implements Comparator<OfflineMember> {
    @Override
    public final int compare(final OfflineMember player1, final OfflineMember player2) {
        return player2.getMoney() - player1.getMoney();
    }
}
