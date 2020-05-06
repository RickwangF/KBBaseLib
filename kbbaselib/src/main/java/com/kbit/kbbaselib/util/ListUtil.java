package com.kbit.kbbaselib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ListUtil {
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static <E> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2) {
        final List<E> result = new ArrayList<>();

        List<? extends E> smaller = list1;
        List<? extends E> larger = list2;
        if (list1.size() > list2.size()) {
            smaller = list2;
            larger = list1;
        }

        final HashSet<E> hashSet = new HashSet<>(smaller);

        for (final E e : larger) {
            if (hashSet.contains(e)) {
                result.add(e);
                hashSet.remove(e);
            }
        }
        return result;
    }

    public static <E> List<E> subtract(final List<? extends E> list1, final List<? extends E> list2) {
        final ArrayList result = new ArrayList<>(list1);
        for (E e : list2) {
            result.remove(e);
        }

        return result;
    }

    public static <E> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
        final ArrayList<E> result = new ArrayList<>(list1.size() + list2.size());
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }

    public static boolean isEqualList(final Collection<?> list1, final Collection<?> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        final Iterator<?> it1 = list1.iterator();
        final Iterator<?> it2 = list2.iterator();
        Object obj1 = null;
        Object obj2 = null;

        while (it1.hasNext() && it2.hasNext()) {
            obj1 = it1.next();
            obj2 = it2.next();

            if (!(obj1 == null ? obj2 == null : obj1.equals(obj2))) {
                return false;
            }
        }

        return !(it1.hasNext() || it2.hasNext());
    }

    public static <E> List<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
        final List<E> list = new ArrayList<>();
        for (final E obj : collection) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

}
