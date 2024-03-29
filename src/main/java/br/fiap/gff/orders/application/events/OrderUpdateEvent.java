package br.fiap.gff.orders.application.events;

import br.fiap.gff.orders.dto.TransactionEvent;
import br.fiap.gff.orders.usecases.OrderUseCase;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderUpdateEvent {

    private final OrderUseCase order;

    @Incoming("update")
    @Blocking
    public void handle(JsonObject message) {
        TransactionEvent event = message.mapTo(TransactionEvent.class);
        Log.info(String.format("[EVENT - UPDATE] Transaction %s received", event.transactionId()));
        order.update(event.transactionId(), event.status());
    }

}
