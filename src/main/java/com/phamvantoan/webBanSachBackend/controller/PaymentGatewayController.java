package com.phamvantoan.webBanSachBackend.controller;

import com.phamvantoan.webBanSachBackend.service.StripeClient;
import com.stripe.model.Charge;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payment")
public class PaymentGatewayController {
    private StripeClient stripeClient;
    @Autowired
    public PaymentGatewayController(StripeClient stripeClient){
        this.stripeClient = stripeClient;
    }
    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader("token") String token, @RequestHeader("amount") String amount) throws Exception{
        double newAmount = Double.valueOf(amount);
        return this.stripeClient.chargeNewCard(token, newAmount);
    }
}
