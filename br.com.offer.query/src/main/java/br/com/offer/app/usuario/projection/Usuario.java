package br.com.offer.app.usuario.projection;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Usuario {
    UUID id;
    String nome;
    String documento;
    String tipo;
}
