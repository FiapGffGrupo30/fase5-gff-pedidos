package br.fiap.gff.orders.events;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderReceiveEvent {

    @Incoming("sent-orders")
    public void consume(String message) {
        System.out.println("Consumed: " + message);
    }
}
