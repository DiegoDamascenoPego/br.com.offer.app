package br.com.offer.app.usuario.projection;

import java.util.UUID;

public record Usuario(UUID id,
                     String nome,
                     String documento,
                     String tipo) {
}
