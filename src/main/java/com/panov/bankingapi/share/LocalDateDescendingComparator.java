package com.panov.bankingapi.share;

import java.time.LocalDate;
import java.util.Comparator;

public class LocalDateDescendingComparator implements Comparator<LocalDate> {
    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        if (o1 == null) return 1;
        if (o2 == null) return -1;
        return o2.compareTo(o1);
    }
}
