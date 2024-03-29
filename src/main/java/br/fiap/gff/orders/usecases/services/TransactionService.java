package br.fiap.gff.orders.usecases.services;

import br.fiap.gff.orders.dto.TransactionProperties;
import br.fiap.gff.orders.exception.TransactionNotAllowedException;
import br.fiap.gff.orders.usecases.TransactionUseCase;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class TransactionService implements TransactionUseCase {

    private final ValueCommands<String, TransactionProperties> redisCommand;

    @SuppressWarnings("CdiInjectionPointsInspection")
    public TransactionService(RedisDataSource ds) {
        this.redisCommand = ds.value(TransactionProperties.class);
    }


    @Override
    public void verify(UUID transactionId, Long customerId) {
        TransactionProperties properties = redisCommand.get(transactionId.toString());
        if (properties == null || !properties.customerId().equals(customerId))
            throw new TransactionNotAllowedException(customerId, transactionId);
    }
}
