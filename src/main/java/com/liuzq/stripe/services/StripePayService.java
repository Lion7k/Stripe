package com.liuzq.stripe.services;

import com.liuzq.stripe.bean.PayOrder;
import com.liuzq.stripe.util.Util;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePayService implements PayService{

    @Override
    public Charge chargeNewCard(PayOrder order) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        //TODO 单位为分
        chargeParams.put("amount", Util.conversionAmount(BigDecimal.valueOf(order.getPrice().doubleValue() * 100)).intValue());
        chargeParams.put("currency", order.getCurType().getType());
        chargeParams.put("description", order.getDescription());
        chargeParams.put("source", order.getSource());
        return Charge.create(chargeParams);
    }

    @Override
    public Customer createCustomer(String token, String email) throws StripeException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    @Override
    public Charge chargeCustomerCard(String customerId, int amount) throws StripeException {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
