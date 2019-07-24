package com.liuzq.stripe.services;

import com.liuzq.stripe.bean.PayOrder;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

public interface PayService {
    Charge chargeNewCard(PayOrder order) throws StripeException;

    //TODO 可以保存信用卡
    Customer createCustomer(String token, String email) throws StripeException;

    //TODO 使用保存的信用卡
    Charge chargeCustomerCard(String customerId, int amount) throws StripeException;
}
