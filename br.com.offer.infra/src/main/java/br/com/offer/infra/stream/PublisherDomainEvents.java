package br.com.offer.infra.stream;

import org.springframework.data.domain.AbstractAggregateRoot;

public interface PublisherDomainEvents {

    <T extends AbstractAggregateRoot<?>> void dispacth(T aggregate);
}
