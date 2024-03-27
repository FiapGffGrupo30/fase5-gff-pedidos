package br.fiap.gff.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private UUID correlationalId;

    private Double total;

    private String status;

    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @ElementCollection
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
}
