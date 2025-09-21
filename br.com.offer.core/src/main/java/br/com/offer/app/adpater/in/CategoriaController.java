package br.com.offer.app.adpater.in;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import br.com.offer.app.domain.categoria.model.CategoriaId;
import br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase;
import br.com.offer.app.domain.categoria.usecase.RegistrarCategoriaUseCase.RegistrarCategoria;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/categorias", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Categoria", description = "API de Categoria")
public class CategoriaController {

    private final RegistrarCategoriaUseCase registrarCategoriaUseCase;

    @PostMapping
    public ResponseEntity<Void> registrarCategoria(@RequestBody RegistrarCategoria categoria) {
        CategoriaId id = registrarCategoriaUseCase.handle(categoria);

        return ResponseEntity.created(fromCurrentRequest()
                .path("/").path(id.asString()).build().toUri())
            .build();
    }
}
