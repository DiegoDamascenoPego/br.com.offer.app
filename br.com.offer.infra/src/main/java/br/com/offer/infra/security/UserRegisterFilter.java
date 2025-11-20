package br.com.offer.infra.security;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor
public class UserRegisterFilter extends OncePerRequestFilter {

    private final UserRepository repository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
        final HttpServletResponse response, final FilterChain filterChain)
        throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken oauth2Token) {
            Object principal = oauth2Token.getPrincipal();

            if (principal instanceof Jwt jwt) {
                final Map<String, Object> claims = jwt.getClaims();
                if (claims.containsKey("document")) {

                    final String document = claims.get("documentType") + ":" + claims.get("document");

                    if (!repository.existsByDocumento(document)) {
                        User user = User.builder()
                            .id(UUID.randomUUID())
                            .nome(claims.get("name").toString())
                            .documento(document)
                            .tipoUsuario("CLIENTE")
                            .build();

                        repository.save(user);
                    }

                }

            }
        }
        filterChain.doFilter(request, response);

    }
}
