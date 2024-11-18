package com.pay.reconcile.service;

import com.pay.reconcile.model.CustomerOrder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DateTransformation implements Transformation{
    @Override
    public CustomerOrder apply(CustomerOrder customerOrder) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/mm/dd");
        try {
            customerOrder.setDate(simpleDateFormat.parse(customerOrder.getDate()).toString());
            return customerOrder;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
