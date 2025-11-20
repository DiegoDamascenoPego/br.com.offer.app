package br.com.offer.infra.stream;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.DomainEvents;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class PublisherDomainEventsImpl implements PublisherDomainEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public <T extends AbstractAggregateRoot<?>> void dispacth(final T aggregate) {

        Arrays.stream(aggregate.getClass().getSuperclass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(DomainEvents.class))
            .findFirst()
            .ifPresent(method -> {
                try {
                    method.setAccessible(true);
                    Collection<?> events = (Collection<?>) method.invoke(aggregate);
                    if (events != null) {
                        events.forEach(applicationEventPublisher::publishEvent);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao publicar eventos de domínio", e);
                }
            });
    }
}
