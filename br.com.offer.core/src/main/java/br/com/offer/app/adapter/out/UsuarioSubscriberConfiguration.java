package br.com.offer.app.adapter.out;

import static br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase.CategoriaRegistrada;
import static br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.offer.app.domain.usuario.usecase.RegistrarUsuarioUseCase;
import br.com.offer.infra.stream.AbstractReceiver;

@Configuration
public class UsuarioSubscriberConfiguration {

    @Bean
    AbstractReceiver<?> usuarioSubscriber() {
        return new AbstractReceiver<UsuarioRegistrado>(UsuarioRegistrado.class) {
            @Override
            public void receiveMessage(final UsuarioRegistrado message) {
                System.out.println("Usuário registrado: " + message.getId() + " - " + message.getNome());
            }
        };

    }

}
