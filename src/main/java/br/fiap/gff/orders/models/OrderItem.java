package br.fiap.gff.orders.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class OrderItem {

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double price;

    private Integer quantity;

}
