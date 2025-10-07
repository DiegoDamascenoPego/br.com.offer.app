package br.com.offer.app.usuario.projection;

import java.util.UUID;

public record Contato(UUID id,
               String contato,
               String tipo) {

}
