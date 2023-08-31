package com.panov.bankingapi.product;

import java.util.Comparator;

public class ProductNameComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getName() == null || o2.getName() == null)
            throw new IllegalArgumentException();

        return o1.getName().compareTo(o2.getName());
    }
}
