package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare players by money.
 * 
 * @author UndeadScythe
 */
public class SortByMoney implements Comparator<Member> {
    @Override
    public final int compare(final Member player1, final Member player2) {
        return player2.getMoney() - player1.getMoney();
    }
}

