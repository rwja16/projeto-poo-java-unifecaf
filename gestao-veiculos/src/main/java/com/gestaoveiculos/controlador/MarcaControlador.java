package com.gestaoveiculos.controlador;

import com.gestaoveiculos.dto.RespostaApi;
import com.gestaoveiculos.dto.MarcaDTO;
import com.gestaoveiculos.servico.MarcaServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para Marcas.
 *
 * POO aplicada:
 * - Encapsulamento: delega regras de negócio ao MarcaServico
 * - Single Responsibility: cuida apenas do roteamento HTTP
 *
 * Endpoints:
 *   POST   /api/brands         -> cria marca
 *   GET    /api/brands         -> lista todas
 *   GET    /api/brands/{id}    -> busca por ID
 *   PUT    /api/brands/{id}    -> atualiza
 *   DELETE /api/brands/{id}    -> remove
 */
@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*")
public class MarcaControlador {

    private final MarcaServico brandService;

    public MarcaControlador(MarcaServico brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<RespostaApi<MarcaDTO>> create(@Valid @RequestBody MarcaDTO dto) {
        MarcaDTO created = brandService.create(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RespostaApi.success(created, "Marca cadastrada com sucesso"));
    }

    @GetMapping
    public ResponseEntity<RespostaApi<List<MarcaDTO>>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country) {

        List<MarcaDTO> brands;
        if (name != null) {
            brands = brandService.findByName(name);
        } else if (country != null) {
            brands = brandService.findByCountry(country);
        } else {
            brands = brandService.findAll();
        }

        return ResponseEntity.ok(RespostaApi.success(brands));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaApi<MarcaDTO>> findById(@PathVariable Long id) {
        MarcaDTO brand = brandService.findById(id);
        return ResponseEntity.ok(RespostaApi.success(brand));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaApi<MarcaDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody MarcaDTO dto) {

        MarcaDTO updated = brandService.update(id, dto);
        return ResponseEntity.ok(RespostaApi.success(updated, "Marca atualizada com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespostaApi<Void>> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(RespostaApi.success(null, "Marca removida com sucesso"));
    }
}
