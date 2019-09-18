package de.adesso.bookstore.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dummy")
public class DummyPayment implements PaymentService {
    @Override
    public void pay(double amount) {
        System.out.println("pay " + amount + " with Dummy.");
    }
}
