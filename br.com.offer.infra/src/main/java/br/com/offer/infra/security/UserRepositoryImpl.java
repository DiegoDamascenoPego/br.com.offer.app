package br.com.offer.infra.security;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Override
    public boolean existsByDocumento(final String documento) {
        return entityManager.createNativeQuery(
                "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM usuario WHERE documento = :documento")
            .setParameter("documento", documento)
            .getSingleResult()
            .equals(Boolean.TRUE);
    }

    @Override
    @Transactional
    public void save(final User user) {
        entityManager.createNativeQuery("INSERT INTO usuario (id, nome, documento, tipo_usuario) VALUES (:id, :nome, :documento, :tipoUsuario)")
            .setParameter("id", user.getId())
            .setParameter("nome", user.getNome())
            .setParameter("documento", user.getDocumento())
            .setParameter("tipoUsuario", user.getTipoUsuario())
            .executeUpdate();
    }
}
