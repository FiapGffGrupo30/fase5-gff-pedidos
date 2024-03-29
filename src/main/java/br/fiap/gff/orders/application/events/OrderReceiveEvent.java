package br.fiap.gff.orders.application.events;

import br.fiap.gff.orders.dto.OrderSentRequest;
import br.fiap.gff.orders.usecases.OrderUseCase;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderReceiveEvent {

    private final OrderUseCase order;

    @Incoming("create-orders")
    @Blocking
    public void consume(JsonObject message) {
        OrderSentRequest request = message.mapTo(OrderSentRequest.class);
        Log.info(String.format("Transaction %s received", request.transactionId()));
        order.create(request);
    }
}
