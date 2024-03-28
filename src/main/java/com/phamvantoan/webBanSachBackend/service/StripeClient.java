package com.phamvantoan.webBanSachBackend.service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class StripeClient {
    public StripeClient(){
        Stripe.apiKey = "sk_test_51Oyy9G08IedeBdvZeTU4ck3H28iZBbTTYsdDXVO0QzMDKdQF3qDIKDcbdfEkLIi5UkvP5LYiKvfl9CrWdh4UYmJw00rs4nLhWT";
    }

    public Customer createCustomer(String token, String email) throws Exception{
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    private Customer getCustomer(String id) throws Exception{
        return Customer.retrieve(id);
    }

    public Charge chargeNewCard(String token, double amount) throws Exception{
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount)); // Số tiền cần thanh toán
        chargeParams.put("currency", "VND"); // Đơn vị tiền tệ là VND
        chargeParams.put("source", token); // Mã thông tin thanh toán (token)
        Charge charge = Charge.create(chargeParams); // Tạo giao dịch thanh toán

        return charge;
    }

    public Charge chargeCustomerCard(String customerId, int amount) throws Exception{
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "VND");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
