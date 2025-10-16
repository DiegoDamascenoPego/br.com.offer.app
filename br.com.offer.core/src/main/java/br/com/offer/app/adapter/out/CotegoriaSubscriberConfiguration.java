package br.com.offer.app.adapter.out;

import static br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase.CategoriaRegistrada;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.offer.infra.stream.AbstractReceiver;

@Configuration
public class CotegoriaSubscriberConfiguration {

    @Bean
    AbstractReceiver<?> categoriaSubscriber() {
        return new AbstractReceiver<CategoriaRegistrada>(CategoriaRegistrada.class) {
            @Override
            public void receiveMessage(final CategoriaRegistrada message) {
                System.out.println("Categoria registrada: " + message.getId() + " - " + message.getDescricao());
            }
        };

    }

}
