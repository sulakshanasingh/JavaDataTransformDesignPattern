package com.pay.reconcile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    private String transactionId;
    private String orderId;
    private double amount;
    private String date;
    private String status;
    private String paymentType;
    public CustomerOrder(Builder builder)
    {
        this.orderId = builder.orderId;
        this.amount = builder.amount;
        this.status = builder.status;
    }


    public static class Builder {

        // instance fields
        private String orderId;
        private double amount;
        private String status;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

        // Setter methods
        public Builder setOrderId(String id)
        {
            this.orderId = id;
            return this;
        }
        public Builder setAmount(double amt)
        {
            this.amount = amt;
            return this;
        }
        public Builder setStatus(String status)
        {
            this.status = status;
            return this;
        }
        public CustomerOrder build()
        {
            return new CustomerOrder(this);
        }
    }

    }
