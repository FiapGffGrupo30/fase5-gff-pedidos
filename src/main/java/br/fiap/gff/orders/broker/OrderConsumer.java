package br.fiap.gff.orders.broker;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderConsumer {

    @Incoming("sent-orders")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
    }
}
