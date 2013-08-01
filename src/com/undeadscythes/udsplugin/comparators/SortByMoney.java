package com.undeadscythes.udsplugin.comparators;

import com.undeadscythes.udsplugin.*;
import java.util.*;

/**
 * Compare players by money.
 * @author UndeadScythe
 */
public class SortByMoney implements Comparator<SaveablePlayer> {
    @Override
    public int compare(final SaveablePlayer player1, final SaveablePlayer player2) {
        return player2.getMoney() - player1.getMoney();
    }
}

