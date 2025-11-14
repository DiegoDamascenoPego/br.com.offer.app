package br.com.offer.infra.security;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;
@org.springframework.stereotype.Repository
public interface UserRepository {

    boolean existsByDocumento(String id);

    void save(User user);
}
