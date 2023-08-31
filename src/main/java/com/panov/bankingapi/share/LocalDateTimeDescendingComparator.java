package com.panov.bankingapi.share;

import java.time.LocalDateTime;
import java.util.Comparator;

public class LocalDateTimeDescendingComparator implements Comparator<LocalDateTime> {
    @Override
    public int compare(LocalDateTime o1, LocalDateTime o2) {
        if (o1 == null) return 1;
        if (o2 == null) return -1;
        return o2.compareTo(o1);
    }
}
