package br.com.offer.app.domain.categoria.model;

import java.util.UUID;

public class CategoriaId {

    private final UUID id;

    public CategoriaId(UUID value) {
        this.id = value;
    }

    public static CategoriaId generate() {
        return new CategoriaId(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }

    public RuntimeException notFoundException() {
        return new RuntimeException("Categoria not found: " + id);
    }
}
