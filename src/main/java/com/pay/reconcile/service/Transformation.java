package com.pay.reconcile.service;

import com.pay.reconcile.model.CustomerOrder;

@FunctionalInterface
public interface Transformation {
    CustomerOrder apply(CustomerOrder customerOrder);
}
