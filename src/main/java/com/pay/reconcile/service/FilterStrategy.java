package com.pay.reconcile.service;

import com.pay.reconcile.model.CustomerOrder;

@FunctionalInterface
public interface FilterStrategy {
    boolean apply(CustomerOrder customerOrder);
}
