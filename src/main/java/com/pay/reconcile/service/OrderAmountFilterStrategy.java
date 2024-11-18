package com.pay.reconcile.service;

import com.pay.reconcile.model.CustomerOrder;
import org.springframework.stereotype.Service;

@Service
public class OrderAmountFilterStrategy implements FilterStrategy{
    @Override
    public boolean apply(CustomerOrder customerOrder) {
        return customerOrder.getAmount()>0?true:false;
    }
}
