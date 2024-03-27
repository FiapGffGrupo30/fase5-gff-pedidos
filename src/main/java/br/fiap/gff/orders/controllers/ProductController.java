package br.fiap.gff.orders.controllers;

import br.fiap.gff.orders.models.Product;
import br.fiap.gff.orders.usecases.ProductUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase product;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Product> products = product.getAll();
        return products.isEmpty() ? Response.noContent().build() : Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(Long id) {
        return Response.ok(product.filterById(id)).build();
    }
}


