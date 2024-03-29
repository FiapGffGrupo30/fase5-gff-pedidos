package br.fiap.gff.orders.application.events;

import br.fiap.gff.orders.dto.TransactionEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class CustomerNotifyEvent {

    @Channel("customer")
    Emitter<TransactionEvent> eventEmitter;

    public void send(TransactionEvent event) {
        eventEmitter.send(event);
    }

}
