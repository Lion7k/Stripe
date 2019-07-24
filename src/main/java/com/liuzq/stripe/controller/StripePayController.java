package com.liuzq.stripe.controller;

import com.liuzq.stripe.bean.DefaultCurType;
import com.liuzq.stripe.bean.OrderData;
import com.liuzq.stripe.bean.PayOrder;
import com.liuzq.stripe.services.StripePayService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 发起支付入口
 */
@Controller
@RequestMapping("/stripe")
public class StripePayController {

    @Autowired
    StripePayService stripePayService;

    @RequestMapping(value = "/hop-payment", method = RequestMethod.GET)
    public String hopPayment(HttpServletRequest request, HttpServletResponse response, Model model){
        System.out.println("hopPayment is running...");
        OrderData orderData = new OrderData();
        orderData.setCode("123456");
        orderData.setTotalPrice("120");
        orderData.setCurrency(DefaultCurType.EUR.getType());
        model.addAttribute("orderData", orderData);
        return "index";
    }

    @RequestMapping(value = "/charge")
    public String charge(final HttpServletRequest request, final HttpServletResponse response, Model model, String amount, String orderCode,  String stripeToken) throws StripeException {
        Stripe.apiKey = "sk_test_g7vzstAemYn9cXlZPHleFleI00HOQYzW9X";

        PayOrder payOrder = new PayOrder();
        payOrder.setPrice(BigDecimal.valueOf(Long.parseLong(amount)));
        payOrder.setCurType(DefaultCurType.EUR);
        payOrder.setSource(stripeToken);

        Charge charge = stripePayService.chargeNewCard(payOrder);
        if ("succeeded".equals(charge.getStatus())) {
            return "success";
        }
        return "fail";
    }
}
