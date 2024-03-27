package br.fiap.gff.orders.controllers;

import br.fiap.gff.orders.models.Category;
import br.fiap.gff.orders.usecases.CategoryUseCase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase category;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Category> categories = category.getAll();
        return categories.isEmpty() ? Response.noContent().build() : Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(Long id) {
        return Response.ok(category.filterById(id)).build();
    }
}
