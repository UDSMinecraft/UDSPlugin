package com.undeadscythes.udsplugin;

import java.util.*;

/**
 * An implementation of a {@link java.util.LinkedList} in which the elements are added in
 * order.
 * 
 * @param <E> the type of elements in this list.
 * @author UndeadScythes
 */
@SuppressWarnings("serial")
public class SortedList<E extends Object> extends LinkedList<E> {
    private final Comparator<E> comparator;
    
    public SortedList(final Comparator<E> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public final boolean add(final E element) {
        int index = 0;
        while(index < size()) {
            if(comparator.compare(element, get(index)) <= 0) {
                break;
            }
            index++;
        }
        add(index, element);
        return true;
    }
}
