package com.pay.reconcile.service;

import com.pay.reconcile.model.CustomerOrder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationStrategy implements FilterStrategy{
    @Override
    public boolean apply(CustomerOrder customerOrder) {
        String datePattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(customerOrder.getDate());
        return matcher.matches()?true:false;
    }
}
