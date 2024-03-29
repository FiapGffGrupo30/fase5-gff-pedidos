package br.fiap.gff.orders.application.controllers;

import br.fiap.gff.orders.models.Order;
import br.fiap.gff.orders.usecases.OrderUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderUseCase order;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Order> orders = order.getAll();
        return orders.isEmpty() ? Response.noContent().build() : Response.ok(orders).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(Long id) {
        return Response.ok(order.filterById(id)).build();
    }
}


